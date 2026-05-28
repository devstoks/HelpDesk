<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.Client"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Ticket"%>
<%@ page import="model.Team"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>

<meta charset="UTF-8">

<title>Menu Client</title>

<link rel="stylesheet" type="text/css" href="css/menuDashboard.css">

<link rel="stylesheet" type="text/css" href="css/pager.css">

</head>

<body>

	<header class="top-header">

		<div class="header-left">

			<h2>Dashboard</h2>

			<span>Acompanhe os detalhes dos seus tickets</span>

		</div>

		<div class="header-right">

			<div class="user-area">

				<div class="user-avatar">M</div>

				<div class="user-info">

					<%
					Team team = (Team) session.getAttribute("team");
					%>

					<strong><%=team.getNome()%></strong> <span>Time</span>

				</div>

			</div>

		</div>

	</header>


	<nav class="sidebar">

		<div class="logo">

			<h1>Help Desk</h1>

		</div>

		<ul class="menu">

			<li>
				<a class="active" href="SvDashboard">Dashboard</a>
			</li>

			<li>
				<a href="SvClient_Ticket">Tickets</a>
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


	<main>

		<%
		Client c = (Client) request.getAttribute("client");
		%>

		<!-- TOPO -->
		<div class="main-content">

			<div class="header-left">

				<h1 class="welcome-title">
					Bem vindo
					<%=c.getNome()%>
				</h1>

				<p class="welcome-subtitle">Visualize métricas, acompanhe
					tickets e abra novas solicitações.</p>

			</div>

			<div class="header-right">

				<button class="open-modal-btn" onclick="abrirTicket()">+
					Criar Ticket</button>

			</div>

		</div>


		<!-- CARDS -->
		<h3 class="section-title">SOBRE OS TICKETS</h3>

		<div class="dashboard-cards">

			<div id="qtdTotal">

				<span>Total</span> <strong><%=request.getAttribute("total")%></strong>

			</div>

			<div id="emAberto">

				<span>Abertos</span> <strong><%=request.getAttribute("aberto")%></strong>

			</div>

			<div id="emAndamento">

				<span>Em andamento</span> <strong><%=request.getAttribute("andamento")%></strong>

			</div>

			<div id="fechado">

				<span>Fechados</span> <strong><%=request.getAttribute("fechado")%></strong>

			</div>

		</div>


		<!-- ULTIMOS -->
		<h2 class="section-title">ÚLTIMOS ABERTOS</h2>

		<div class="tickets-wrapper">

			<%
			List<Ticket> tickets = (List<Ticket>) request.getAttribute("ultimosTickets");

			if (tickets != null && !tickets.isEmpty()) {

				for (Ticket t : tickets) {
			%>

			<div class="ticket-card">

				<div class="ticket-top">

					<div class="ticket-tags">

						<span class="tag-category"> <%=t.getCategoria()%>
						</span> <span class="tag-priority"> <%=t.getPrioridade()%>
						</span>

					</div>

					<span class="ticket-code"> <%=t.getCodigo() != null ? t.getCodigo() : "TICKET"%>

					</span>

				</div>

				<div class="ticket-body">

					<h3><%=t.getTitulo()%></h3>

					<p><%=t.getDescricao()%></p>

				</div>

				<div class="ticket-info">

					<div class="info-box">

						<small>Status</small> <span><%=t.getStatus()%></span>

					</div>

					<div class="info-box">

						<small>Data</small> <span> <%=t.getDataCriacao() != null ? t.getDataCriacao().toLocalDate() : "-"%>

						</span>

					</div>

				</div>

			</div>

			<%
			}
			}
			%>

		</div>

	</main>


	<!-- OVERLAY -->
	<div id="modal-overlay" onclick="fecharTicketOverlay(event)">
		<div id="modal-ticket" onclick="event.stopPropagation()">

			<button class="close-btn" onclick="fecharTicket()">×</button>

			<div class="modal-header">
				<div>
					<h2>Criar Novo Ticket</h2>
					<p>Descreva o problema, solicitação ou atividade para sua
						equipe.</p>
				</div>

				<div class="modal-status">
					<span class="status-badge status-open">ABERTO</span>
				</div>
			</div>

			<form action="SvCreateTicket" method="get" id="display-ticket">

				<!-- LINHA 1 -->
				<div class="modal-row">

					<div class="field-group">
						<label for="categoria">📁 Categoria</label>

						<select class="modal-input" name="categoria" id="categoria">
							<option value="">Selecione</option>
							<option value="DESENVOLVIMENTO">Desenvolvimento</option>
							<option value="SUPORTE">Suporte</option>
							<option value="DESIGN">Design</option>
							<option value="AUDIOVISUAL">Audiovisual</option>
							<option value="OPERACIONAL">Operacional</option>
							<option value="OUTROS">Outros</option>
						</select> <small class="error-message" id="categoriaMessage"></small>
					</div>

					<div class="field-group">
						<label for="tipo">🏷️ Tipo</label>

						<select class="modal-input" name="tipo" id="tipo">
							<option value="">Selecione</option>
							<option value="BUG">Bug</option>
							<option value="TASK">Task</option>
							<option value="FEATURE">Feature</option>
						</select> <small class="error-message" id="tipoMessage"></small>
					</div>

					<div class="field-group">
						<label for="prioridade">⚡ Prioridade</label>

						<select class="modal-input" name="prioridade" id="prioridade">
							<option value="BAIXA">Baixa</option>
							<option value="MEDIA">Média</option>
							<option value="ALTA">Alta</option>
							<option value="URGENTE">Urgente</option>
						</select> <small class="error-message" id="prioridadeMessage"></small>
					</div>

				</div>

				<!-- LINHA 2 -->
				<div class="modal-row">

					<div class="field-group">
						<label for="complexidade">📊 Complexidade</label>

						<select class="modal-input" name="complexidade" id="complexidade">
							<option value="">Selecione</option>
							<option value="SIMPLES">Simples</option>
							<option value="MEDIA">Média</option>
							<option value="COMPLEXA">Complexa</option>
						</select> <small class="error-message" id="complexidadeMessage"></small>
					</div>

					<div class="field-group">
						<label for="prazo">📅 Prazo</label>

						<input class="modal-input" type="datetime-local" name="prazo"
							id="prazo" />

						<small class="error-message" id="prazoMessage"></small>
					</div>

				</div>

				<!-- TÍTULO -->
				<div class="field-group field-full">
					<label for="titulo">✏️ Título do Ticket</label>

					<input class="modal-input" id="titulo" name="titulo"
						placeholder="Ex: Ajustar iluminação da cena" type="text" />

					<small class="error-message" id="tituloMessage"></small>
				</div>

				<!-- DESCRIÇÃO -->
				<div class="field-group field-full">
					<label for="descricao">📝 Descrição</label>

					<textarea class="modal-textarea" id="descricao" name="descricao"
						placeholder="Explique detalhadamente o ticket..."></textarea>

					<small class="error-message" id="descricaoMessage"></small>
				</div>

				<!-- FOOTER -->
				<div class="modal-footer">
					<div class="modal-info">
						<span class="info-brand">🎫 Help Desk Platform</span> <small>Tickets
							ajudam sua equipe a organizar fluxos e tarefas.</small>
					</div>

					<button class="form-btn" type="submit">✨ Abrir Ticket</button>
				</div>

			</form>

		</div>
	</div>


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

	<!-- ================= DEV SWITCH MODAL ================= -->
	<div id="devSwitchModal" class="dev-modal">

		<div class="dev-buttons">

			<!-- Conta 1 -->
			<button class="dev-btn dev-btn-1"
				onclick="switchAccount('ma@gmail.com', '12345678')" title="Conta 1">
			</button>

			<!-- Conta 2 -->
			<button class="dev-btn dev-btn-2"
				onclick="switchAccount('vi@gmail.com', '12345678')" title="Conta 2">
			</button>

		</div>

	</div>

	<script type="text/javascript">
	
	// ================= SWITCH DE CONTA =================

function switchAccount(email, senha) {

    // opcional: limpar sessão atual primeiro
    fetch("SvLogout", {
        method: "POST"
    }).finally(() => {

        // redireciona para login automático
        const form = document.createElement("form");
        form.method = "POST";
        form.action = "SvLogin";

        const emailInput = document.createElement("input");
        emailInput.name = "email";
        emailInput.value = email;

        const senhaInput = document.createElement("input");
        senhaInput.name = "senha";
        senhaInput.value = senha;

        form.appendChild(emailInput);
        form.appendChild(senhaInput);

        document.body.appendChild(form);
        form.submit();
    });
}
	</script>

	<script type="text/javascript" src="js/validacoes-createTicket.js"></script>
	<script type="text/javascript" src="js/screenTicket.js"></script>

</body>
</html>