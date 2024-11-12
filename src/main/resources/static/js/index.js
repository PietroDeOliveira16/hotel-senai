function logout(){
    Swal.fire({
        title: "Tem certeza que deseja sair?",
        text: "Você será desconectado e será necessário realizar login novamente para acessar os recursos da página",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sim",
        cancelButtonText: "Não"
    }).then((result) => {
        if (result.isConfirmed) {
            $.get("/logout",function(){window.location.href="/"});
        }
    });
}
$("#btnLogout").click(logout);

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
                    document.getElementById('inputSolteiro').value = quarto.camas_solteiro;
                    document.getElementById('inputCasal').value = quarto.camas_casal;
                    document.getElementById('inputBanheiros').value = quarto.banheiros;
                    document.getElementById('inputCloset').checked = quarto.closet;
                },
                error: function() {
                    document.getElementById('inputTipo').value = null;
                    document.getElementById('inputEspaco').value = null;
                    document.getElementById('inputSolteiro').value = null;
                    document.getElementById('inputCasal').value = null;
                    document.getElementById('inputBanheiros').value = null;
                    document.getElementById('inputCloset').checked = false;
                }
            });
        }
    }