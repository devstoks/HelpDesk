package control;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Client;
import model.TeamMember;
import model.Ticket;

@WebServlet("/SvUpdateTicket")
public class SvUpdateTicket extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SvUpdateTicket() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			// ============= -- CLIENTE LOGADO -- =============== //
			/*
			 * Toda ação dentro do sistema precisa validar se existe um usuário autenticado
			 * na sessão.
			 * 
			 * Isso impede: - acesso direto pela URL - ações sem login - manipulação manual
			 * do sistema
			 */

			Client client = (Client) request.getSession().getAttribute("client");

			if (client == null) {

				response.sendRedirect("login.jsp");

				return;
			}

			// ============= -- BUSCA TICKET -- =============== //
			/*
			 * Recebe o ID enviado pelo formulário e busca o ticket no banco.
			 * 
			 * Caso o ticket não exista, redireciona para listagem.
			 */

			Long id = Long.parseLong(request.getParameter("id"));

			Ticket ticket = Ticket.buscarPorId(id);

			if (ticket == null) {

				response.sendRedirect("SvClient_Ticket");

				return;
			}

			// ============= -- DADOS PRINCIPAIS -- =============== //
			/*
			 * "acao" define qual botão foi clicado:
			 * 
			 * assumir salvar finalizar excluir
			 * 
			 * A role define o nível de acesso do usuário dentro da equipe.
			 */

			String acao = request.getParameter("acao");

			String role = TeamMember.buscarRole(client.getId());

			// ============= -- STATUS -- =============== //
			/*
			 * Essas variáveis facilitam a leitura do código e evitam repetir:
			 * 
			 * ticket.getStatus().equals(...)
			 * 
			 * em vários lugares.
			 */

			boolean ticketAberto = Ticket.STATUS_ABERTO.equalsIgnoreCase(ticket.getStatus());

			boolean ticketAndamento = Ticket.STATUS_EM_ANDAMENTO.equalsIgnoreCase(ticket.getStatus());

			boolean ticketFechado = Ticket.STATUS_FECHADO.equalsIgnoreCase(ticket.getStatus());

			// ============= -- ROLES -- =============== //
			/*
			 * Separa permissões por cargo.
			 * 
			 * LÍDER: controle total do sistema
			 * 
			 * AGENT: trabalha nos tickets
			 * 
			 * MEMBRO: apenas cria e acompanha tickets pessoais
			 */

			boolean isLider = TeamMember.ROLE_LIDER.equals(role);

			boolean isAgent = TeamMember.ROLE_AGENT.equals(role);

			boolean isMembro = TeamMember.ROLE_MEMBRO.equals(role);

			// ============= -- RELAÇÕES -- =============== //
			/*
			 * Verifica vínculos do usuário com o ticket atual.
			 * 
			 * isCriador: verifica se o ticket pertence ao usuário logado.
			 * 
			 * isResponsavel: verifica se o usuário assumiu o ticket.
			 */

			boolean isCriador = ticket.getCriador() != null && ticket.getCriador().getId().equals(client.getId());

			boolean possuiResponsavel = ticket.getResponsavel() != null;

			boolean isResponsavel = possuiResponsavel && ticket.getResponsavel().getId().equals(client.getId());

			// ============= -- ASSUMIR TICKET -- =============== //
			/*
			 * Fluxo responsável por permitir que um usuário assuma um ticket.
			 * 
			 * Regras:
			 * 
			 * LÍDER: pode assumir qualquer ticket aberto
			 * 
			 * AGENT: pode assumir qualquer ticket aberto
			 * 
			 * MEMBRO: não pode assumir tickets da equipe.
			 * 
			 * O membro apenas visualiza.
			 */

			if ("assumir".equals(acao)) {

				boolean podeAssumir = false;

				if (isLider && ticketAberto) {

					podeAssumir = true;
				}

				else if (isAgent && ticketAberto) {

					podeAssumir = true;
				}

				/*
				 * Caso não tenha permissão, bloqueia a ação.
				 */

				if (!podeAssumir) {

					response.sendRedirect("acesso-negado.jsp");

					return;
				}

				/*
				 * Define responsável e muda status automaticamente para EM_ANDAMENTO.
				 */

				ticket.assumir(client);

				ticket.update();

				response.sendRedirect("SvTicketDetails?id=" + id);

				return;
			}

			// ============= -- FINALIZAR TICKET -- =============== //
			/*
			 * Responsável por encerrar tickets concluídos.
			 * 
			 * Regras:
			 * 
			 * LÍDER: pode finalizar qualquer ticket em andamento.
			 * 
			 * AGENT: só pode finalizar tickets assumidos por ele.
			 * 
			 * MEMBRO: não pode finalizar tickets.
			 */

			if ("finalizar".equals(acao)) {

				boolean podeFinalizar = false;

				if (isLider && ticketAndamento) {

					podeFinalizar = true;
				}

				else if (isAgent && ticketAndamento && isResponsavel) {

					podeFinalizar = true;
				}

				if (!podeFinalizar) {

					response.sendRedirect("acesso-negado.jsp");

					return;
				}

				/*
				 * Salva resposta técnica final antes de fechar o ticket.
				 */

				String respostaFinal = request.getParameter("respostaFinal");

				ticket.fechar(respostaFinal);

				ticket.update();

				response.sendRedirect("SvTicketDetails?id=" + id);

				return;
			}

			// ============= -- EDITAR / SALVAR -- =============== //
			/*
			 * Responsável por atualizar os dados do ticket.
			 * 
			 * Regras:
			 * 
			 * LÍDER: pode editar tudo.
			 * 
			 * AGENT: pode editar tickets ainda não fechados.
			 * 
			 * MEMBRO: apenas tickets próprios e ainda abertos.
			 */

			if ("salvar".equals(acao)) {

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

				if (!podeEditar) {

					response.sendRedirect("acesso-negado.jsp");

					return;
				}

				// ============= -- ATUALIZAR CAMPOS -- ============== //
				/*
				 * Atualiza os dados vindos do formulário JSP.
				 */

				ticket.setTitulo(request.getParameter("titulo"));

				ticket.setDescricao(request.getParameter("descricao"));

				ticket.setCategoria(request.getParameter("categoria"));

				ticket.setPrioridade(request.getParameter("prioridade"));

				ticket.setTipo(request.getParameter("tipo"));

				ticket.setComplexidade(request.getParameter("complexidade"));

				ticket.setResposta(request.getParameter("resposta"));

				// ============= -- PRAZO -- ========================= //
				/*
				 * O input datetime-local envia:
				 * 
				 * 2026-05-19T14:30
				 * 
				 * O parse converte automaticamente para LocalDateTime.
				 */

				String prazo = request.getParameter("prazo");

				if (prazo != null && !prazo.isBlank()) {

					ticket.setPrazo(LocalDateTime.parse(prazo));
				}

				ticket.update();

				response.sendRedirect("SvTicketDetails?id=" + id);

				return;
			}

			// ============= -- EXCLUIR TICKET -- =============== //
			/*
			 * Exclusão permanente do ticket.
			 * 
			 * Apenas líderes podem excluir.
			 * 
			 * Antes de remover: - salva histórico - registra motivo - registra quem deletou
			 */

			if ("excluir".equals(acao)) {

				if (!isLider) {

					response.sendRedirect("acesso-negado.jsp");

					return;
				}

				String motivoDelecao = request.getParameter("motivoDelecao");

				ticket.excluir(client, motivoDelecao);

				response.sendRedirect("SvClient_Ticket");

				return;
			}

			// ============= -- AÇÃO INVÁLIDA -- =============== //
			/*
			 * Caso nenhuma ação válida seja encontrada.
			 */

			response.sendRedirect("SvClient_Ticket");

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect("SvClient_Ticket");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}