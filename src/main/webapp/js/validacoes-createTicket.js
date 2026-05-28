// ELEMENTOS

const formTicket = document.getElementById("display-ticket");
const submitBtn = document.querySelector(".form-btn");

// controla se usuário já interagiu com o formulário
let touched = false;


// HELPERS VISUAIS

function setErrorField(id, message) {
    const el = document.getElementById(id);
    el.classList.add("input-error");
    el.classList.remove("input-success");

    console.log(message);
}

function setSuccessField(id) {
    const el = document.getElementById(id);
    el.classList.remove("input-error");
    el.classList.add("input-success");
}


// TRATAMENTO DE TEXTO (SEM destruir digitação)

function normalizarTexto(valor) {
    let result = "";

    for (let i = 0;i < valor.length;i++) {
        const c = valor[i];

        // evita espaços duplicados
        if (c === " " && result[result.length - 1] === " ") continue;

        result += c;
    }

    return result;
}

// detecta lixo tipo "aaaaaa", "@@@@@@", "....."
function textoValidoBasico(valor) {
    let letras = 0;
    let outros = 0;

    for (let i = 0;i < valor.length;i++) {
        const c = valor[i];

        if (
            (c >= "a" && c <= "z") ||
            (c >= "A" && c <= "Z") ||
            (c >= "À" && c <= "ÿ") ||
            c === " "
        ) {
            letras++;
        } else {
            outros++;
        }
    }

    if (letras < 3) return false;
    if (outros > letras) return false;

    return true;
}


// VALIDAÇÕES

function validateCategoria() {
    const el = document.getElementById("categoria");

    if (!el.value) {
        setErrorField("categoria", "Categoria obrigatória");
        return false;
    }

    setSuccessField("categoria");
    return true;
}

function validateTipo() {
    const el = document.getElementById("tipo");

    if (el.value === "") return true;

    setSuccessField("tipo");
    return true;
}

function validatePrioridade() {
    const el = document.getElementById("prioridade");

    if (!el.value) {
        setErrorField("prioridade", "Prioridade obrigatória");
        return false;
    }

    setSuccessField("prioridade");
    return true;
}

function validateComplexidade() {
    const el = document.getElementById("complexidade");

    if (el.value === "") return true;

    setSuccessField("complexidade");
    return true;
}

function validatePrazo() {
    const el = document.getElementById("prazo");

    if (!el.value) return true;

    const data = new Date(el.value);
    const agora = new Date();

    if (data < agora) {
        setErrorField("prazo", "Prazo não pode estar no passado");
        return false;
    }

    setSuccessField("prazo");
    return true;
}

function validateTitulo() {
    const el = document.getElementById("titulo");
    const value = normalizarTexto(el.value);

    if (value.trim().length < 5) {
        setErrorField("titulo", "Título muito curto");
        return false;
    }

    if (!textoValidoBasico(value)) {
        setErrorField("titulo", "Título inválido");
        return false;
    }

    setSuccessField("titulo");
    return true;
}

function validateDescricao() {
    const el = document.getElementById("descricao");
    const value = normalizarTexto(el.value);

    const trimmed = value.trim();

    if (trimmed.length === 0) {
        setErrorField("descricao", "Descrição obrigatória");
        return false;
    }

    if (trimmed.length < 10) {
        setErrorField("descricao", "Descrição muito curta");
        return false;
    }

    // detecta spam puro de símbolos
    let letrasOuNumeros = 0;
    let outros = 0;

    for (let i = 0;i < trimmed.length;i++) {
        const c = trimmed[i];

        const isLetter =
            (c >= "a" && c <= "z") ||
            (c >= "A" && c <= "Z") ||
            (c >= "À" && c <= "ÿ");

        const isNumber = (c >= "0" && c <= "9");

        if (isLetter || isNumber) {
            letrasOuNumeros++;
        } else if (c !== " ") {
            outros++;
        }
    }

    // se for só símbolo (ex: @@@@@@, ......, ######)
    if (letrasOuNumeros === 0 && outros > 0) {
        setErrorField("descricao", "Descrição inválida");
        return false;
    }

    setSuccessField("descricao");
    return true;
}


// VALIDAÇÃO GERAL

function validateTicket() {
    const results = [
        validateCategoria(),
        validateTipo(),
        validatePrioridade(),
        validateComplexidade(),
        validatePrazo(),
        validateTitulo(),
        validateDescricao()
    ];

    const isValid = results.every(r => r === true);

    // bloqueia botão até interação do usuário
    if (!touched) {
        submitBtn.disabled = true;
        submitBtn.classList.add("btn-disabled");
        return false;
    }

    submitBtn.disabled = !isValid;

    if (!isValid) {
        submitBtn.classList.add("btn-disabled");
    } else {
        submitBtn.classList.remove("btn-disabled");
    }

    return isValid;
}


// EVENTS

[
    "categoria",
    "tipo",
    "prioridade",
    "complexidade",
    "prazo",
    "titulo",
    "descricao"
].forEach(id => {
    const el = document.getElementById(id);

    el.addEventListener("input", () => {
        touched = true;
        validateTicket();
    });

    el.addEventListener("change", () => {
        touched = true;
        validateTicket();
    });
});


// SUBMIT

formTicket.addEventListener("submit", function(e) {
    if (!validateTicket()) {
        e.preventDefault();
    }
});


// INIT (IMPORTANTE: não validar no início)

submitBtn.disabled = true;
submitBtn.classList.add("btn-disabled");