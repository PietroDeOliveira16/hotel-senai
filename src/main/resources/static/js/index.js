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

function atualizarCampos(element){
    let tipo = element.dataset.tipo;
    let espaco = element.dataset.espaco;
    let solteiro = element.dataset.solteiro;
    let casal = element.dataset.casal;
    let banheiros = element.dataset.banheiros;

    $.ajax({
        url: "locacao/cadastro",
        method: "post",
        data:{
            tipo: tipo,
            espaco: espaco,
            solteiro: solteiro,
            casal: casal,
            banheiros: banheiros
        }
    })
}