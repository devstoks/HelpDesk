<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.Client"%>
<%@ page import="model.Team"%>

<%
Client client = (Client) session.getAttribute("client");

Team team = (Team) session.getAttribute("team");
%>

<!DOCTYPE html>
<html lang="pt-BR">

<head>

<meta charset="UTF-8">

<title>Perfil</title>

<link rel="stylesheet" href="css/pager.css">
<link rel="stylesheet" href="css/perfil.css">

</head>

<body>

	<!-- ================= HEADER ================= -->

	<header class="top-header">

		<div class="header-left">

			<h2>Seu Perfil</h2>

			<span>Visualize e altere suas informações pessoais</span>

		</div>

		<div class="header-right">

			<div class="user-area">

				<div class="user-avatar">
					<%=client.getNome().substring(0, 1).toUpperCase()%>
				</div>

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
				<a href="SvClient_Ticket">Tickets</a>
			</li>

			<li>
				<a class="active" href="SvClient_Perfil">Perfil</a>
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

		<form action="SvUpdateProfile" method="post" class="profile-card"
			id="profileForm">

			<!-- ================= HEADER CARD ================= -->

			<div class="profile-header">

				<div class="profile-left">

					<div class="avatar">
						<%=client.getNome().substring(0, 1).toUpperCase()%>
					</div>

					<div class="profile-text">

						<h1>
							<%=client.getNome()%>
							<%=client.getSobrenome()%>
						</h1>

						<span> Gerencie suas informações pessoais </span>

					</div>

				</div>

			</div>

			<input type="hidden" name="id" value="<%=client.getId()%>">

			<!-- ================= SECTIONS ================= -->

			<div class="profile-sections">

				<!-- ================= DADOS PESSOAIS ================= -->

				<section class="profile-section">

					<div class="section-title">

						<h2>Dados Pessoais</h2>

						<p>Informações principais da sua conta</p>

					</div>

					<div class="form-grid">

						<!-- NOME -->

						<div class="field-group">

							<label>Nome</label>

							<input type="text" name="nome" id="nome"
								value="<%=client.getNome()%>">

							<small id="nome-error" class="input-message"></small>

						</div>

						<!-- SOBRENOME -->

						<div class="field-group">

							<label>Sobrenome</label>

							<input type="text" name="sobrenome" id="sobrenome"
								value="<%=client.getSobrenome()%>">

							<small id="sobrenome-error" class="input-message"></small>

						</div>

						<!-- EMAIL -->

						<div class="field-group">

							<label>Email</label>

							<input type="email" name="email" id="email"
								value="<%=client.getEmail()%>">

							<small id="email-error" class="input-message"></small>

						</div>

						<!-- SENHA -->

						<div class="field-group">

							<label>Senha</label>

							<div class="password-wrapper">

								<input type="password" name="senha" id="senha"
									placeholder="Digite uma nova senha">

								<button type="button" class="toggle-password"
									onclick="toggleSenha()">👁</button>

							</div>

							<small id="senha-error" class="input-message"></small>

						</div>

						<!-- CPF -->

						<div class="field-group">

							<label>CPF</label>

							<input type="text" name="cpf" id="cpf"
								value="<%=client.getCpf()%>">

							<small id="cpf-error" class="input-message"></small>

						</div>

						<!-- TELEFONE -->

						<div class="field-group">

							<label>Telefone</label>

							<input type="text" name="telefone" id="telefone"
								value="<%=client.getTelefone()%>">

							<small id="telefone-error" class="input-message"></small>

						</div>

						<!-- DATA NASCIMENTO -->

						<div class="field-group">

							<label>Data de Nascimento</label>

							<input type="date" name="dataNascimento" id="dataNascimento"
								value="<%=client.getDataNascimento() != null ? client.getDataNascimento().toString() : ""%>">

							<small id="dataNascimento-error" class="input-message"></small>

						</div>

					</div>

				</section>

				<!-- ================= ENDEREÇO ================= -->

				<section class="profile-section">

					<div class="section-title">

						<h2>Endereço</h2>

						<p>Informações de localização</p>

					</div>

					<div class="form-grid">

						<!-- CEP -->

						<div class="field-group">

							<label>CEP</label>

							<input type="text" name="cep" id="cep"
								value="<%=client.getCep()%>">

							<small id="cep-error" class="input-message"></small>

						</div>

						<!-- NUMERO -->

						<div class="field-group">

							<label>Número</label>

							<input type="text" name="numero" id="numero"
								value="<%=client.getNumero()%>">

							<small id="numero-error" class="input-message"></small>

						</div>

						<!-- ENDEREÇO -->

						<div class="field-group full">

							<label>Endereço</label>

							<input type="text" name="endereco" id="endereco"
								value="<%=client.getEndereco()%>">

							<small id="endereco-error" class="input-message"></small>

						</div>

						<!-- BAIRRO -->

						<div class="field-group">

							<label>Bairro</label>

							<input type="text" name="bairro" id="bairro"
								value="<%=client.getBairro()%>">

							<small id="bairro-error" class="input-message"></small>

						</div>

						<!-- CIDADE -->

						<div class="field-group">

							<label>Cidade</label>

							<input type="text" name="cidade" id="cidade"
								value="<%=client.getCidade()%>">

							<small id="cidade-error" class="input-message"></small>

						</div>

						<!-- ESTADO -->

						<div class="field-group">

							<label>Estado</label>

							<input type="text" name="estado" id="estado"
								value="<%=client.getEstado()%>">

							<small id="estado-error" class="input-message"></small>

						</div>

						<!-- COMPLEMENTO -->

						<div class="field-group full">

							<label>Complemento</label>

							<input type="text" name="complemento" id="complemento"
								value="<%=client.getComplemento()%>">

							<small id="complemento-error" class="input-message"></small>

						</div>

					</div>

				</section>

			</div>

			<!-- ================= BOTÕES ================= -->

			<div class="buttons-area">

				<div class="left-buttons">

					<button type="submit" class="profile-btn">Salvar
						Alterações</button>

					<button type="button" class="delete-btn"
						onclick="confirmarDesativacao()">Desativar Conta</button>

				</div>

			</div>

			<!-- ================= FEEDBACK ================= -->

			<%
			String msg = (String) request.getAttribute("msg");

			if (msg != null) {
			%>

			<p class="msg-feedback">
				<%=msg%>
			</p>

			<%
			}
			%>

		</form>

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

	<script type="text/javascript" src="js/perfil.js"></script>
	<script type="text/javascript" src="js/validacoes-client.js"></script>

</body>

</html>