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

@WebServlet("/SvDisableUser")
public class SvDisableUser extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SvDisableUser() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Recupera tudo do usuário salvo na sessão
		HttpSession session = request.getSession();

		Client clientSession = (Client) session.getAttribute("client");

		if (clientSession == null) {

			response.sendRedirect("login.jsp");

			return;
		}

		TeamMember tm = new TeamMember();

		// remove do time primeiro
		if (!tm.removerMembro(clientSession.getId())) {

			request.setAttribute("msg", tm.getMsg());

			request.getRequestDispatcher("perfil.jsp").forward(request, response);

			return;
		}

		Client client = new Client();

		client.setId(clientSession.getId());

		// desativa conta
		if (!client.desativarConta()) {

			request.setAttribute("msg", client.getMsg());

			request.getRequestDispatcher("SvClient_Perfil").forward(request, response);

			return;
		}

		// encerra sessão
		session.invalidate();

		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}