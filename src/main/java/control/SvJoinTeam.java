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

@WebServlet("/SvJoinTeam")
public class SvJoinTeam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SvJoinTeam() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ================= CLIENTE LOGADO =================

		Client c = (Client) request.getSession().getAttribute("client");

		if (c == null) {

			response.sendRedirect("login.jsp");

			return;
		}

		// ================= RECEBE CÓDIGO =================

		String codigo = request.getParameter("codigo");

		// validação simples
		if (codigo == null || codigo.trim().isEmpty()) {

			request.setAttribute("msg", "Digite um código válido.");

			request.getRequestDispatcher("teamSetup.jsp").forward(request, response);

			return;
		}

		// ================= BUSCA TEAM =================

		Team team = Team.buscarTeam(codigo);

		// não encontrou
		if (team == null || team.getId() == null) {

			request.setAttribute("msg", "Time não encontrado.");

			request.getRequestDispatcher("teamSetup.jsp").forward(request, response);

			return;
		}

		// ================= VERIFICA SE JÁ ESTÁ EM UM TIME =================

		if (TeamMember.possuiTeam(c.getId())) {

			request.setAttribute("msg", "Você já participa de um time.");

			request.getRequestDispatcher("team.jsp").forward(request, response);

			return;
		}

		// ================= CRIA MEMBRO =================

		TeamMember tm = new TeamMember();

		tm.setTeam(team);

		tm.setClient(c);

		tm.setRole(TeamMember.ROLE_MEMBRO); // define como client padrão

		tm.setDataEntrada(LocalDateTime.now());

		// salva no banco
		if (!tm.createMember()) {

			request.setAttribute("msg", tm.getMsg());

			request.getRequestDispatcher("teamSetup.jsp").forward(request, response);

			return;
		}

		// ================= SALVA TEAM NA SESSÃO =================

		request.getSession().setAttribute("team", team);

		// ================= SUCESSO =================

		request.setAttribute("msg", "Você entrou no time com sucesso!");

		response.sendRedirect("SvDashboard");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}