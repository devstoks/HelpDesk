
// STEP


let currentStep = 1;


// ELEMENTOS


const nextBtn = document.getElementById("nextBtn");


// HELPERS


function setError(input, messageId, message) {
    const msg = document.getElementById(messageId);

    input.classList.remove("input-success");
    input.classList.add("input-error");

    msg.innerText = message;
}

function setSuccess(input, messageId) {
    const msg = document.getElementById(messageId);

    input.classList.remove("input-error");
    input.classList.add("input-success");

    msg.innerText = "";
}


// UTIL: SOMENTE NÚMEROS


function somenteNumeros(valor) {
    let result = "";

    for (let i = 0;i < valor.length;i++) {
        const c = valor[i];

        if (c >= "0" && c <= "9") {
            result += c;
        }
    }

    return result;
}


// UTIL: VERIFICA SE É LETRA


function isLetra(char) {
    const letras =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅàáâãäåÉÈÊËéèêëÍÌÎÏíìîïÓÒÔÕÖóòôõöÚÙÛÜúùûüÇç";

    return letras.indexOf(char) !== -1;
}


// MÁSCARAS


function formatarCPF(input) {
    let v = somenteNumeros(input.value);

    if (v.length > 11) v = v.substring(0, 11);

    let out = "";

    for (let i = 0;i < v.length;i++) {
        out += v[i];

        if (i === 2 || i === 5) {
            if (i !== v.length - 1) out += ".";
        }

        if (i === 8) {
            if (i !== v.length - 1) out += "-";
        }
    }

    input.value = out;
}

function formatarTelefone(input) {
    let v = somenteNumeros(input.value);

    if (v.length > 11) v = v.substring(0, 11);

    let out = "";

    for (let i = 0;i < v.length;i++) {
        if (i === 0) out += "(";

        out += v[i];

        if (i === 1) out += ") ";

        if (i === 5 && v.length <= 10) out += "-";
        if (i === 6 && v.length > 10) out += "-";
    }

    input.value = out;
}

function formatarCEP(input) {
    let v = somenteNumeros(input.value);

    if (v.length > 8) v = v.substring(0, 8);

    let out = "";

    for (let i = 0;i < v.length;i++) {
        out += v[i];

        if (i === 4 && i !== v.length - 1) {
            out += "-";
        }
    }

    input.value = out;
}


// STEP DISPLAY


function updateStepDisplay() {
    document.getElementById("step1")
        .classList.toggle("active", currentStep === 1);

    document.getElementById("step2")
        .classList.toggle("active", currentStep === 2);

    document.getElementById("step1Indicator")
        .classList.toggle("active", currentStep === 1);

    document.getElementById("step2Indicator")
        .classList.toggle("active", currentStep === 2);

    document.getElementById("step1Indicator")
        .classList.toggle("completed", currentStep > 1);

    document.getElementById("progressFill").style.width =
        currentStep === 1 ? "50%" : "100%";

    document.getElementById("prevBtn").style.display =
        currentStep === 1 ? "none" : "flex";

    nextBtn.style.display =
        currentStep === 1 ? "flex" : "none";

    document.getElementById("submitBtn").style.display =
        currentStep === 2 ? "flex" : "none";
}


// STEP CONTROL


function changeStep(direction) {
    if (direction === 1 && nextBtn.disabled) return;

    currentStep += direction;
    updateStepDisplay();
}


// VALIDAÇÕES


function validateNome() {
    const input = document.getElementById("nome");
    const value = input.value.trim();

    if (value.length < 2) {
        setError(input, "nomeMessage", "Nome inválido.");
        return false;
    }

    for (let i = 0;i < value.length;i++) {
        if (!isLetra(value[i]) && value[i] !== " ") {
            setError(input, "nomeMessage", "Nome inválido.");
            return false;
        }
    }

    setSuccess(input, "nomeMessage");
    return true;
}

function validateSobrenome() {
    const input = document.getElementById("sobrenome");
    const value = input.value.trim();

    if (value.length < 2) {
        setError(input, "sobrenomeMessage", "Sobrenome inválido.");
        return false;
    }

    for (let i = 0;i < value.length;i++) {
        if (!isLetra(value[i]) && value[i] !== " ") {
            setError(input, "sobrenomeMessage", "Sobrenome inválido.");
            return false;
        }
    }

    setSuccess(input, "sobrenomeMessage");
    return true;
}


// EMAIL (SEM REGEX)


function validateEmail() {
    const input = document.getElementById("email");
    const value = input.value.trim();

    let atIndex = -1;
    let dotIndex = -1;

    // precisa ter exatamente 1 @
    for (let i = 0;i < value.length;i++) {
        if (value[i] === "@") {
            if (atIndex !== -1) {
                setError(input, "emailMessage", "Email inválido.");
                return false;
            }
            atIndex = i;
        }
    }

    if (atIndex <= 0 || atIndex === value.length - 1) {
        setError(input, "emailMessage", "Email inválido.");
        return false;
    }

    // precisa ter ponto depois do @
    for (let i = atIndex + 1;i < value.length;i++) {
        if (value[i] === ".") {
            dotIndex = i;
            break;
        }
    }

    if (dotIndex === -1 || dotIndex === atIndex + 1 || dotIndex === value.length - 1) {
        setError(input, "emailMessage", "Email inválido.");
        return false;
    }

    setSuccess(input, "emailMessage");
    return true;
}

function validateSenha() {
    const input = document.getElementById("senha");

    if (input.value.length < 6) {
        setError(input, "senhaMessage", "Senha fraca.");
        return false;
    }

    setSuccess(input, "senhaMessage");
    return true;
}

function validateCPF() {
    const input = document.getElementById("cpf");
    const v = somenteNumeros(input.value);

    if (v.length !== 11) {
        setError(input, "cpfMessage", "CPF inválido.");
        return false;
    }

    setSuccess(input, "cpfMessage");
    return true;
}

function validateTelefone() {
    const input = document.getElementById("telefone");
    const v = somenteNumeros(input.value);

    if (v.length < 10) {
        setError(input, "telefoneMessage", "Telefone inválido.");
        return false;
    }

    setSuccess(input, "telefoneMessage");
    return true;
}


// STEP VALIDATION


function validateStep1() {
    const nomeOk = validateNome();
    const sobrenomeOk = validateSobrenome();
    const emailOk = validateEmail();
    const senhaOk = validateSenha();
    const cpfOk = validateCPF();
    const telefoneOk = validateTelefone();

    const valid =
        nomeOk &&
        sobrenomeOk &&
        emailOk &&
        senhaOk &&
        cpfOk &&
        telefoneOk;

    nextBtn.disabled = !valid;

    if (valid) {
        nextBtn.classList.remove("btn-disabled");
    } else {
        nextBtn.classList.add("btn-disabled");
    }
}


// EVENTS


["nome", "sobrenome", "email", "senha"].forEach(id => {
    document.getElementById(id)
        .addEventListener("input", validateStep1);
});

document.getElementById("cpf").addEventListener("input", function() {
    formatarCPF(this);
    validateStep1();
});

document.getElementById("telefone").addEventListener("input", function() {
    formatarTelefone(this);
    validateStep1();
});

document.getElementById("cep").addEventListener("input", function() {
    formatarCEP(this);
});


// INIT


updateStepDisplay();