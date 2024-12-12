function adicionarSelecaoQuartos(){
    $(".row .campo").remove();
    $.ajax({
        url: '/buscarQuartos',
        method: 'POST',
        data:{
            checkIn: $("#checkIn").val(),
            checkOut: $("#checkOut").val()
        },
        success: function(response){
            let div = $(response).addClass('campo');
            $(".row").append(div);
        },
        error: function(){
            console.log("Erro: ", error);
        }
    })
}
$("#btnSelecQuartos").click(adicionarSelecaoQuartos);

function atualizarCampos(select) {
        var numeroQuarto = select.value;

        if (numeroQuarto) {
            // Fazendo a requisição AJAX para pegar as informações do quarto
            $.ajax({
                url: '/quarto/' + numeroQuarto, // URL que vai buscar as informações
                method: 'GET',
                success: function(quarto) {
                    // Preenchendo os campos de acordo com a resposta da API
                    document.getElementById('inputTipo').value = quarto.tipo;
                    document.getElementById('inputEspaco').value = quarto.espaco_pessoas;
                    document.getElementById('inputPreco').value = quarto.preco;
                    document.getElementById('inputSolteiro').value = quarto.camas_solteiro;
                    document.getElementById('inputCasal').value = quarto.camas_casal;
                    document.getElementById('inputBanheiros').value = quarto.banheiros;
                    document.getElementById('inputCloset').checked = quarto.closet;
                    document.getElementById('inputTotal').value = ($('#inputDias').val() * quarto.preco);
                },
                error: function() {
                    document.getElementById('inputTipo').value = null;
                    document.getElementById('inputEspaco').value = null;
                    document.getElementById('inputPreco').value = null;
                    document.getElementById('inputSolteiro').value = null;
                    document.getElementById('inputCasal').value = null;
                    document.getElementById('inputBanheiros').value = null;
                    document.getElementById('inputCloset').checked = false;
                }
            });
        }
}