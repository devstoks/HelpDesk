
// ELEMENTOS


const formTicket = document.getElementById("display-ticket");

const submitBtn = document.querySelector(".btn-primary"); // SALVAR ALTERAÇÕES
const assumeBtn = document.querySelector(".btn-warning");  // ASSUMIR TICKET



// HELPERS VISUAIS


function setErrorField(id, message) {
    const el = document.getElementById(id);
    if (!el) return;

    el.classList.add("input-error");
    el.classList.remove("input-success");

    console.log(message);
}

function setSuccessField(id) {
    const el = document.getElementById(id);
    if (!el) return;

    el.classList.remove("input-error");
    el.classList.add("input-success");
}



// NORMALIZAÇÃO DE TEXTO (SEM REGEX)


function normalizarTexto(valor) {
    let result = "";

    for (let i = 0;i < valor.length;i++) {
        const c = valor[i];

        // remove espaços duplicados
        if (c === " " && result[result.length - 1] === " ") continue;

        result += c;
    }

    return result.trim();
}



// VALIDAÇÃO BÁSICA DE TEXTO


function textoValidoBasico(valor) {
    let letras = 0;
    let outros = 0;

    for (let i = 0;i < valor.length;i++) {
        const c = valor[i];

        const isLetter =
            (c >= "a" && c <= "z") ||
            (c >= "A" && c <= "Z") ||
            (c >= "À" && c <= "ÿ") ||
            c === " ";

        if (isLetter) letras++;
        else outros++;
    }

    if (letras < 3) return false;
    if (outros > letras) return false;

    return true;
}



// VALIDAÇÕES INDIVIDUAIS


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

    let value = normalizarTexto(el.value);
    el.value = value;

    if (value.length < 5) {
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

    let value = normalizarTexto(el.value);
    el.value = value;

    if (value.length === 0) {
        setErrorField("descricao", "Descrição obrigatória");
        return false;
    }

    if (value.length < 10) {
        setErrorField("descricao", "Descrição muito curta");
        return false;
    }

    setSuccessField("descricao");
    return true;
}



// VALIDAÇÃO GERAL (SALVAR)


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

    if (submitBtn) {
        submitBtn.disabled = !isValid;

        if (!isValid) {
            submitBtn.classList.add("btn-disabled");
        } else {
            submitBtn.classList.remove("btn-disabled");
        }
    }

    return isValid;
}



// CONTROLE DO BOTÃO "ASSUMIR TICKET"


function updateAssumeButtonState(canAssume) {
    if (!assumeBtn) return;

    assumeBtn.disabled = !canAssume;

    if (!canAssume) {
        assumeBtn.classList.add("btn-disabled");
    } else {
        assumeBtn.classList.remove("btn-disabled");
    }
}

function evaluateAssumePermission() {
    const status = document.querySelector(".status-badge")?.innerText?.trim();
    const responsavel = document.querySelector(".responsavel-banner");

    const isAberto = status === "ABERTO";
    const hasResponsavel = !!responsavel;

    const canAssume = isAberto && !hasResponsavel;

    updateAssumeButtonState(canAssume);

    return canAssume;
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

    if (!el) return;

    el.addEventListener("input", validateTicket);
    el.addEventListener("change", validateTicket);
});



// SUBMIT BLOCK


formTicket.addEventListener("submit", function(e) {
    if (!validateTicket()) {
        e.preventDefault();
    }
});



// INIT


window.addEventListener("load", () => {
    validateTicket();
    evaluateAssumePermission();
});