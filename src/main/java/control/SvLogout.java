package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/SvLogout")
public class SvLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvLogout() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null) {

			// remove dados específicos primeiro (boa prática)
			session.removeAttribute("client");
			session.removeAttribute("team");
			session.removeAttribute("role");

			// invalida tudo (limpeza total de sessão)
			session.invalidate();
		}

		// evita cache de páginas protegidas
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		// volta para login
		response.sendRedirect("login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}