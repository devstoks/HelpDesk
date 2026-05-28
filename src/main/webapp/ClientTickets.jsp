<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%@ page import="model.Ticket"%>
<%@ page import="model.Team"%>
<%@ page import="model.Client"%>
<%@ page import="model.TeamMember"%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

<meta charset="UTF-8">

<title>Exibir Tickets</title>

<link rel="stylesheet" href="css/pager.css">
<link rel="stylesheet" href="css/exibirTickets.css">

</head>

<body>
	<!-- iMPORTANDO INFORMAÇÕES PARA EXIBIR NA PÁGINA -->
	<%
	Client client = (Client) request.getAttribute("client"); // IMPORTA O CLIENT

	String role = (String) request.getAttribute("role"); // IMPORTA OS CARGOS

	List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets"); // IMPORTA OS TICKETS

	Team team = (Team) session.getAttribute("team"); // IMPORTA O TIME

	// FAZ A SEPARAÇÃO NAS ARRAYS
	List<Ticket> meusTickets = new ArrayList<>();

	List<Ticket> ticketsEquipe = new ArrayList<>();

	List<Ticket> ticketsFechados = new ArrayList<>();

	// VALIDA SE HÁ TICKET
	if (tickets != null) {

		// CASO TENHA, FAZ UM LOOP PEGANDO INFORMAÇÕES DOS TICKETS
		for (Ticket t : tickets) {

			boolean ehMeuTicket = t.getCriador() != null && t.getCriador().getId().equals(client.getId()); // BUSCA OS CRIADORES

			boolean fechado = "FECHADO".equalsIgnoreCase(t.getStatus()); // BUSCA OS TICKETS FECHADOS

			// CASO TENHA FECHADO, ADICIONA NA LISTA
			if (fechado) {
		ticketsFechados.add(t);
			}

			else {

		// CASO TENHA ALGUM TICKET DO PRÓPRIO USUÁRIO, ADICIONA NA LISTA
		if (ehMeuTicket) {
			meusTickets.add(t);
		}

		// CASO TENHA ALGUM TICKET DA EQUIPE, ADICIONA NA LISTA
		else {
			ticketsEquipe.add(t);
		}
			}
		}
	}
	%>

	<!-- =================== HEADER: CABEÇALHO DE TOPO ================================== -->

	<header class="top-header">

		<div class="header-left">

			<h2>Exibir Tickets</h2>

			<span> Acompanhe todos os tickets da plataforma </span>

		</div>

		<div class="header-right">

			<div class="header-search">

				<input type="text" placeholder="Buscar tickets...">

			</div>

			<div class="user-area">

				<div class="user-avatar">M</div>

				<div class="user-info">

					<strong> <%=team != null ? team.getNome() : "Sem Team"%>
					</strong> <span>Time</span>

				</div>

			</div>

		</div>

	</header>

	<!-- ================== SIDEBAR: BARRA LATERAL LEFT =================================== -->

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

	<!-- =================== MAIN : ESTRUTURA PRINCIPAL DA PÁGINA ================================== -->

	<main>

		<!-- ================== BOTÕES DE FILTRO =============================== -->

		<div class="ticket-filters">

			<button class="filter-btn active" onclick="filtrarSecao('all', this)">
				All</button>

			<button class="filter-btn" onclick="filtrarSecao('pessoal', this)">
				Pessoal</button>

			<button class="filter-btn" onclick="filtrarSecao('equipe', this)">
				Equipe</button>

			<button class="filter-btn"
				onclick="filtrarSecao('finalizados', this)">Finalizados</button>

		</div>

		<!-- ================= EXIBIR ÁREA PESSOAL ================================ -->

		<section class="ticket-section section-pessoal">

			<div class="section-header">

				<div class="section-title">
					<h2>Área Pessoal</h2>
					<p>Aqui aparecem apenas os tickets criados por você.</p>
				</div>

				<div class="section-counter">
					<%=meusTickets.size()%>
				</div>

			</div>

			<div class="ticket-grid">

				<%
				if (!meusTickets.isEmpty()) {
					for (Ticket t : meusTickets) {
				%>

				<div class="ticket-card">

					<div class="ticket-tags">

						<span class="tag-categoria"> <%=t.getCategoria()%>
						</span> <span
							class="tag-prioridade prioridade-<%=t.getPrioridade().toLowerCase()%>">
							<%=t.getPrioridade()%>
						</span>

					</div>

					<h3><%=t.getTitulo()%></h3>

					<p>
						<%=t.getDescricao().length() > 70 ? t.getDescricao().substring(0, 70) + "..." : t.getDescricao()%>
					</p>

					<div class="ticket-footer">

						<span
							class="status-badge status-<%=t.getStatus().toLowerCase().replace(" ", "_")%>">
							<%=t.getStatus()%>
						</span> <a class="ticket-detail" href="SvTicketDetails?id=<%=t.getId()%>">
							Ver detalhes </a>

					</div>

				</div>

				<%
				}
				} else {
				%>

				<div class="empty-state">

					<h3>Nenhum ticket pessoal encontrado.</h3>

					<p>Você ainda não possui tickets abertos.</p>

				</div>

				<%
				}
				%>

			</div>

		</section>


		<!-- ================= EXIBIR ÁREA DA EQUIPE ================================ -->

		<%
		if (role.equals(TeamMember.ROLE_AGENT) || role.equals(TeamMember.ROLE_LIDER) || role.equals(TeamMember.ROLE_MEMBRO)) {
		%>

		<section class="ticket-section section-equipe">

			<div class="section-header">

				<div class="section-title">
					<h2>Área da Equipe</h2>
					<p>Tickets criados pelos membros da equipe.</p>
				</div>

				<div class="section-counter">
					<%=ticketsEquipe.size()%>
				</div>

			</div>

			<div class="ticket-grid">

				<%
				if (!ticketsEquipe.isEmpty()) {

					for (Ticket t : ticketsEquipe) {
				%>

				<div class="ticket-card">

					<div class="ticket-tags">

						<span class="tag-categoria"> <%=t.getCategoria()%>
						</span> <span
							class="tag-prioridade prioridade-<%=t.getPrioridade().toLowerCase()%>">
							<%=t.getPrioridade()%>
						</span>

					</div>

					<h3><%=t.getTitulo()%></h3>

					<!-- DESCRIÇÃO -->
					<p>
						<%=t.getDescricao().length() > 70 ? t.getDescricao().substring(0, 70) + "..." : t.getDescricao()%>
					</p>

					<div class="ticket-criador">

						<strong>Criado por</strong> <span> <%=t.getCriador().getNome()%>
						</span>

					</div>

					<!-- RESPONSÁVEL -->
					<%
					if (t.getResponsavel() != null) {
					%>

					<div class="ticket-responsavel">

						<strong>Assumido por</strong> <span> <%=t.getResponsavel().getNome()%>
						</span>

					</div>

					<%
					}
					%>

					<div class="ticket-footer">

						<!-- STATUS -->
						<span
							class="status-badge status-<%=t.getStatus().toLowerCase().replace(" ", "_")%>">
							<%=t.getStatus()%>
						</span> <a class="ticket-detail" href="SvTicketDetails?id=<%=t.getId()%>">

							Ver detalhes </a>

					</div>

				</div>

				<%
				}
				} else {
				%>

				<div class="empty-state">

					<h3>Nenhum ticket da equipe.</h3>

					<p>Não existem tickets ativos da equipe.</p>

				</div>

				<%
				}
				%>

			</div>

		</section>

		<%
		}
		%>

		<!-- ============== EXIBIR TICKETS FINALIZADOS =================================== -->

		<section class="ticket-section section-finalizados">

			<div class="section-header">

				<div class="section-title">
					<h2>Tickets Finalizados</h2>
					<p>Tickets encerrados e resolvidos.</p>
				</div>

				<div class="section-counter">
					<%=ticketsFechados.size()%>
				</div>

			</div>

			<div class="ticket-grid">

				<%
				if (!ticketsFechados.isEmpty()) {

					for (Ticket t : ticketsFechados) {
				%>

				<div class="ticket-card">

					<div class="ticket-tags">

						<span class="tag-categoria"> <%=t.getCategoria()%>
						</span> <span
							class="tag-prioridade prioridade-<%=t.getPrioridade().toLowerCase()%>">
							<%=t.getPrioridade()%>
						</span>

					</div>

					<h3><%=t.getTitulo()%></h3>

					<!-- DESCRIÇÃO -->
					<p>
						<%=t.getDescricao().length() > 70 ? t.getDescricao().substring(0, 70) + "..." : t.getDescricao()%>
					</p>

					<!-- RESPONSÁVEL -->
					<%
					if (t.getResponsavel() != null) {
					%>

					<div class="ticket-responsavel">

						<strong>Finalizado por</strong> <span> <%=t.getResponsavel().getNome()%>
						</span>

					</div>

					<%
					}
					%>

					<div class="ticket-footer">

						<span class="status-badge status-fechado"> FECHADO </span> <a
							class="ticket-detail" href="SvTicketDetails?id=<%=t.getId()%>">

							Ver detalhes </a>

					</div>

				</div>

				<%
				}
				} else {
				%>

				<div class="empty-state">

					<h3>Nenhum ticket finalizado.</h3>

					<p>Ainda não existem tickets encerrados.</p>

				</div>

				<%
				}
				%>

			</div>

		</section>

	</main>


	<!-- ================ FOOTER ===================================== -->

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

	<script type="text/javascript" src="js/clientTickets.js"></script>
	<script type="text/javascript" src="js/filtroTickets.js"></script>

</body>

</html>