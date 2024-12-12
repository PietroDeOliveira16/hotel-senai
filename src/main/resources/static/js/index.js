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

function locacoesEmCurso(callback) {
    $.ajax({
    url: '/addLocacoesAtivas', // Substitua pelo URL do seu servidor ou endpoint
    method: 'POST',
        success: function(response) {
            $(".container .curso").append(response);
            if(callback){
                callback();
            }
        },
        error: function(error) {
            console.log("Erro: ", error);
        }
    });
}

function locacoesPassadas(callback) {
    $.ajax({
    url: '/addLocacoesPassadas', // Substitua pelo URL do seu servidor ou endpoint
    method: 'POST',
        success: function(response) {
            $(".container .passadas").append(response);
            if(callback){
                callback();
            }
        },
        error: function(error) {
            console.log("Erro: ", error);
        }
    });
}

function locacoesReservadas(callback) {
    $.ajax({
    url: '/addLocacoesReservadas', // Substitua pelo URL do seu servidor ou endpoint
    method: 'POST',
        success: function(response) {
            $(".container .reservas").append(response);
            if(callback){
                callback();
            }
        },
        error: function(error) {
            console.log("Erro: ", error);
        }
    });
}

window.onload = function() {
    locacoesEmCurso(function() {  // Após a primeira requisição ser concluída
        locacoesPassadas(function() {  // Após a segunda requisição ser concluída
            locacoesReservadas();  // Após a terceira requisição ser concluída
        });
    });
}

/*
const Toast = Swal.mixin({
  toast: true,
  position: "top-end",
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.onmouseenter = Swal.stopTimer;
    toast.onmouseleave = Swal.resumeTimer;
  }
});
Toast.fire({
  icon: "success",
  title: "Signed in successfully"
});
*/



