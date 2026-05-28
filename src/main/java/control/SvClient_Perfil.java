package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;

import java.io.IOException;

/**
 * Servlet implementation class SvClient_Perfil
 */
@WebServlet("/SvClient_Perfil")
public class SvClient_Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SvClient_Perfil() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Recupera o cliente salvo antes no Login
				Client c = (Client) request.getSession().getAttribute("client");

				// caso esteja nulo, envia o usuario para a tela de login
				if(c == null) {
					response.sendRedirect("login.jsp");
					return;
				}
		
				
				// cahmar o método
				if(!c.buscarDadosPerfil(c.getId())) {
					request.setAttribute("erro", c.getMsg());
				}else {
					// enviando para a view
					request.setAttribute("client", c);
					
					
					// manda tudo para a tela menuClient
			        request.getRequestDispatcher("Client-perfil.jsp").forward(request, response);
				}
				
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
