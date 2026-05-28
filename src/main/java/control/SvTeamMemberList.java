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
import java.util.List;

@WebServlet("/SvTeamMemberList")
public class SvTeamMemberList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvTeamMemberList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ================= CLIENT LOGADO ================= //

		Client c = (Client) request.getSession().getAttribute("client");

		// se não estiver logado
		if (c == null) {

			response.sendRedirect("login.jsp");
			return;
		}

		// ================= BUSCA TEAM ================= //

		Team team = TeamMember.buscarTeamDoClient(c.getId());

		// se não possuir team
		if (team == null) {

			request.setAttribute("erro", "Você ainda não possui um time.");

			request.getRequestDispatcher("teamSetup.jsp").forward(request, response);

			return;
		}

		// ================= LISTA MEMBROS ================= //

		List<TeamMember> membros = TeamMember.listarMembros(team.getId());

		// ================= MEMBRO LOGADO ================= //

		TeamMember membroLogado = TeamMember.buscarMembro(c.getId(), team.getId());

		// ================= PERMISSÕES ================= //

		boolean isLeader = false;

		if (membroLogado != null) {

			isLeader = TeamMember.ROLE_LIDER.equals(membroLogado.getRole());
		}

		// ================= ENVIA DADOS ================= //

		request.setAttribute("members", membros);

		request.setAttribute("isLeader", isLeader);

		request.setAttribute("team", team);

		request.setAttribute("memberLogged", membroLogado);

		// ================= FORWARD ================= //

		request.getRequestDispatcher("team.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
