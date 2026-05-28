package control;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Client;

@WebServlet("/SvReactivateAccount")
public class SvReactivateAccount extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		String email = request.getParameter("email");

		Client client = Client.buscarPorEmail(email);

		if (client == null) {

			request.setAttribute("msg", "Conta não encontrada.");

			request.getRequestDispatcher("login.jsp").forward(request, response);

			return;
		}

		client.setStatus("ACTIVE");

		if (!client.updateStatus()) {

			request.setAttribute("msg", client.getMsg());

			request.getRequestDispatcher("login.jsp").forward(request, response);

			return;
		}

		request.setAttribute("msg", "Conta reativada com sucesso!");

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}