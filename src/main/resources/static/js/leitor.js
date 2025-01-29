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

function insereProduto(codigo){
	$("#codBarras").val(codigo);
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
        document.getElementById('descProduto').textContent = produtoSelecionado.nome;

        const precoFormatado = new Intl.NumberFormat('pt-BR', {
                    style: 'currency',
                    currency: 'BRL',
                    minimumFractionDigits: 2, // Garante sempre 2 casas decimais
                    maximumFractionDigits: 2  // Limita para 2 casas decimais
                }).format(produtoSelecionado.preco);


        document.getElementById('valorProduto').textContent = precoFormatado;
    } else {
                // Caso o produto não seja encontrado
        document.getElementById('codBarras').value = '';
        document.getElementById('descProduto').textContent = 'Produto não encontrado';
        document.getElementById('valorProduto').textContent = '---';
    }
}

$("[type='number']").keypress(function (evt) {
    evt.preventDefault();
});

$("#btnRegistrar").click(registrarConsumo);

function registrarConsumo(){
    const nomeProduto = document.getElementById('nomeProduto').value;

    // Obtém o datalist associado ao input
    const datalist = document.getElementById('produtos');

    // Encontra a opção no datalist correspondente ao nome do produto
    const option = Array.from(datalist.options).find(option => option.value === nomeProduto);

    if (option) {
        // Acessa o atributo data-id da opção selecionada
        const produtoId = option.getAttribute('data-id');
        const produtoPreco = option.getAttribute('data-preco');
    }

    const quantidade = document.getElementById('inputQuantidade').value;

    $.ajax({
        url: '/registrarConsumo',
        method: 'POST',
        success: function(response){
            if(response != null){
                document.getElementById('codBarras').value = '';
                document.getElementById('descProduto').textContent = 'Produto não encontrado';
                document.getElementById('valorProduto').textContent = '---';
            }
        },
        error: function(){
            console.log('fodeu');
        }
    })

}
