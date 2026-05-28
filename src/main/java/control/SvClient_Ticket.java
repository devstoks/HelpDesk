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
import java.util.List;

/**
 * Servlet implementation class SvClient_Ticket
 */
@WebServlet("/SvClient_Ticket")
public class SvClient_Ticket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvClient_Ticket() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Client c = (Client) request.getSession().getAttribute("client");

		if (c == null) {

			response.sendRedirect("login.jsp");
			return;
		}

		Team team = TeamMember.buscarTeamDoClient(c.getId());

		TeamMember membro = TeamMember.buscarMembro(c.getId(), team.getId());

		String role = membro.getRole();

		List<Ticket> tickets = null;

		// ================= MEMBRO =================
		/*
		 * O membro pode visualizar:
		 * 
		 * - tickets próprios - tickets da equipe
		 * 
		 * Porém não possui permissões operacionais para assumir/finalizar/excluir.
		 */

		if (role.equals(TeamMember.ROLE_MEMBRO)) {

			tickets = Ticket.listarTicketsEquipe(c.getId(), team.getId());
		}

		// ================= AGENT/LIDER =================

		else if (role.equals(TeamMember.ROLE_AGENT) || role.equals(TeamMember.ROLE_LIDER)) {

			tickets = Ticket.listarTicketsEquipe(c.getId(), team.getId());
		}

		request.setAttribute("tickets", tickets);

		request.setAttribute("client", c);

		request.setAttribute("role", role);

		request.setAttribute("team", team);

		request.getRequestDispatcher("ClientTickets.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
