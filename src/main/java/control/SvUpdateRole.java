package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TeamMember;

import java.io.IOException;

/**
 * Servlet implementation class SvUpdateRole
 */
@WebServlet("/SvUpdateRole")
public class SvUpdateRole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SvUpdateRole() {
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
		try {

			Long memberId = Long.parseLong(request.getParameter("memberId"));

			String role = request.getParameter("role");

			boolean atualizado = TeamMember.atualizarRole(memberId, role);

			if (atualizado) {

				response.sendRedirect("SvTeamMemberList");

			} else {

				response.sendRedirect("SvTeamMemberList?erro=1");
			}

		} catch (Exception e) {

			e.printStackTrace();

			response.sendRedirect("SvTeamMemberList?erro=1");
		}
	}
}
