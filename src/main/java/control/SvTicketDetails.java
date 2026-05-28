package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;
import model.Team;
import model.TeamMember;
import model.Ticket;

import java.io.IOException;

/**
 * Servlet implementation class SvTicketDetails
 */
@WebServlet("/SvTicketDetails")
public class SvTicketDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvTicketDetails() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			// CLIENT LOGADO
			Client client = (Client) request.getSession().getAttribute("client");

			if (client == null) {

				response.sendRedirect("login.jsp");
				return;
			}

			// ROLE
			String role = TeamMember.buscarRole(client.getId());

			// TEAM
			request.getSession().setAttribute("team", TeamMember.buscarTeamDoClient(client.getId()));

			// ID DO TICKET
			Long id = Long.parseLong(request.getParameter("id"));

			// BUSCA O TICKET
			Ticket ticket = Ticket.buscarPorId(id);

			// VALIDA SE EXISTE
			if (ticket == null) {

				response.sendRedirect("SvClient_Ticket");
				return;
			}

			// VERIFICA SE O TICKET PERTENCE AO MESMO TIME
			boolean mesmoTime = false;

			if (ticket.getTeam() != null && TeamMember.possuiTeam(client.getId())) {

				Team teamClient = TeamMember.buscarTeamDoClient(client.getId());

				if (teamClient != null) {

					mesmoTime = ticket.getTeam().getId().equals(teamClient.getId());
				}
			}

			// CRIADOR
			boolean isCriador = ticket.getCriador() != null && ticket.getCriador().getId().equals(client.getId());

			// AGENT
			boolean isAgent = TeamMember.ROLE_AGENT.equals(role);

			// LIDER
			boolean isLider = TeamMember.ROLE_LIDER.equals(role);

			// MEMBRO
			boolean isMembro = TeamMember.ROLE_MEMBRO.equals(role);

			/*
			 * REGRAS:
			 *
			 * - Criador pode visualizar - Qualquer membro do mesmo time pode visualizar -
			 * Agent/Lider podem visualizar
			 */

			if (!(isCriador || mesmoTime || isAgent || isLider || isMembro)) {

				response.sendRedirect("acesso-negado.jsp");
				return;
			}

			// ENVIA DADOS
			request.setAttribute("ticket", ticket);

			request.setAttribute("role", role);

			request.setAttribute("client", client);

			request.getRequestDispatcher("ticket-details.jsp").forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect("SvClient_Ticket");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
