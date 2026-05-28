package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Client;
import model.TeamMember;

import java.io.IOException;

/**
 * Servlet implementation class SvLeaveTeam
 */
@WebServlet("/SvLeaveTeam")
public class SvLeaveTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SvLeaveTeam() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		Client client = (Client) session.getAttribute("client");

		try {

			TeamMember dao = new TeamMember();

			dao.removerMembro(client.getId());

			session.removeAttribute("team");

			response.sendRedirect("teamSetup.jsp");

		}

		catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect("SvTeamMemberList");
		}

	}

}
