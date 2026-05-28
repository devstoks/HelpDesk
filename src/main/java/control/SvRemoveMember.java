package control;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Client;
import model.Team;
import model.TeamMember;

@WebServlet("/SvRemoveMember")
public class SvRemoveMember extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SvRemoveMember() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ================= CLIENT LOGADO ================= //

		Client clientLogado = (Client) request.getSession().getAttribute("client");

		if (clientLogado == null) {

			response.sendRedirect("login.jsp");
			return;
		}

		// ================= TEAM ================= //

		Team team = TeamMember.buscarTeamDoClient(clientLogado.getId());

		if (team == null) {

			response.sendRedirect("teamSetup.jsp");
			return;
		}

		// ================= MEMBRO LOGADO ================= //

		TeamMember membroLogado = TeamMember.buscarMembro(clientLogado.getId(), team.getId());

		if (membroLogado == null) {

			response.sendRedirect("SvTeamMemberList");
			return;
		}

		// ================= VALIDA LÍDER ================= //

		boolean isLeader = TeamMember.ROLE_LIDER.equals(membroLogado.getRole());

		if (!isLeader) {

			response.sendRedirect("SvTeamMemberList");
			return;
		}

		// ================= MEMBER ID ================= //

		String memberIdParam = request.getParameter("memberId");

		if (memberIdParam == null || memberIdParam.isEmpty()) {

			response.sendRedirect("SvTeamMemberList");
			return;
		}

		Long memberId = Long.parseLong(memberIdParam);

		// ================= MEMBRO A REMOVER ================= //

		TeamMember membro = TeamMember.buscarPorId(memberId);

		if (membro == null) {

			response.sendRedirect("SvTeamMemberList");
			return;
		}

		// impede remover líder
		if (TeamMember.ROLE_LIDER.equals(membro.getRole())) {

			response.sendRedirect("SvTeamMemberList");
			return;
		}

		// impede remover membro de outro time
		if (!membro.getTeam().getId().equals(team.getId())) {

			response.sendRedirect("SvTeamMemberList");
			return;
		}

		// ================= REMOVE ================= //

		boolean removeu = TeamMember.removerPorId(memberId);

		if (removeu) {

			request.getSession().setAttribute("msg", "Membro removido com sucesso.");

		} else {

			request.getSession().setAttribute("erro", "Erro ao remover membro.");
		}

		response.sendRedirect("SvTeamMemberList");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}