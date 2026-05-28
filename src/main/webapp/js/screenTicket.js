function abrirTicket() {
    document.getElementById("modal-overlay").style.display = "flex";
}

function fecharTicket() {
    document.getElementById("modal-overlay").style.display = "none";
}

/* fechar clicando fora */
document.getElementById("modal-overlay").addEventListener("click", function(e) {
    if (e.target === this) {
        fecharTicket();
    }
});