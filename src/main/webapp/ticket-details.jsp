<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.Ticket"%>
<%@ page import="model.Team"%>
<%@ page import="model.Client"%>
<%@ page import="model.TeamMember"%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

<meta charset="UTF-8">

<title>Detalhes do Ticket</title>

<link rel="stylesheet" href="css/pager.css">
<link rel="stylesheet" href="css/ticket-details.css">

</head>

<body>

	<%
	Ticket ticket = (Ticket) request.getAttribute("ticket");

	Client client = (Client) session.getAttribute("client");

	String role = (String) request.getAttribute("role");

	Team team = (Team) session.getAttribute("team");

	// ============ -- ROLES -- ===================== // 

	boolean isLider = TeamMember.ROLE_LIDER.equals(role);

	boolean isAgent = TeamMember.ROLE_AGENT.equals(role);

	boolean isMembro = TeamMember.ROLE_MEMBRO.equals(role);

	// ============ -- RELAÇÕES -- ===================== // 

	boolean isCriador = ticket.getCriador() != null && ticket.getCriador().getId().equals(client.getId());

	boolean possuiResponsavel = ticket.getResponsavel() != null;

	boolean isResponsavel = possuiResponsavel && ticket.getResponsavel().getId().equals(client.getId());

	// ============ -- STATUS -- ===================== // 

	boolean ticketAberto = Ticket.STATUS_ABERTO.equalsIgnoreCase(ticket.getStatus());

	boolean ticketAndamento = Ticket.STATUS_EM_ANDAMENTO.equalsIgnoreCase(ticket.getStatus());

	boolean ticketFechado = Ticket.STATUS_FECHADO.equalsIgnoreCase(ticket.getStatus());

	// ============ -- ASSUMIR -- ===================== //
	/*
	 * Regras para assumir ticket:
	 *
	 * LÍDER:
	 * Pode assumir qualquer ticket aberto.
	 *
	 * AGENT:
	 * Pode assumir qualquer ticket aberto.
	 *
	 * MEMBRO:
	 * NÃO pode assumir tickets.
	 * Apenas visualiza tickets da equipe.
	 */

	boolean podeAssumir = false;

	/*
	 * Só permite assumir se:
	 *
	 * - for LÍDER ou AGENT
	 * - ticket estiver ABERTO
	 * - ticket ainda NÃO possuir responsável
	 */

	if ((isLider || isAgent) && ticketAberto && !possuiResponsavel) {

		podeAssumir = true;
	}
	// ============ -- EDITAR -- ===================== // 

	boolean podeEditar = false;

	if (isLider) {

		podeEditar = true;
	}

	else if (isAgent && !ticketFechado) {

		podeEditar = true;
	}

	else if (isMembro && isCriador && ticketAberto) {

		podeEditar = true;
	}

	// ============ -- FINALIZAR -- ===================== // 

	boolean podeFinalizar = false;

	if (ticketAndamento && possuiResponsavel && isResponsavel) {

		podeFinalizar = true;
	}

	// ============ -- PRIORIDADE -- ===================== // 

	String prioridadeClass = ticket.getPrioridade().toLowerCase();
	%>


	<%
	// ============ -- FORMANTANDO HORA -- ===================== // 
	String prazoFormatado = "";

	if (ticket.getPrazo() != null) {

		prazoFormatado = ticket.getPrazo().toString().substring(0, 16);
	}
	%>


	<!-- ================= HEADER ================= -->

	<header class="top-header">

		<div class="header-left">

			<h2>Detalhes do Ticket</h2>

			<span> Visualize e gerencie as informações do ticket </span>

		</div>

		<div class="header-right">

			<div class="user-area">

				<div class="user-avatar">M</div>

				<div class="user-info">

					<strong> <%=team != null ? team.getNome() : "Sem Team"%>
					</strong> <span>Time</span>

				</div>

			</div>

		</div>

	</header>

	<!-- ================= SIDEBAR ================= -->

	<nav class="sidebar">

		<div class="logo">

			<h1>Help Desk</h1>

		</div>

		<ul class="menu">

			<li>
				<a href="SvDashboard">Dashboard</a>
			</li>

			<li>
				<a class="active" href="SvClient_Ticket">Tickets </a>
			</li>

			<li>
				<a href="SvClient_Perfil">Perfil</a>
			</li>

			<li>
				<a href="SvTeamMemberList">Team</a>
			</li>

			<li>
				<a href="SvLogout">Logout</a>
			</li>

		</ul>

	</nav>

	<!-- ================= MAIN ================= -->

	<main>

		<section class="ticket-container">

			<form action="SvUpdateTicket" method="post">

				<input type="hidden" name="id" value="<%=ticket.getId()%>">

				<input type="hidden" name="motivoDelecao" id="motivoDelecao">



				<!-- ================= RESPONSÁVEL BANNER ================= -->

				<%
				if (possuiResponsavel) {
				%>

				<div class="responsavel-banner">

					<strong>Assumido por:</strong> <span> <%=ticket.getResponsavel().getNome()%>
					</span>

				</div>

				<%
				}
				%>




				<!-- ================= PRIORIDADE ================= -->

				<div class="urgencia-banner
					urgencia-<%=prioridadeClass%>">

					<strong>Prioridade:</strong>

					<%=ticket.getPrioridade()%>

				</div>

				<!-- ================= COMPLEXIDADE ================= -->

				<div
					class="complexidade-banner
					complexidade-<%=ticket.getComplexidade() != null ? ticket.getComplexidade().toLowerCase() : "simples"%>">

					<strong>Complexidade:</strong>

					<%=ticket.getComplexidade() != null ? ticket.getComplexidade() : "Não definida"%>

				</div>

				<!-- ================= HEADER ================= -->

				<div class="ticket-top">

					<div class="ticket-title-area">

						<div class="ticket-code">
							#TICKET-<%=ticket.getId()%>
						</div>

						<h1>
							<%=ticket.getTitulo()%>
						</h1>

						<p class="ticket-subtitle">Visualize, acompanhe e gerencie
							todas as informações deste ticket.</p>

					</div>

					<div class="ticket-status-area">

						<span
							class="status-badge
			status-<%=ticket.getStatus().toLowerCase().replace(" ", "_")%>">

							<%=ticket.getStatus()%>

						</span>

					</div>

				</div>

				<!-- ================= GRID ================= -->

				<div class="ticket-grid">

					<!-- TITULO -->
					<div class="field-group full-width">

						<label>✏️ Título</label>

						<input type="text" id="titulo" name="titulo" class="form-input"
							value="<%=ticket.getTitulo()%>"
							<%=!podeEditar ? "readonly" : ""%>>

						<small class="error-message" id="tituloError"></small>

					</div>

					<!-- CATEGORIA -->
					<div class="field-group">

						<label>📁 Categoria</label>

						<select id="categoria" name="categoria" class="form-input"
							<%=!podeEditar ? "disabled" : ""%>>

							<option value="SUPORTE"
								<%=ticket.getCategoria().equals("SUPORTE") ? "selected" : ""%>>SUPORTE</option>
							<option value="BUG"
								<%=ticket.getCategoria().equals("BUG") ? "selected" : ""%>>BUG</option>
							<option value="FEATURE"
								<%=ticket.getCategoria().equals("FEATURE") ? "selected" : ""%>>FEATURE</option>
							<option value="TASK"
								<%=ticket.getCategoria().equals("TASK") ? "selected" : ""%>>TASK</option>

						</select> <small class="error-message" id="categoriaError"></small>

					</div>

					<!-- PRIORIDADE -->
					<div class="field-group">

						<label>⚡ Prioridade</label>

						<select id="prioridade" name="prioridade" class="form-input"
							<%=!podeEditar ? "disabled" : ""%>>

							<option value="BAIXA"
								<%=ticket.getPrioridade().equals("BAIXA") ? "selected" : ""%>>BAIXA</option>
							<option value="MEDIA"
								<%=ticket.getPrioridade().equals("MEDIA") ? "selected" : ""%>>MEDIA</option>
							<option value="ALTA"
								<%=ticket.getPrioridade().equals("ALTA") ? "selected" : ""%>>ALTA</option>
							<option value="URGENTE"
								<%=ticket.getPrioridade().equals("URGENTE") ? "selected" : ""%>>URGENTE</option>

						</select> <small class="error-message" id="prioridadeError"></small>

					</div>

					<!-- TIPO -->
					<div class="field-group">

						<label>🏷️ Tipo</label>

						<input type="text" id="tipo" name="tipo" class="form-input"
							value="<%=ticket.getTipo()%>" <%=!podeEditar ? "readonly" : ""%>>

						<small class="error-message" id="tipoError"></small>

					</div>

					<!-- COMPLEXIDADE -->
					<div class="field-group">

						<label>📊 Complexidade</label>

						<input type="text" id="complexidade" name="complexidade"
							class="form-input" value="<%=ticket.getComplexidade()%>"
							<%=!podeEditar ? "readonly" : ""%>>

						<small class="error-message" id="complexidadeError"></small>

					</div>

					<!-- PRAZO -->
					<div class="field-group">

						<label>📅 Prazo</label>

						<input type="datetime-local" id="prazo" name="prazo"
							class="form-input"
							value="<%=ticket.getPrazo() != null ? ticket.getPrazo().toString().replace(" ", "T").substring(0, 16) : ""%>"
							<%=!podeEditar ? "readonly" : ""%>>

						<small class="error-message" id="prazoError"></small>

					</div>

				</div>

				<!-- DESCRIÇÃO -->
				<div class="field-group description-box">

					<label>📝 Descrição</label>

					<textarea id="descricao" name="descricao" class="form-textarea"
						<%=!podeEditar ? "readonly" : ""%>><%=ticket.getDescricao()%></textarea>

					<small class="error-message" id="descricaoError"></small>

				</div>

				<!-- ACTIONS -->
				<div class="ticket-actions">

					<a href="SvClient_Ticket" class="btn-secondary">Voltar</a>

					<%
					if (podeAssumir) {
					%>
					<button type="submit" name="acao" value="assumir"
						class="btn-warning">Assumir Ticket</button>
					<%
					}
					%>

					<%
					if (podeEditar) {
					%>
					<button type="submit" name="acao" value="salvar"
						class="btn-primary">Salvar Alterações</button>
					<%
					}
					%>

					<%
					if (podeFinalizar && !ticketFechado) {
					%>
					<button type="button" class="btn-finish"
						onclick="toggleFinalizar()">Finalizar Ticket</button>
					<%
					}
					%>

				</div>

				<!-- FINALIZAÇÃO -->
				<%
				if (podeFinalizar && !ticketFechado) {
				%>

				<div id="finalizar-box" class="finish-box">

					<h3>Finalizar Ticket</h3>

					<p>Informe a solução aplicada antes de finalizar.</p>

					<div class="field-group">

						<label>Resposta Final</label>

						<textarea name="respostaFinal" class="form-textarea"></textarea>

					</div>

					<button type="submit" name="acao" value="finalizar"
						class="btn-success">Confirmar Finalização</button>

				</div>

				<%
				}
				%>

			</form>

		</section>

	</main>

	<!-- ================= FOOTER ================= -->

	<footer class="footer">

		<div class="footer-content">

			<div class="footer-brand">

				<h3>Help Desk</h3>

				<p>Plataforma de gerenciamento e resolução de tarefas.</p>

			</div>

		</div>

		<div class="footer-bottom">

			<span> © 2026 Help Desk Platform — Todos os direitos
				reservados. </span>

		</div>

	</footer>

<script type="text/javascript" src="js/validacoes-detalhesTickets.js"></script>

	<script>

		function toggleFinalizar() {

			const box =
				document.getElementById("finalizar-box");

			if (box) {

				box.classList.toggle("active");
			}
		}

		function confirmarExclusao() {

			const motivo =
				prompt("Informe o motivo da exclusão:");

			if (motivo == null || motivo.trim() === "") {

				alert("Você precisa informar um motivo.");
				return;
			}

			const confirmar =
				confirm("Tem certeza que deseja excluir este ticket?");

			if (!confirmar) {

				return;
			}

			document.getElementById("motivoDelecao").value = motivo;

			const form =
				document.querySelector("form");

			const input =
				document.createElement("input");

			input.type = "hidden";
			input.name = "acao";
			input.value = "excluir";

			form.appendChild(input);

			form.submit();
		}

	</script>

</body>
</html>