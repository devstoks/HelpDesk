<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.TeamMember"%>
<%@ page import="model.Team"%>
<%@ page import="model.Client"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>

<meta charset="UTF-8">

<title>Team</title>

<link rel="stylesheet" href="css/team.css">
<link rel="stylesheet" href="css/pager.css">

</head>

<body>

	<%
	Client c = (Client) session.getAttribute("client");

	Team team = (Team) request.getAttribute("team");

	List<TeamMember> membros = (List<TeamMember>) request.getAttribute("members");

	boolean isLeader = (request.getAttribute("isLeader") != null) && (boolean) request.getAttribute("isLeader");
	%>

	<!-- ================= HEADER ================= -->

	<header class="top-header">

		<div class="header-left">

			<h2>Team</h2>

			<span> Gerencie os membros do seu time </span>

		</div>

		<div class="user-area">

			<div class="user-avatar">

				<%=c.getNome().charAt(0)%>

			</div>

			<div class="user-info">

				<strong> <%=team.getNome()%>

				</strong> <span>Equipe</span>

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
				<a href="SvClient_Ticket">Tickets</a>
			</li>

			<li>
				<a href="SvClient_Perfil">Perfil</a>
			</li>

			<li>
				<a class="active" href="SvTeamMemberList">Team</a>
			</li>

			<li>
				<a href="SvLogout">Logout</a>
			</li>

		</ul>

	</nav>

	<!-- ================= MAIN ================= -->

	<main>

		<!-- ===== TOPO ===== -->

		<div class="team-top">

			<div>

				<h2 class="team-title">

					<%=team.getNome()%>

				</h2>

				<p class="team-subtitle">Membros e permissões do time</p>

			</div>

			<!-- ===== BOTÃO ADD ===== -->

			<%
			if (isLeader) {
			%>

			<div class="team-actions">

				<button type="button" class="add-member-btn"
					onclick="abrirModalConvite()">+ Adicionar membro</button>

			</div>

			<%
			}
			%>

		</div>

		<!-- ===== LISTA ===== -->

		<div class="team-wrapper">

			<%
			if (membros != null && !membros.isEmpty()) {

				for (TeamMember tm : membros) {
			%>

			<div class="team-card">

				<!-- ===== INFO ===== -->

				<div class="team-info">

					<div class="team-avatar">

						<%=tm.getClient().getNome().charAt(0)%>

					</div>

					<div>

						<div class="team-name">

							<%=tm.getClient().getNome()%>

						</div>

						<div class="team-email">

							<%=tm.getClient().getEmail()%>

						</div>

						<div class="team-role-badge">

							<%=tm.getRole()%>

						</div>

					</div>

				</div>

				<!-- ===== CONTROLES ===== -->

				<div class="team-controls">

					<%
					boolean isSelf = tm.getClient().getId().equals(c.getId());
					%>

					<!-- ===== SOMENTE LÍDER PODE ALTERAR ===== -->

					<%
					if (isLeader && !TeamMember.ROLE_LIDER.equals(tm.getRole())) {
					%>

					<form action="SvUpdateRole" method="post" class="team-role">

						<input type="hidden" name="memberId" value="<%=tm.getId()%>" />

						<select name="role" class="role-select"
							data-original-role="<%=tm.getRole()%>"
							onchange="validarRole(this)">

							<option value="MEMBRO"
								<%=TeamMember.ROLE_MEMBRO.equals(tm.getRole()) ? "selected" : ""%>>

								Membro</option>

							<option value="AGENT"
								<%=TeamMember.ROLE_AGENT.equals(tm.getRole()) ? "selected" : ""%>>

								Agent</option>

						</select>

						<button class="role-btn hidden-save-btn" type="submit">

							Salvar</button>

					</form>

					<%
					}
					%>

					<!-- ===== REMOVER ===== -->

					<%
					if (isLeader && !isSelf) {
					%>

					<form action="SvRemoveMember" method="post">

						<input type="hidden" name="memberId" value="<%=tm.getId()%>" />

						<button class="remove-btn" type="submit">Remover</button>

					</form>

					<%
					}
					%>

				</div>

			</div>

			<%
			}

			} else {
			%>

			<div class="empty-team">

				<h3>Nenhum membro encontrado</h3>

				<p>Seu time ainda não possui integrantes.</p>

			</div>

			<%
			}
			%>

		</div>




		<!-- ================= SAIR DA EQUIPE ================= -->
		<div class="leave-team-wrapper">

			<form action="SvLeaveTeam" method="post">

				<button type="submit" class="leave-team-btn">Sair da equipe

				</button>

			</form>

		</div>

	</main>

	<!-- ================= MODAL CONVITE ================= -->

	<div id="invite-modal" class="invite-modal">

		<div class="invite-content">

			<div class="invite-header">

				<h3>Código de Convite</h3>

				<button class="close-modal" onclick="fecharModalConvite()">

					×</button>

			</div>

			<p class="invite-text">Compartilhe este código para adicionar
				novos membros ao time.</p>

			<div class="invite-code">

				<%=team.getCodigoConvite()%>

			</div>

		</div>

	</div>



	<script type="text/javascript" src="js/team.js"></script>

</body>
</html>