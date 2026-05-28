package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;
import model.Team;
import model.TeamMember;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Servlet implementation class SvCreateTeam
 */
@WebServlet("/SvCreateTeam")
public class SvCreateTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvCreateTeam() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String nome = request.getParameter("nome"); // nome do time

		// pega o client salvo na sessão
		Client c = (Client) request.getSession().getAttribute("client");

		// ================= INSTANCIANDO ENTIDADE =================
		Team t = new Team();
		t.setNome(nome);
		t.setOwner(c);
		t.setCodigoConvite(Team.gerarCodigoConvite()); // chama um método que gera um código aleatório

		if (!t.createTeam()) {
			request.setAttribute("msg", t.getMsg());
			request.getRequestDispatcher("teamSetup.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute("client", c); // guarda o client na memória do servidor
			request.getSession().setAttribute("team", t);
			
			TeamMember tm = new TeamMember();

			tm.setTeam(t);
			tm.setClient(c);
			tm.setRole("LIDER");
			tm.setDataEntrada(LocalDateTime.now());

			tm.createMember();

			// redireciona dashboard
			request.getRequestDispatcher("SvDashboard").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
