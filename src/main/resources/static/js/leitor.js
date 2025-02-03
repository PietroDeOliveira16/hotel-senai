function lerProduto(){
    $("#camera-container").removeClass('d-none');

	let lastCodes = []; // Armazena as últimas leituras
	let requiredMatches = 5; // Número de leituras idênticas necessárias

	Quagga.init({
		inputStream: {
			name: "Live",
			type: "LiveStream",
			target: $('#camera')[0]
		},
		decoder: {
			readers: ['ean_reader']
		}
	}, function(err) {
		if (err) {
			console.log(err);
			return;
		}
		console.log("Initialization finished. Ready to start");
		Quagga.start();
	});

	Quagga.onDetected(function(data) {
		const currentCode = data.codeResult.code;

		if (lastCodes.length === 0 || lastCodes[lastCodes.length - 1] === currentCode) {
			// Adiciona o código se for o primeiro ou igual ao anterior
			lastCodes.push(currentCode);
		} else {
			// Reinicia a contagem se o código for diferente
			lastCodes = [currentCode];
		}

		if (lastCodes.length >= requiredMatches) {
			insereProduto(currentCode);
			lastCodes = []; // Reinicia para novas leituras
		}
	});
}

function insereProduto(code){
    $.ajax({
       url: '/rest/produto',
       method: 'GET',
       data:{
            codigo: code
       },
       success: function(produto){
            document.getElementById('nomeProduto').value = produto.nome;
            const precoFormatado = new Intl.NumberFormat('pt-BR', {
                                style: 'currency',
                                currency: 'BRL',
                                minimumFractionDigits: 2, // Garante sempre 2 casas decimais
                                maximumFractionDigits: 2  // Limita para 2 casas decimais
                            }).format(produto.preco);
            document.getElementById('valorProduto').textContent = precoFormatado;
            document.getElementById('valorTotal').textContent = precoFormatado;
            document.getElementById('inputQuantidade').value = 1;
            $("#codBarras").val(code);
       },
       error: function(){
            console.log('fodeu');
       }
    });
}

function mostrarProduto(inputElement) {
    const nomeProduto = inputElement.value.trim().toLowerCase();

    console.log(nomeProduto);
            // Encontrar o produto correspondente na lista
    if(!Array.isArray(produtosData)){
        console.error("produtosData n é considerado um array", produtosData);
        produtosData = [];
    }

    const produtoSelecionado = produtosData.find(produto => produto.nome.toLowerCase() === nomeProduto);

    console.log(produtoSelecionado);

    if (produtoSelecionado) {
                // Atualizar os campos com as informações do produto encontrado
        document.getElementById('codBarras').value = produtoSelecionado.cod_barras;

        const precoFormatado = new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                    minimumFractionDigits: 2, // Garante sempre 2 casas decimais
                    maximumFractionDigits: 2  // Limita para 2 casas decimais
                }).format(produtoSelecionado.preco);


        document.getElementById('valorProduto').textContent = precoFormatado;
        document.getElementById('valorTotal').textContent = precoFormatado;
        document.getElementById('inputQuantidade').value = 1;

    } else {
                // Caso o produto não seja encontrado
        document.getElementById('codBarras').value = '';
        document.getElementById('nomeProduto').value = '';
        document.getElementById('inputQuantidade').value = 1;
        document.getElementById('valorProduto').textContent = '---';
        document.getElementById('valorTotal').textContent = '---';
    }
}

/*$("[type='number']").keypress(function (evt) {
    evt.preventDefault();
});*/

$("#btnRegistrar").click(registrarConsumo);
function registrarConsumo(){
    var nomeProduto = document.getElementById('nomeProduto').value;
    console.log("Nome do produto = " + nomeProduto);

    // Obtém o datalist associado ao input
    var datalist = document.getElementById('produtos');
    console.log("datalist = " + datalist);

    // Encontra a opção no datalist correspondente ao nome do produto
    var option = Array.from(datalist.options).find(option => option.value.trim().toLowerCase() === nomeProduto.trim().toLowerCase());


    if (option) {
        console.log("opção escolhida = " + option);
        // Acessa o atributo data-id da opção selecionada
        var produtoId = option.getAttribute('data-id');
        console.log("id produto = " + produtoId);
        var produtoPreco = option.getAttribute('data-preco');
        console.log("preco = " + produtoPreco);
    }else{
        console.log("opção não encontrada");
    }

    var qtd = document.getElementById('inputQuantidade').value;

    var locacaoId = document.getElementById("locacao").value;

    $.ajax({
        url: '/registrarConsumo',
        method: 'POST',
        data:{
            id_produto: produtoId,
            id_locacao: locacaoId,
            quantidade: qtd,
            preco: produtoPreco
        },
        success: function(response){
            if(response != null){
                $("#lista-itens").prepend(response);
                console.log(response);
                document.getElementById("nomeProduto").value = '';
                document.getElementById('codBarras').value = '';
                document.getElementById('valorProduto').textContent = '---';
                document.getElementById('valorTotal').textContent = '---';
                document.getElementById('inputQuantidade').value = 1;
                Swal.fire({
                   title: "Registrado!",
                   text: "Seu consumo foi registrado na sua locação e será cobrado ao fim de sua estadia, obrigado!",
                   icon: "success",
                   showConfirmButton: false,
                   timer: 4500,
                   timerProgressBar: true,
                     didOpen: () => {
                       Swal.showLoading();
                       const timer = Swal.getPopup().querySelector("b");
                       timerInterval = setInterval(() => {
                         timer.textContent = `${Swal.getTimerLeft()}`;
                       }, 100);
                     },
                     willClose: () => {
                       clearInterval(timerInterval);
                     }
                   }).then((result) => {
                     /* Read more about handling dismissals below */
                     if (result.dismiss === Swal.DismissReason.timer) {}
                });
            }
        },
        error: function(){
            console.log('fudeu');
        }
    });
}

function incrementarPrecoTotal(qtdInput){
    var quantidade = qtdInput.value;

    const nomeProduto = document.getElementById('nomeProduto').value.trim().toLowerCase();
    const produtoSelecionado = produtosData.find(produto => produto.nome.toLowerCase() === nomeProduto);

    var precoNormal = produtoSelecionado.preco;
    var total = precoNormal * quantidade;

    const precoFormatado = new Intl.NumberFormat('pt-BR', {
                        style: 'currency',
                        currency: 'BRL',
                        minimumFractionDigits: 2, // Garante sempre 2 casas decimais
                        maximumFractionDigits: 2  // Limita para 2 casas decimais
                    }).format(total);
    document.getElementById('valorTotal').textContent = precoFormatado;
}
