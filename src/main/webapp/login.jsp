<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String msg = (String) request.getAttribute("msg");

Boolean deactivated = (Boolean) request.getAttribute("deactivated");

String emailDeactivated = (String) request.getAttribute("email");
%>

<!DOCTYPE html>
<html lang="pt-br">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Login | HelpDesk Pro</title>

<link rel="stylesheet" type="text/css" href="css/login.css">

<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

</head>

<body>

	<div class="dot-grid"></div>

	<header class="header">

		<nav class="nav">

			<div class="brand">⚡ HelpDesk Pro</div>

			<div class="nav-links">

				<a href="index.jsp">Início</a> <a href="index.jsp#sobre">Sobre</a> <a
					href="index.jsp#como-funciona">Como funciona</a> <a
					href="index.jsp#beneficios">Benefícios</a>

			</div>

		</nav>

	</header>

	<main>

		<div class="login-wrapper">

			<div class="login-container">

				<!-- LEFT -->

				<div class="login-brand">

					<div class="hero-badge">🚀 Sistema de chamados + suporte
						técnico</div>

					<h1>

						Gestão de tickets <br> <span>simples e inteligente</span>

					</h1>

					<p>Centralize solicitações, acompanhe status em tempo real e
						melhore a comunicação entre equipes técnicas e usuários finais.</p>

					<div class="features-list">

						<div class="feature-item">

							<i class="fas fa-check-circle"></i> <span>Dashboard em
								tempo real</span>

						</div>

						<div class="feature-item">

							<i class="fas fa-check-circle"></i> <span>Relatórios
								inteligentes</span>

						</div>

						<div class="feature-item">

							<i class="fas fa-check-circle"></i> <span>Suporte 24/7</span>

						</div>

					</div>

				</div>

				<!-- RIGHT -->

				<div class="login-form-container">

					<div class="form-header">

						<h2>Bem-vindo de volta</h2>

						<p>Entre com suas credenciais</p>

					</div>

					<form class="login-form" action="SvLogin" method="get">

						<!-- EMAIL -->

						<div class="input-group">

							<div class="input-wrapper">

								<i class="fas fa-envelope"></i>

								<input type="email" id="email" name="email"
									placeholder="Seu email" required
									value="<%=request.getParameter("email") != null ? request.getParameter("email") : ""%>">

							</div>

							<small class="input-message" id="emailMessage"></small>

						</div>

						<!-- SENHA -->

						<div class="input-group">

							<div class="input-wrapper">

								<i class="fas fa-lock"></i>

								<input type="password" id="password" name="senha"
									placeholder="Sua senha" required
									value="<%=request.getParameter("senha") != null ? request.getParameter("senha") : ""%>">

								<button type="button" class="toggle-password"
									onclick="togglePassword()">

									<i class="fas fa-eye"></i>

								</button>

							</div>

							<small class="input-message" id="passwordMessage"></small>

						</div>

						<!-- OPTIONS -->

						<div class="form-options">

							<label class="checkbox-label">

								<input type="checkbox" name="remember">

								<span>Lembrar-me</span>

							</label>

							<a href="#" class="forgot-link"> Esqueceu a senha? </a>

						</div>

						<!-- ALERTA BACK -->

						<%
						if (msg != null) {
						%>

						<div
							class="message-alert
							<%=msg.toLowerCase().contains("sucesso") ? "success" : "error"%>">

							<i
								class="fas
								<%=msg.toLowerCase().contains("sucesso") ? "fa-check-circle" : "fa-exclamation-triangle"%>">

							</i> <span><%=msg%></span>

						</div>

						<%
						}
						%>

						<!-- BOTÃO -->

						<%
						if (Boolean.TRUE.equals(deactivated)) {
						%>

					</form>

					<form action="SvReactivateAccount" method="post">

						<input type="hidden" name="email" value="<%=emailDeactivated%>">

						<button type="submit" class="btn-login reactivate-btn">

							<span>Reativar conta</span> <i class="fas fa-rotate-right"></i>

						</button>

					</form>

					<%
					} else {
					%>

					<button type="submit" class="btn-login">

						<span>Entrar agora</span> <i class="fas fa-arrow-right"></i>

					</button>

					</form>

					<%
					}
					%>

					<div class="register-link">

						<p>

							Não tem uma conta? <a href="register.jsp">Crie agora</a>

						</p>

					</div>

				</div>

			</div>

		</div>

	</main>

	<footer class="footer">

		<div class="footer-content">

			<div class="footer-copyright">© 2026 HelpDesk Pro — Plataforma
				de Suporte e Gestão de chamados</div>

			<div class="footer-links">

				<a href="#">Termos de uso</a> <a href="#">Privacidade</a> <a
					href="#">Suporte</a> <a href="#">Status</a>

			</div>

		</div>

	</footer>

	<script type="text/javascript" src="js/validacoes-login.js"></script>

</body>

</html>