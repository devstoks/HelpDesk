function validarRole(select) {

    const roleOriginal =
        select.dataset.originalRole;

    const roleAtual =
        select.value;

    const form =
        select.closest("form");

    const botao =
        form.querySelector(".role-btn");

    if (roleAtual === roleOriginal) {

        botao.classList.remove("active");

    } else {

        botao.classList.add("active");
    }
}


function abrirModalConvite() {

    document
        .getElementById("invite-modal")
        .classList.add("active");
}

function fecharModalConvite() {

    document
        .getElementById("invite-modal")
        .classList.remove("active");
}

function copiarCodigo() {

    const codigo =
        "<%=team.getCodigoConvite()%>";

    navigator.clipboard.writeText(codigo);

    alert("Código copiado!");
}