<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
    
    <style>
        /* RESET & BASE */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        html {
            scroll-behavior: smooth;
        }

        body {
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
            color: #ffffff;
            background: linear-gradient(135deg, #0f172a 0%, #0a0f1c 50%, #05070f 100%);
            min-height: 100vh;
            overflow-x: hidden;
            line-height: 1.5;
        }

        /* DOT GRID PATTERN */
        .dot-grid {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: radial-gradient(circle at 1px 1px, rgba(255,255,255,0.08) 1px, transparent 1px);
            background-size: 32px 32px;
            pointer-events: none;
            z-index: 0;
        }

        /* LAYOUT OVERLAY */
        header, main, footer {
            position: relative;
            z-index: 1;
        }

        /* TYPOGRAPHY */
        h1, h2, h3 {
            font-weight: 700;
            letter-spacing: -0.02em;
        }

        h1 {
            font-size: 3.5rem;
            line-height: 1.2;
            margin-bottom: 1.5rem;
        }

        h2 {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            background: linear-gradient(135deg, #ffffff 0%, #94a3b8 100%);
            background-clip: text;
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        h3 {
            font-size: 1.25rem;
            margin-bottom: 0.75rem;
        }

        /* HEADER FIXO */
        .header {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            background: rgba(15, 23, 42, 0.8);
            backdrop-filter: blur(12px);
            border-bottom: 1px solid rgba(255,255,255,0.08);
            padding: 1rem 2rem;
            z-index: 100;
            transition: all 0.3s ease;
        }

        .nav {
            max-width: 1280px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 1rem;
        }

        .brand {
            font-size: 1.5rem;
            font-weight: 800;
            background: linear-gradient(135deg, #3b82f6, #60a5fa);
            background-clip: text;
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            letter-spacing: -0.02em;
        }

        .nav-links {
            display: flex;
            gap: 2rem;
            align-items: center;
            flex-wrap: wrap;
        }

        .nav-links a {
            color: #e2e8f0;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
            transition: all 0.2s ease;
            position: relative;
        }

        .nav-links a:hover {
            color: #60a5fa;
        }

        .nav-links a::after {
            content: '';
            position: absolute;
            bottom: -4px;
            left: 0;
            width: 0;
            height: 2px;
            background: linear-gradient(90deg, #3b82f6, #60a5fa);
            transition: width 0.3s ease;
        }

        .nav-links a:hover::after {
            width: 100%;
        }

        /* BOTÕES */
        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.75rem 1.5rem;
            border-radius: 10px;
            font-weight: 600;
            font-size: 0.9rem;
            text-decoration: none;
            transition: all 0.25s ease;
            cursor: pointer;
            border: none;
            gap: 0.5rem;
        }

        .btn-primary {
            background: linear-gradient(135deg, #3b82f6, #2563eb);
            color: white;
            box-shadow: 0 4px 12px rgba(59,130,246,0.3);
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(59,130,246,0.4);
            filter: brightness(1.05);
        }

        .btn-secondary {
            background: rgba(255,255,255,0.05);
            border: 1px solid rgba(255,255,255,0.15);
            color: #e2e8f0;
        }

        .btn-secondary:hover {
            background: rgba(255,255,255,0.1);
            transform: translateY(-2px);
            border-color: rgba(59,130,246,0.5);
        }

        .btn-outline {
            background: transparent;
            border: 1px solid rgba(255,255,255,0.2);
            color: #cbd5e1;
        }

        .btn-outline:hover {
            border-color: #3b82f6;
            color: #60a5fa;
            transform: translateY(-2px);
        }

        /* MAIN CONTENT PADDING (compensando header fixo) */
        main {
            padding-top: 80px;
        }

        /* HERO SECTION */
        .hero {
            padding: 4rem 2rem 6rem;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .hero-content {
            max-width: 900px;
            margin: 0 auto;
            animation: fadeUp 0.8s ease-out;
        }

        .hero-badge {
            display: inline-block;
            background: rgba(59,130,246,0.15);
            border: 1px solid rgba(59,130,246,0.3);
            border-radius: 50px;
            padding: 0.4rem 1rem;
            font-size: 0.8rem;
            font-weight: 500;
            color: #60a5fa;
            margin-bottom: 2rem;
            backdrop-filter: blur(4px);
        }

        .hero h1 span {
            background: linear-gradient(135deg, #3b82f6, #38bdf8);
            background-clip: text;
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .hero-description {
            font-size: 1.2rem;
            color: #94a3b8;
            max-width: 650px;
            margin: 0 auto 2rem;
            line-height: 1.6;
        }

        .hero-buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
        }

        /* SEÇÕES GERAIS */
        .section {
            max-width: 1200px;
            margin: 0 auto;
            padding: 5rem 2rem;
        }

        .section-header {
            text-align: center;
            margin-bottom: 3rem;
        }

        .section-header p {
            color: #94a3b8;
            max-width: 600px;
            margin: 0 auto;
        }

        /* GRID */
        .grid-3 {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 1rem;
        }

        /* CARDS COM TRANSPARÊNCIA */
        .card {
            background: rgba(255,255,255,0.03);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255,255,255,0.08);
            border-radius: 20px;
            padding: 2rem;
            transition: all 0.3s ease;
            animation: fadeUp 0.6s ease-out forwards;
            opacity: 0;
        }

        .card:nth-child(1) { animation-delay: 0.1s; }
        .card:nth-child(2) { animation-delay: 0.2s; }
        .card:nth-child(3) { animation-delay: 0.3s; }

        .card:hover {
            transform: translateY(-6px);
            border-color: rgba(59,130,246,0.4);
            background: rgba(255,255,255,0.05);
            box-shadow: 0 20px 35px -12px rgba(0,0,0,0.3);
        }

        .card-icon {
            font-size: 2.5rem;
            margin-bottom: 1.25rem;
        }

        .card h3 {
            margin-bottom: 0.75rem;
            color: #f1f5f9;
        }

        .card p {
            color: #94a3b8;
            line-height: 1.5;
            font-size: 0.95rem;
        }

        /* PASSO A PASSO (FLUXO) */
        .steps-container {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 1.5rem;
            margin-top: 2rem;
        }

        .step-item {
            flex: 1;
            min-width: 200px;
            text-align: center;
            background: rgba(255,255,255,0.02);
            border-radius: 20px;
            padding: 2rem 1rem;
            border: 1px solid rgba(255,255,255,0.06);
            transition: all 0.3s ease;
        }

        .step-item:hover {
            transform: translateY(-4px);
            border-color: rgba(59,130,246,0.3);
            background: rgba(255,255,255,0.04);
        }

        .step-number {
            width: 48px;
            height: 48px;
            background: linear-gradient(135deg, #3b82f6, #2563eb);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 1rem;
            font-weight: 700;
            font-size: 1.25rem;
            box-shadow: 0 4px 12px rgba(59,130,246,0.3);
        }

        /* BENEFÍCIOS LISTA COM ÍCONES */
        .benefits-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 1.5rem;
            margin-top: 2rem;
        }

        .benefit-card {
            background: rgba(255,255,255,0.02);
            border: 1px solid rgba(255,255,255,0.06);
            border-radius: 16px;
            padding: 1.5rem;
            display: flex;
            align-items: center;
            gap: 1rem;
            transition: all 0.3s ease;
        }

        .benefit-card:hover {
            background: rgba(255,255,255,0.05);
            transform: translateX(4px);
            border-left: 3px solid #3b82f6;
        }

        .benefit-icon {
            font-size: 2rem;
        }

        .benefit-text {
            font-weight: 500;
            color: #e2e8f0;
        }

        .benefit-text small {
            display: block;
            font-size: 0.8rem;
            color: #64748b;
            margin-top: 0.25rem;
        }

        /* FOOTER */
        .footer {
            border-top: 1px solid rgba(255,255,255,0.05);
            padding: 3rem 2rem;
            text-align: center;
            margin-top: 2rem;
        }

        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 1rem;
        }

        .footer-copyright {
            color: #64748b;
            font-size: 0.85rem;
        }

        .footer-links {
            display: flex;
            gap: 2rem;
        }

        .footer-links a {
            color: #94a3b8;
            text-decoration: none;
            font-size: 0.85rem;
            transition: color 0.2s;
        }

        .footer-links a:hover {
            color: #60a5fa;
        }

        /* ANIMAÇÕES */
        @keyframes fadeUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* RESPONSIVIDADE */
        @media (max-width: 768px) {
            h1 {
                font-size: 2.2rem;
            }
            
            h2 {
                font-size: 1.8rem;
            }
            
            .header {
                padding: 1rem;
            }
            
            .nav {
                flex-direction: column;
            }
            
            .nav-links {
                justify-content: center;
                gap: 1.2rem;
            }
            
            .hero {
                padding: 3rem 1rem 4rem;
            }
            
            .section {
                padding: 3rem 1rem;
            }
            
            .grid-3 {
                gap: 1rem;
            }
            
            .card {
                padding: 1.5rem;
            }
            
            .steps-container {
                flex-direction: column;
            }
            
            .footer-content {
                flex-direction: column;
                text-align: center;
            }
        }

        /* SMOOTH SCROLL + efeitos extras */
        ::selection {
            background: #3b82f6;
            color: white;
        }
        
        a, button {
            transition: all 0.2s ease;
        }
        
        /* SCROLLBAR ESTILIZADA */
        ::-webkit-scrollbar {
            width: 8px;
        }
        
        ::-webkit-scrollbar-track {
            background: #0f172a;
        }
        
        ::-webkit-scrollbar-thumb {
            background: #3b82f6;
            border-radius: 10px;
        }
        
        ::-webkit-scrollbar-thumb:hover {
            background: #2563eb;
        }
    </style>
</head>
<body>

<!-- DOT GRID BACKGROUND -->
<div class="dot-grid"></div>

<!-- HEADER FIXO -->
<header class="header">
    <nav class="nav">
        <div class="brand">⚡ HelpDesk Pro</div>
        <div class="nav-links">
            <a href="#sobre">Sobre</a>
            <a href="#como-funciona">Como funciona</a>
            <a href="#beneficios">Benefícios</a>
            <a href="#recursos">Recursos</a>
            <a href="login.jsp" class="btn btn-outline" style="padding: 0.5rem 1rem;">Login</a>
        </div>
    </nav>
</header>

<main>
    <!-- HERO SECTION -->
    <section class="hero">
        <div class="hero-content">
            <div class="hero-badge">
                🚀 Sistema de chamados + suporte técnico
            </div>
            <h1>
                Gestão de tickets <br>
                <span>simples, rápida e inteligente</span>
            </h1>
            <p class="hero-description">
                Centralize solicitações, acompanhe status em tempo real e melhore a comunicação 
                entre equipes técnicas e usuários finais. Ideal para suporte e desenvolvimento.
            </p>
            <div class="hero-buttons">
                <a href="login.jsp" class="btn btn-primary">✨ Começar agora</a>
                <a href="#sobre" class="btn btn-secondary">📖 Explorar sistema</a>
            </div>
        </div>
    </section>

    <!-- SOBRE O SISTEMA -->
    <section id="sobre" class="section">
        <div class="section-header">
            <h2>Sobre o HelpDesk</h2>
            <p>Uma plataforma unificada para gerenciar chamados de suporte e registrar bugs com eficiência</p>
        </div>
        <div class="grid-3">
            <div class="card">
                <div class="card-icon">🎫</div>
                <h3>Gestão total de tickets</h3>
                <p>Organize todas as solicitações em um único painel, categorizando por prioridade, setor e responsável técnico.</p>
            </div>
            <div class="card">
                <div class="card-icon">🔄</div>
                <h3>Integração com devs</h3>
                <p>Identifique e registre bugs diretamente durante o desenvolvimento, com histórico completo e logs de erro.</p>
            </div>
            <div class="card">
                <div class="card-icon">📊</div>
                <h3>Relatórios inteligentes</h3>
                <p>Acompanhe métricas de resolução, tempo médio de resposta e pontos de melhoria contínua.</p>
            </div>
        </div>
    </section>

    <!-- COMO FUNCIONA (FLUXO EM ETAPAS) -->
    <section id="como-funciona" class="section">
        <div class="section-header">
            <h2>⚙️ Como funciona</h2>
            <p>Fluxo simples e direto para resolver chamados com agilidade</p>
        </div>
        <div class="steps-container">
            <div class="step-item">
                <div class="step-number">1</div>
                <h3>Abertura de chamado</h3>
                <p>Usuário final ou desenvolvedor registra problema/sugestão com título, descrição e anexos.</p>
            </div>
            <div class="step-item">
                <div class="step-number">2</div>
                <h3>Triagem automática</h3>
                <p>Sistema categoriza o ticket, define prioridade e notifica a equipe responsável.</p>
            </div>
            <div class="step-item">
                <div class="step-number">3</div>
                <h3>Acompanhamento</h3>
                <p>Status em tempo real (Aberto, Em Análise, Resolvido, Fechado) com comentários internos.</p>
            </div>
            <div class="step-item">
                <div class="step-number">4</div>
                <h3>Resolução & feedback</h3>
                <p>Equipe resolve e encerra, com possibilidade de avaliação e base de conhecimento.</p>
            </div>
        </div>
    </section>

    <!-- BENEFÍCIOS DETALHADOS -->
    <section id="beneficios" class="section">
        <div class="section-header">
            <h2>Vantagens para times e usuários</h2>
            <p>Porque empresas de tecnologia confiam no HelpDesk Pro</p>
        </div>
        <div class="benefits-grid">
            <div class="benefit-card">
                <span class="benefit-icon">✅</span>
                <div class="benefit-text">Centralização de chamados<br><small>Unifica solicitações de e-mail, chat e formulários</small></div>
            </div>
            <div class="benefit-card">
                <span class="benefit-icon">🐞</span>
                <div class="benefit-text">Rastreamento de bugs<br><small>Identificação e registro direto no fluxo de desenvolvimento</small></div>
            </div>
            <div class="benefit-card">
                <span class="benefit-icon">📈</span>
                <div class="benefit-text">Redução de retrabalho<br><small>Histórico completo evita repetição de problemas</small></div>
            </div>
            <div class="benefit-card">
                <span class="benefit-icon">🤝</span>
                <div class="benefit-text">Comunicação transparente<br><small>Equipes técnicas e clientes alinhados em cada etapa</small></div>
            </div>
            <div class="benefit-card">
                <span class="benefit-icon">⚡</span>
                <div class="benefit-text">Atendimento mais rápido<br><small>Automatização de roteamento e SLAs personalizados</small></div>
            </div>
            <div class="benefit-card">
                <span class="benefit-icon">🔒</span>
                <div class="benefit-text">Segurança e conformidade<br><small>Acesso por perfis e logs de auditoria</small></div>
            </div>
        </div>
    </section>

    <!-- EXTRA: RECURSOS PARA DEVs (seção adicional para demonstrar capacidade) -->
    <section id="recursos" class="section">
        <div class="section-header">
            <h2>🔧 Para desenvolvedores</h2>
            <p>Ferramentas que ajudam a identificar e registrar bugs durante o ciclo de desenvolvimento</p>
        </div>
        <div class="grid-3">
            <div class="card">
                <div class="card-icon">📝</div>
                <h3>Registro de bugs técnicos</h3>
                <p>Capture stack traces, versões de ambiente e prints diretamente pelo próprio chamado.</p>
            </div>
            <div class="card">
                <div class="card-icon">🔗</div>
                <h3>Integração com GitHub/Jira</h3>
                <p>Vínculo automático entre tickets e issues, sincronizando o status do desenvolvimento.</p>
            </div>
            <div class="card">
                <div class="card-icon">📡</div>
                <h3>Webhooks & API</h3>
                <p>Automatize a criação de chamados via API e receba atualizações em tempo real.</p>
            </div>
        </div>
    </section>
</main>

<!-- FOOTER -->
<footer class="footer">
    <div class="footer-content">
        <div class="footer-copyright">
            © 2026 HelpDesk Pro — Plataforma de Suporte e Gestão de chamados
        </div>
        <div class="footer-links">
            <a href="#">Termos de uso</a>
            <a href="#">Privacidade</a>
            <a href="#">Suporte</a>
            <a href="#">Status</a>
        </div>
    </div>
</footer>


</body>
</html>