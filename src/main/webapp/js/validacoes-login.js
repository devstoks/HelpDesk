
// mostrar senha
function togglePassword() {

    const passwordInput =
        document.getElementById("password");

    const toggleBtn =
        document.querySelector(".toggle-password i");

    if (passwordInput.type === "password") {

        passwordInput.type = "text";

        toggleBtn.classList.remove("fa-eye");

        toggleBtn.classList.add("fa-eye-slash");

    } else {

        passwordInput.type = "password";

        toggleBtn.classList.remove("fa-eye-slash");

        toggleBtn.classList.add("fa-eye");
    }
}

// mensagem erro
function setError(input, messageId, message) {

    const msg =
        document.getElementById(messageId);

    input.classList.remove("input-success");

    input.classList.add("input-error");

    msg.innerText = message;
}


// mensagem sucesso
function setSuccess(input, messageId) {

    const msg =
        document.getElementById(messageId);

    input.classList.remove("input-error");

    input.classList.add("input-success");

    msg.innerText = "";
}

// validando caracteres especiais
function containsInvalidChars(texto) {

    const especiais =
        "!#$%¨&*()+={}[]|\\/<>,;:\"'`~^";

    for (let i = 0;i < texto.length;i++) {

        const char = texto[i];

        for (let j = 0;j < especiais.length;j++) {

            if (char === especiais[j]) {
                return true;
            }
        }
    }

    return false;
}



// SENHA

const passwordInput =
    document.getElementById("password");

passwordInput.addEventListener("input", function() {

    const valor =
        passwordInput.value;

    if (valor.length === 0) {

        setError(
            passwordInput,
            "passwordMessage",
            "A senha é obrigatória."
        );

        return;
    }

    if (valor.length < 6) {

        setError(
            passwordInput,
            "passwordMessage",
            "A senha precisa ter no mínimo 6 caracteres."
        );

        return;
    }

    if (valor.includes(" ")) {

        setError(
            passwordInput,
            "passwordMessage",
            "A senha não pode conter espaços."
        );

        return;
    }

    if (containsInvalidChars(valor)) {

        setError(
            passwordInput,
            "passwordMessage",
            "A senha contém caracteres inválidos."
        );

        return;
    }

    setSuccess(passwordInput, "passwordMessage");
});

// SUBMIT

const form =
    document.querySelector(".login-form");

form.addEventListener("submit", function(e) {

    const hasError =
        document.querySelector(".input-error");

    if (hasError) {

        e.preventDefault();

        alert(
            "Corrija os campos inválidos antes de continuar."
        );
    }
});



// EMAIL

const emailInput =
    document.getElementById("email");

emailInput.addEventListener("input", function() {

    const valor =
        emailInput.value.trim();

    // Campo vazio
    if (valor.length === 0) {

        setError(
            emailInput,
            "emailMessage",
            "O email é obrigatório."
        );

        return;
    }

    // Espaços
    if (valor.includes(" ")) {

        setError(
            emailInput,
            "emailMessage",
            "O email não pode conter espaços."
        );

        return;
    }

    // Precisa conter @
    if (!valor.includes("@")) {

        setError(
            emailInput,
            "emailMessage",
            "O email precisa conter @."
        );

        return;
    }

    // Não pode começar ou terminar com @
    if (valor.startsWith("@") || valor.endsWith("@")) {

        setError(
            emailInput,
            "emailMessage",
            "O email é inválido."
        );

        return;
    }

    // Separar partes
    const partes =
        valor.split("@");

    // Só pode existir 1 @
    if (partes.length !== 2) {

        setError(
            emailInput,
            "emailMessage",
            "O email contém múltiplos @."
        );

        return;
    }

    const usuario =
        partes[0];

    const dominio =
        partes[1];

    // Usuário vazio
    if (usuario.length < 1) {

        setError(
            emailInput,
            "emailMessage",
            "O email precisa ter usuário."
        );

        return;
    }

    // Domínio precisa ter ponto
    if (!dominio.includes(".")) {

        setError(
            emailInput,
            "emailMessage",
            "O domínio precisa conter ponto."
        );

        return;
    }

    const dominioPartes =
        dominio.split(".");

    // Exemplo: gmail + com
    if (dominioPartes.length < 2) {

        setError(
            emailInput,
            "emailMessage",
            "Domínio inválido."
        );

        return;
    }

    const nomeDominio =
        dominioPartes[0];

    const extensao =
        dominioPartes[dominioPartes.length - 1];

    // gmail
    if (nomeDominio.length < 3) {

        setError(
            emailInput,
            "emailMessage",
            "O domínio precisa ter pelo menos 3 letras."
        );

        return;
    }

    // com
    if (extensao.length < 2) {

        setError(
            emailInput,
            "emailMessage",
            "A extensão do domínio é inválida."
        );

        return;
    }

    setSuccess(emailInput, "emailMessage");
});