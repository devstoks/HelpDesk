<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Registro | HelpDesk Pro</title>

<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>

/* ================= RESET ================= */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Inter', sans-serif;
	background: linear-gradient(135deg, #0f172a 0%, #0a0f1c 50%, #05070f 100%);
	color: white;
	min-height: 100vh;
	overflow-x: hidden;
}

/* ================= BG ================= */
.dot-grid {
	position: fixed;
	inset: 0;
	background-image: radial-gradient(circle at 1px 1px, rgba(255, 255, 255, .08)
		1px, transparent 1px);
	background-size: 32px 32px;
	z-index: 0;
}

/* ================= HEADER ================= */
.header {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 100;
	padding: 18px 40px;
	background: rgba(15, 23, 42, .75);
	backdrop-filter: blur(12px);
	border-bottom: 1px solid rgba(255, 255, 255, .08);
}

.nav {
	max-width: 1300px;
	margin: auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.brand {
	font-size: 26px;
	font-weight: 800;
	background: linear-gradient(135deg, #3b82f6, #60a5fa);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}

.nav-links {
	display: flex;
	gap: 28px;
	align-items: center;
}

.nav-links a {
	color: #dbe4ee;
	text-decoration: none;
	font-size: 14px;
}

.btn-outline {
	padding: 10px 18px;
	border-radius: 12px;
	border: 1px solid rgba(255, 255, 255, .15);
	transition: .2s;
}

.btn-outline:hover {
	border-color: #3b82f6;
	color: #60a5fa;
}

/* ================= MAIN ================= */
main {
	position: relative;
	z-index: 1;
	padding-top: 120px;
	padding-bottom: 50px;
}

.register-wrapper {
	width: 100%;
	max-width: 1100px;
	margin: auto;
	padding: 20px;
}

.register-container {
	background: rgba(255, 255, 255, .03);
	border: 1px solid rgba(255, 255, 255, .08);
	border-radius: 30px;
	overflow: hidden;
	backdrop-filter: blur(12px);
}

/* ================= HEADER CARD ================= */
.register-header {
	padding: 40px 40px 10px;
	text-align: center;
}

.hero-badge {
	display: inline-flex;
	align-items: center;
	gap: 8px;
	padding: 10px 18px;
	border-radius: 999px;
	background: rgba(59, 130, 246, .15);
	border: 1px solid rgba(59, 130, 246, .3);
	color: #60a5fa;
	font-size: 13px;
	font-weight: 600;
	margin-bottom: 24px;
}

.register-header h1 {
	font-size: 42px;
	margin-bottom: 10px;
}

.register-header h1 span {
	background: linear-gradient(135deg, #3b82f6, #60a5fa);
	-webkit-background-clip: text;
	-webkit-text-fill-color: transparent;
}

.register-header p {
	color: #94a3b8;
}

/* ================= FORM ================= */
.register-form {
	padding: 35px 40px 40px;
}

/* ================= PROGRESS ================= */
.progress-container {
	margin-bottom: 35px;
}

.progress-bar {
	width: 100%;
	height: 8px;
	background: rgba(255, 255, 255, .08);
	border-radius: 999px;
	overflow: hidden;
	margin-bottom: 24px;
}

.progress-fill {
	width: 50%;
	height: 100%;
	background: linear-gradient(90deg, #3b82f6, #60a5fa);
	transition: .3s;
}

.progress-steps {
	display: flex;
	justify-content: space-between;
}

.progress-step {
	flex: 1;
	text-align: center;
	color: #64748b;
	transition: .2s;
}

.progress-step.active {
	color: #60a5fa;
}

.progress-step.completed {
	color: #10b981;
}

.step-circle {
	width: 42px;
	height: 42px;
	margin: auto auto 10px;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	background: rgba(255, 255, 255, .08);
	border: 1px solid rgba(255, 255, 255, .15);
	font-weight: 700;
}

.progress-step.active .step-circle {
	background: linear-gradient(135deg, #3b82f6, #2563eb);
	border-color: #3b82f6;
}

.progress-step.completed .step-circle {
	background: #10b981;
	border-color: #10b981;
}

/* ================= STEP ================= */
.form-step {
	display: none;
}

.form-step.active {
	display: block;
}

.form-step h3 {
	margin-bottom: 25px;
	font-size: 24px;
}

/* ================= GRID ================= */
.grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
	gap: 22px;
}

.input-group {
	display: flex;
	flex-direction: column;
}

.full-width {
	grid-column: 1/-1;
}

/* ================= LABEL ================= */
label {
	margin-bottom: 8px;
	color: #cbd5e1;
	font-size: 14px;
	font-weight: 600;
}

/* ================= INPUT ================= */
.input-wrapper {
	position: relative;
}

.input-wrapper i {
	position: absolute;
	top: 50%;
	left: 16px;
	transform: translateY(-50%);
	color: #64748b;
}

.input-wrapper input {
	width: 100%;
	height: 52px;
	padding-left: 48px;
	padding-right: 14px;
	background: rgba(255, 255, 255, .05);
	border: 1px solid rgba(255, 255, 255, .1);
	border-radius: 14px;
	color: white;
	transition: .2s;
}

.input-wrapper input:focus {
	outline: none;
	border-color: #3b82f6;
	box-shadow: 0 0 0 4px rgba(59, 130, 246, .15);
}

/* ================= MESSAGE ================= */
.input-message {
	min-height: 18px;
	margin-top: 6px;
	font-size: 12px;
	font-weight: 500;
	color: #ff6b6b;
}

.input-error {
	border-color: #ef4444 !important;
}

.input-success {
	border-color: #10b981 !important;
}

/* ================= BUTTONS ================= */
.buttons {
	margin-top: 35px;
	display: flex;
	justify-content: space-between;
	gap: 15px;
}

button {
	height: 52px;
	padding: 0 24px;
	border: none;
	border-radius: 14px;
	cursor: pointer;
	font-weight: 700;
	transition: .2s;
	display: flex;
	align-items: center;
	gap: 10px;
}

.btn-primary {
	background: linear-gradient(135deg, #3b82f6, #2563eb);
	color: white;
}

.btn-primary:hover {
	transform: translateY(-2px);
}

.btn-secondary {
	background: rgba(255, 255, 255, .06);
	color: #dbe4ee;
	border: 1px solid rgba(255, 255, 255, .08);
}

/* ================= BOTÃO DESABILITADO ================= */
.btn-disabled {
	opacity: .45;
	cursor: not-allowed !important;
	filter: grayscale(.5);
	transform: none !important;
}

/* ================= LOGIN LINK ================= */
.login-link {
	padding: 25px 40px 40px;
	border-top: 1px solid rgba(255, 255, 255, .08);
	text-align: center;
}

.login-link p {
	color: #94a3b8;
}

.login-link a {
	color: #60a5fa;
	text-decoration: none;
	font-weight: 700;
}

/* ================= RESPONSIVO ================= */
@media ( max-width :768px) {
	.nav {
		flex-direction: column;
		gap: 20px;
	}
	.nav-links {
		flex-wrap: wrap;
		justify-content: center;
	}
	.register-header h1 {
		font-size: 32px;
	}
	.buttons {
		flex-direction: column;
	}
	button {
		width: 100%;
		justify-content: center;
	}
	.register-form {
		padding: 25px;
	}
	.register-header {
		padding: 30px 25px 10px;
	}
}
</style>

</head>

<body>

	<div class="dot-grid"></div>

	<header class="header">

		<nav class="nav">

			<div class="brand">⚡ HelpDesk Pro</div>

			<div class="nav-links">

				<a href="index.jsp">Início</a> <a href="index.jsp#sobre">Sobre</a> <a
					href="index.jsp#como-funciona">Como funciona</a> <a
					href="index.jsp#beneficios">Benefícios</a> <a href="login.jsp"
					class="btn-outline"> Entrar </a>

			</div>

		</nav>

	</header>

	<main>

		<div class="register-wrapper">

			<div class="register-container">

				<div class="register-header">

					<div class="hero-badge">🚀 Comece agora gratuitamente</div>

					<h1>
						Crie sua <span>conta</span>
					</h1>

					<p>Preencha os dados abaixo para começar</p>

				</div>

				<form id="registerForm" class="register-form"
					action="SvRegisterClient" method="post">

					<div class="progress-container">

						<div class="progress-bar">
							<div class="progress-fill" id="progressFill"></div>
						</div>

						<div class="progress-steps">

							<div class="progress-step active" id="step1Indicator">

								<div class="step-circle">1</div>

								<span>Dados Pessoais</span>

							</div>

							<div class="progress-step" id="step2Indicator">

								<div class="step-circle">2</div>

								<span>Endereço</span>

							</div>

						</div>

					</div>

					<!-- STEP 1 -->

					<div class="form-step active" id="step1">

						<h3>
							<i class="fas fa-user"></i> Informações Pessoais
						</h3>

						<div class="grid">

							<div class="input-group">

								<label>Nome</label>

								<div class="input-wrapper">

									<i class="fas fa-user"></i>

									<input type="text" id="nome" name="nome"
										placeholder="Informe seu nome" required>

								</div>

								<small class="input-message" id="nomeMessage"></small>

							</div>

							<div class="input-group">

								<label>Sobrenome</label>

								<div class="input-wrapper">

									<i class="fas fa-user"></i>

									<input type="text" id="sobrenome" name="sobrenome"
										placeholder="Informe seu sobrenome" required>

								</div>

								<small class="input-message" id="sobrenomeMessage"></small>

							</div>

							<div class="input-group">

								<label>Email</label>

								<div class="input-wrapper">

									<i class="fas fa-envelope"></i>

									<input type="email" id="email" name="email"
										placeholder="Informe seu email: seuemail@gmail.com" required>

								</div>

								<small class="input-message" id="emailMessage"></small>

							</div>

							<div class="input-group">

								<label>Senha</label>

								<div class="input-wrapper">

									<i class="fas fa-lock"></i>

									<input type="password" id="senha" name="senha"
										placeholder="Informe uma senha, Ex: MinhaSenha01@" required>

								</div>

								<small class="input-message" id="senhaMessage"></small>

							</div>

							<div class="input-group">

								<label>CPF</label>

								<div class="input-wrapper">

									<i class="fas fa-id-card"></i>

									<input type="text" id="cpf" name="cpf"
										placeholder="Insira seu CPF: 000.000.000-00" required>

								</div>

								<small class="input-message" id="cpfMessage"></small>

							</div>

							<div class="input-group">

								<label>Telefone</label>

								<div class="input-wrapper">

									<i class="fas fa-phone"></i>

									<input type="text" id="telefone" name="telefone"
										placeholder="Informe seu telefone: (00) 00000-0000" required>

								</div>

								<small class="input-message" id="telefoneMessage"></small>

							</div>

							<div class="input-group full-width">

								<label>Data de Nascimento</label>

								<div class="input-wrapper">

									<i class="fas fa-calendar"></i>

									<input type="date" id="dataNascimento" name="dataNascimento"
										required>

								</div>

								<small class="input-message" id="dataNascimentoMessage"></small>

							</div>

						</div>

					</div>

					<!-- STEP 2 -->

					<div class="form-step" id="step2">

						<h3>
							<i class="fas fa-map-marker-alt"></i> Endereço
						</h3>

						<div class="grid">

							<div class="input-group">

								<label>CEP</label>

								<div class="input-wrapper">

									<i class="fas fa-mail-bulk"></i>

									<input type="text" id="cep" name="cep"
										placeholder="Informe seu CEP" required>

								</div>

								<small class="input-message" id="cepMessage"></small>

							</div>

							<div class="input-group">

								<label>Número</label>

								<div class="input-wrapper">

									<i class="fas fa-home"></i>

									<input type="text" id="numero" name="numero"
										placeholder="Informe o número da casa" required>

								</div>

								<small class="input-message" id="numeroMessage"></small>

							</div>

							<div class="input-group full-width">

								<label>Endereço</label>

								<div class="input-wrapper">

									<i class="fas fa-road"></i>

									<input type="text" id="endereco" name="endereco"
										placeholder="Informe seu endereço completo" required>

								</div>

								<small class="input-message" id="enderecoMessage"></small>

							</div>

							<div class="input-group">

								<label>Bairro</label>

								<div class="input-wrapper">

									<i class="fas fa-city"></i>

									<input type="text" id="bairro" name="bairro"
										placeholder="Informe seu bairro" required>

								</div>

								<small class="input-message" id="bairroMessage"></small>

							</div>

							<div class="input-group">

								<label>Cidade</label>

								<div class="input-wrapper">

									<i class="fas fa-building"></i>

									<input type="text" id="cidade" name="cidade"
										placeholder="Informe sua cidade" required>

								</div>

								<small class="input-message" id="cidadeMessage"></small>

							</div>

							<div class="input-group">

								<label>Estado</label>

								<div class="input-wrapper">

									<i class="fas fa-map"></i>

									<input type="text" id="estado" name="estado"
										placeholder="Informe seu estado" maxlength="2" required>

								</div>

								<small class="input-message" id="estadoMessage"></small>

							</div>

							<div class="input-group full-width">

								<label>Complemento</label>

								<div class="input-wrapper">

									<i class="fas fa-location-arrow"></i>

									<input type="text" id="complemento" name="complemento"
										placeholder="Informe o complemento da residência">

								</div>

								<small class="input-message" id="complementoMessage"></small>

							</div>

						</div>

					</div>

					<div class="buttons">

						<button type="button" class="btn-secondary" id="prevBtn"
							style="display: none" onclick="changeStep(-1)">

							<i class="fas fa-arrow-left"></i> Voltar

						</button>

						<button type="button" class="btn-primary btn-disabled"
							id="nextBtn" disabled onclick="changeStep(1)">

							Próximo <i class="fas fa-arrow-right"></i>

						</button>

						<button type="submit" class="btn-primary" id="submitBtn"
							style="display: none">

							<i class="fas fa-check-circle"></i> Criar Conta

						</button>

					</div>

				</form>

				<div class="login-link">

					<p>

						Já tem uma conta? <a href="login.jsp"> Faça login </a>

					</p>

				</div>

			</div>

		</div>

	</main>

	<%
	String msg = (String) request.getAttribute("msg");
	%>

	<%
	if (msg != null) {
	%>
	<script>
    alert("<%=msg.replace("\"", "\\\"")%>");
</script>
	<%
	}
	%>

	<script type="text/javascript" src="js/validacoes-registro.js"></script>

</body>

</html>