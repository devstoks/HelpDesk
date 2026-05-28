<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="UTF-8">
<title>Configurar Team</title>

<link rel="stylesheet" href="css/teamsetup.css">
<link rel="stylesheet" href="css/pager.css">

</head>

<body>

	<!-- HEADER -->
	<header class="main-header">
		<div class="header-content">
			<h1>Help Desk</h1>

			<nav>
				<a href="index.jsp">Início</a> <a href="login.jsp">Login</a>
			</nav>
		</div>
	</header>

	<!-- MAIN -->
	<main class="setup-main">

		<div class="setup-container">

			<h1 class="logo">Bem-vindo ao seu Workspace</h1>

			<p class="subtitle">
				Você ainda não participa de nenhum time.<br> Crie um novo team
				ou entre utilizando um código de convite.
			</p>

			<div class="setup-grid">

				<!-- CRIAR TEAM -->
				<div class="setup-card">

					<h2>Criar Team</h2>

					<form action="SvCreateTeam" method="get">

						<input type="text" name="nome" placeholder="Nome do Team" required>

						<button type="submit">Criar Team</button>

					</form>

				</div>

				<!-- ENTRAR EM TEAM -->
				<div class="setup-card">

					<h2>Entrar em Team</h2>

					<form action="SvJoinTeam" method="post">

						<input type="text" name="codigo" placeholder="Código do convite"
							required>

						<button type="submit">Entrar no Team</button>

					</form>

				</div>

			</div>

			<!-- MENSAGEM -->
			<div class="msg-box">
				<%=request.getAttribute("msg") != null ? request.getAttribute("msg") : ""%>
			</div>

		</div>

	</main>

	<!-- FOOTER -->
	<footer class="main-footer">

		<div class="footer-content">

			<p>Help Desk • Plataforma colaborativa de tickets e resolução de
				problemas</p>

		</div>

	</footer>

</body>
</html>