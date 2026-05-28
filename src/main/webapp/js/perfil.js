// para desativar a conta

function confirmarDesativacao(){

    const confirmar = confirm(
        "Tem certeza que deseja desativar sua conta?"
    );

    if(confirmar){

        window.location.href = "SvDisableUser";

    }
}

 // para exibir a senha

function toggleSenha() {

    const input = document.getElementById("senha");

    if (input.type === "password") {
        input.type = "text";
    } else {
        input.type = "password";
    }

}