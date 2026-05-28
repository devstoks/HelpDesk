// OBJETIVO:
//
// - Não usar regex
// - Validar caractere por caractere
// - Exibir mensagens abaixo do input
// - Aplicar máscaras
//
// ==========================================================


// ==========================================================
// FUNÇÕES AUXILIARES
// ==========================================================

function criarMensagemErro(input) {

    let erro =
        input.parentElement.querySelector(".input-error");

    if (!erro) {

        erro = document.createElement("span");

        erro.className = "input-error";

        input.parentElement.appendChild(erro);
    }

    return erro;
}

function mostrarErro(input, mensagem) {

    input.style.borderColor = "#ff5f5f";

    const erro = criarMensagemErro(input);

    erro.innerText = mensagem;

    erro.style.display = "block";
}

function limparErro(input) {

    input.style.borderColor = "";

    const erro =
        input.parentElement.querySelector(".input-error");

    if (erro) {

        erro.innerText = "";

        erro.style.display = "none";
    }
}


// ==========================================================
// VERIFICA SE É LETRA
// ==========================================================

function ehLetra(char) {

    const letras =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅ" +
        "àáâãäåÈÉÊËèéêëÌÍÎÏìíîïÒÓÔÕÖØ" +
        "òóôõöøÙÚÛÜùúûüÇç ";

    return letras.includes(char);
}


// ==========================================================
// VERIFICA SE É NÚMERO
// ==========================================================

function ehNumero(char) {

    return char >= "0" && char <= "9";
}


// ==========================================================
// VALIDA NOME
// ==========================================================

function validarNome(input) {

    let valor = input.value.trim();

    if (valor.length < 2) {

        mostrarErro(
            input,
            'O nome precisa ter no mínimo 2 caracteres.'
        );

        return false;
    }

    if (valor.length > 45) {

        mostrarErro(
            input,
            'O nome ultrapassou o limite permitido.'
        );

        return false;
    }

    for (let i = 0;i < valor.length;i++) {

        const char = valor[i];

        if (!ehLetra(char)) {

            mostrarErro(
                input,
                'Não pode colocar caracteres especiais no campo "Nome".'
            );

            return false;
        }
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA SOBRENOME
// ==========================================================

function validarSobrenome(input) {

    let valor = input.value.trim();

    if (valor.length < 2) {

        mostrarErro(
            input,
            'O sobrenome precisa ter no mínimo 2 caracteres.'
        );

        return false;
    }

    for (let i = 0;i < valor.length;i++) {

        if (!ehLetra(valor[i])) {

            mostrarErro(
                input,
                'Não pode colocar caracteres especiais no sobrenome.'
            );

            return false;
        }
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA EMAIL
// ==========================================================

function validarEmail(input) {

    let valor = input.value.trim();

    let possuiArroba = false;
    let possuiPonto = false;

    for (let i = 0;i < valor.length;i++) {

        const char = valor[i];

        if (char === "@") {
            possuiArroba = true;
        }

        if (char === ".") {
            possuiPonto = true;
        }
    }

    if (!possuiArroba || !possuiPonto) {

        mostrarErro(
            input,
            "Digite um e-mail válido."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA SENHA
// ==========================================================

function validarSenha(input) {

    let valor = input.value;

    if (valor === "") {

        limparErro(input);

        return true;
    }

    if (valor.length < 6) {

        mostrarErro(
            input,
            "A senha precisa ter no mínimo 6 caracteres."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA CPF
// ==========================================================

function validarCPF(input) {

    let valor = input.value;

    let numeros = "";

    for (let i = 0;i < valor.length;i++) {

        if (ehNumero(valor[i])) {

            numeros += valor[i];
        }
    }

    if (numeros.length !== 11) {

        mostrarErro(
            input,
            "O CPF deve possuir 11 números."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA TELEFONE
// ==========================================================

function validarTelefone(input) {

    let valor = input.value;

    let numeros = "";

    for (let i = 0;i < valor.length;i++) {

        if (ehNumero(valor[i])) {

            numeros += valor[i];
        }
    }

    if (numeros.length < 10 || numeros.length > 11) {

        mostrarErro(
            input,
            "Digite um telefone válido."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA CEP
// ==========================================================

function validarCEP(input) {

    let valor = input.value;

    let numeros = "";

    for (let i = 0;i < valor.length;i++) {

        if (ehNumero(valor[i])) {

            numeros += valor[i];
        }
    }

    if (numeros.length !== 8) {

        mostrarErro(
            input,
            "O CEP deve possuir 8 números."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA TEXTO SIMPLES
// ==========================================================

function validarTexto(input, campo) {

    let valor = input.value.trim();

    if (valor.length < 2) {

        mostrarErro(
            input,
            `O campo "${campo}" está muito curto.`
        );

        return false;
    }

    for (let i = 0;i < valor.length;i++) {

        if (!ehLetra(valor[i])) {

            mostrarErro(
                input,
                `O campo "${campo}" não aceita números ou caracteres especiais.`
            );

            return false;
        }
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA ENDEREÇO
// ==========================================================

function validarEndereco(input) {

    let valor = input.value.trim();

    if (valor.length < 5) {

        mostrarErro(
            input,
            "Digite um endereço válido."
        );

        return false;
    }

    if (valor.length > 150) {

        mostrarErro(
            input,
            "O endereço ultrapassou o limite permitido."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// VALIDA DATA
// ==========================================================

function validarDataNascimento(input) {

    let valor = input.value;

    if (valor === "") {

        mostrarErro(
            input,
            "Informe sua data de nascimento."
        );

        return false;
    }

    const nascimento = new Date(valor);

    const hoje = new Date();

    if (nascimento > hoje) {

        mostrarErro(
            input,
            "A data não pode ser futura."
        );

        return false;
    }

    limparErro(input);

    return true;
}


// ==========================================================
// MÁSCARA CPF
// ==========================================================

document.getElementById("cpf")
    ?.addEventListener("input", function(e) {

        let numeros = "";

        for (let i = 0;i < e.target.value.length;i++) {

            const char = e.target.value[i];

            if (ehNumero(char)) {

                numeros += char;
            }
        }

        if (numeros.length > 11) {

            numeros = numeros.substring(0, 11);
        }

        let formatado = "";

        for (let i = 0;i < numeros.length;i++) {

            if (i === 3 || i === 6) {

                formatado += ".";
            }

            if (i === 9) {

                formatado += "-";
            }

            formatado += numeros[i];
        }

        e.target.value = formatado;
    });


// ==========================================================
// MÁSCARA TELEFONE
// ==========================================================

document.getElementById("telefone")
    ?.addEventListener("input", function(e) {

        let numeros = "";

        for (let i = 0;i < e.target.value.length;i++) {

            const char = e.target.value[i];

            if (ehNumero(char)) {

                numeros += char;
            }
        }

        if (numeros.length > 11) {

            numeros = numeros.substring(0, 11);
        }

        let formatado = "";

        for (let i = 0;i < numeros.length;i++) {

            if (i === 0) {

                formatado += "(";
            }

            if (i === 2) {

                formatado += ") ";
            }

            if (i === 7) {

                formatado += "-";
            }

            formatado += numeros[i];
        }

        e.target.value = formatado;
    });


// ==========================================================
// MÁSCARA CEP
// ==========================================================

document.getElementById("cep")
    ?.addEventListener("input", function(e) {

        let numeros = "";

        for (let i = 0;i < e.target.value.length;i++) {

            const char = e.target.value[i];

            if (ehNumero(char)) {

                numeros += char;
            }
        }

        if (numeros.length > 8) {

            numeros = numeros.substring(0, 8);
        }

        let formatado = "";

        for (let i = 0;i < numeros.length;i++) {

            if (i === 5) {

                formatado += "-";
            }

            formatado += numeros[i];
        }

        e.target.value = formatado;
    });


// ==========================================================
// EVENTOS INPUT
// ==========================================================

document.getElementById("nome")
    ?.addEventListener("input", function() {

        validarNome(this);
    });

document.getElementById("sobrenome")
    ?.addEventListener("input", function() {

        validarSobrenome(this);
    });

document.getElementById("email")
    ?.addEventListener("input", function() {

        validarEmail(this);
    });

document.getElementById("senha")
    ?.addEventListener("input", function() {

        validarSenha(this);
    });

document.getElementById("cpf")
    ?.addEventListener("input", function() {

        validarCPF(this);
    });

document.getElementById("telefone")
    ?.addEventListener("input", function() {

        validarTelefone(this);
    });

document.getElementById("cep")
    ?.addEventListener("input", function() {

        validarCEP(this);
    });

document.getElementById("cidade")
    ?.addEventListener("input", function() {

        validarTexto(this, "Cidade");
    });

document.getElementById("bairro")
    ?.addEventListener("input", function() {

        validarTexto(this, "Bairro");
    });

document.getElementById("estado")
    ?.addEventListener("input", function() {

        validarTexto(this, "Estado");
    });

document.getElementById("dataNascimento")
    ?.addEventListener("change", function() {

        validarDataNascimento(this);
    });

document.getElementById("endereco")
    ?.addEventListener("input", function() {

        validarEndereco(this);
    });


// ==========================================================
// SUBMIT
// ==========================================================

document.querySelector("form")
    ?.addEventListener("submit", function(e) {

        let valido = true;

        if (!validarNome(document.getElementById("nome"))) {
            valido = false;
        }

        if (!validarSobrenome(document.getElementById("sobrenome"))) {
            valido = false;
        }

        if (!validarEmail(document.getElementById("email"))) {
            valido = false;
        }

        if (!validarSenha(document.getElementById("senha"))) {
            valido = false;
        }

        if (!validarCPF(document.getElementById("cpf"))) {
            valido = false;
        }

        if (!validarTelefone(document.getElementById("telefone"))) {
            valido = false;
        }

        if (!validarCEP(document.getElementById("cep"))) {
            valido = false;
        }

        if (!validarEndereco(document.getElementById("endereco"))) {
            valido = false;
        }

        if (!validarDataNascimento(document.getElementById("dataNascimento"))) {
            valido = false;
        }

        if (!valido) {

            e.preventDefault();
        }
    });


// ==========================================================
// MOSTRAR / ESCONDER SENHA
// ==========================================================

function toggleSenha() {

    const senha =
        document.getElementById("senha");

    if (senha.type === "password") {

        senha.type = "text";

    } else {

        senha.type = "password";
    }
}