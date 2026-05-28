package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;
import model.TeamMember;
import model.Team;

import java.io.IOException;

/**
 * Servlet implementation class SvLogin
 */
@WebServlet("/SvLogin")
public class SvLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvLogin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");

		String senha = request.getParameter("senha");

		// ================= BUSCA CLIENT =================

		Client client = Client.buscarPorEmail(email);

		// ================= CONTA DESATIVADA =================

		if (client != null && "DEACTIVATED".equals(client.getStatus())) {

			request.setAttribute("msg", "Sua conta está desativada.");

			request.setAttribute("deactivated", true);

			request.setAttribute("email", email);

			request.getRequestDispatcher("login.jsp").forward(request, response);

			return;
		}

		// ================= LOGIN =================

		Client c = new Client();

		c.setEmail(email);

		c.setSenha(senha);

		System.out.println("chegou no servlet " + email);

		System.out.println("chegou no servlet " + senha);

		if (!c.login(email, senha)) {

			request.setAttribute("msg", c.getMsg());

			request.getRequestDispatcher("login.jsp").forward(request, response);

		} else {

			// salva client na sessão
			request.getSession().setAttribute("client", c);

			// verifica se possui team
			boolean possuiTeam = TeamMember.possuiTeam(c.getId());

			// sem time
			if (!possuiTeam) {

				request.getRequestDispatcher("teamSetup.jsp").forward(request, response);

			} else {

				Team team = TeamMember.buscarTeamDoClient(c.getId());

				request.getSession().setAttribute("team", team);

				request.getRequestDispatcher("SvDashboard").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
