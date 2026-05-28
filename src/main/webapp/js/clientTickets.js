function abrirDetalhes(
    role,
    usuarioLogadoId,
    criadorId,

    id,
    codigo,
    titulo,
    descricao,
    categoria,
    prioridade,
    status,
    tipo,
    complexidade,
    resposta,
    responsavel,
    team
) {

    // abre modal
    document.getElementById("modal-overlay").style.display = "flex";

    // seta ids
    document.getElementById("ticket-id").value = id;

    // header
    document.getElementById("modal-codigo").innerText = codigo;
    document.getElementById("modal-status").innerText = status;

    // inputs
    document.getElementById("input-titulo").value = titulo;
    document.getElementById("input-descricao").value = descricao;
    document.getElementById("input-categoria").value = categoria;
    document.getElementById("input-prioridade").value = prioridade;
    document.getElementById("input-status").value = status;
    document.getElementById("input-tipo").value = tipo;
    document.getElementById("input-complexidade").value = complexidade;
    document.getElementById("input-resposta").value = resposta;
    document.getElementById("input-responsavel").value = responsavel;
    document.getElementById("input-team").value = team;

    // area dos botoes
    let actions = document.getElementById("modal-actions");

    actions.innerHTML = "";

    // verifica se é do proprio usuario
    let ehMeuTicket = Number(usuarioLogadoId) === Number(criadorId);

    // verifica se está aberto
    let ticketAberto = status === "ABERTO";

    // =========================
    // CLIENT
    // =========================

    if (role === "CLIENT") {

        // client só edita se:
        // - ticket for dele
        // - ticket estiver aberto

        if (ehMeuTicket && ticketAberto) {

            actions.innerHTML += `
                <button class="btn-editar">
                    Salvar Alterações
                </button>
            `;

            actions.innerHTML += `
                <button class="btn-delete">
                    Excluir
                </button>
            `;
        }

        // client pode finalizar os proprios
        if (ehMeuTicket && status !== "FECHADO") {

            actions.innerHTML += `
                <button class="btn-finalizar">
                    Finalizar
                </button>
            `;
        }
    }

    // =========================
    // AGENT
    // =========================

    if (role === "AGENT") {

        // agent só edita aberto
        if (ticketAberto) {

            actions.innerHTML += `
                <button class="btn-editar">
                    Salvar Alterações
                </button>
            `;
        }

        // agent pode finalizar qualquer um
        if (status !== "FECHADO") {

            actions.innerHTML += `
                <button class="btn-finalizar">
                    Finalizar
                </button>
            `;
        }
    }

    // =========================
    // LIDER
    // =========================

    if (role === "LIDER") {

        actions.innerHTML += `
            <button class="btn-editar">
                Salvar Alterações
            </button>
        `;

        actions.innerHTML += `
            <button class="btn-delete">
                Excluir
            </button>
        `;

        if (status !== "FECHADO") {

            actions.innerHTML += `
                <button class="btn-finalizar">
                    Finalizar
                </button>
            `;
        }
    }

}