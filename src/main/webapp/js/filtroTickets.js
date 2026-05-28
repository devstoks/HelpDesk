
function filtrarSecao(tipo, botao) {

    /* remove active */
    document.querySelectorAll(".filter-btn")
        .forEach(btn => btn.classList.remove("active"));

    botao.classList.add("active");

    /* seções */
    const pessoal = document.querySelector(".section-pessoal");
    const equipe = document.querySelector(".section-equipe");
    const finalizados = document.querySelector(".section-finalizados");

    /* mostrar tudo */
    if (tipo === "all") {

        if (pessoal) pessoal.style.display = "block";
        if (equipe) equipe.style.display = "block";
        if (finalizados) finalizados.style.display = "block";

        return;
    }

    /* esconder tudo */
    if (pessoal) pessoal.style.display = "none";
    if (equipe) equipe.style.display = "none";
    if (finalizados) finalizados.style.display = "none";

    /* mostrar seção selecionada */
    if (tipo === "pessoal" && pessoal) {
        pessoal.style.display = "block";
    }

    if (tipo === "equipe" && equipe) {
        equipe.style.display = "block";
    }

    if (tipo === "finalizados" && finalizados) {
        finalizados.style.display = "block";
    }
}
