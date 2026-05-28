package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;
import model.Ticket;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class SvDashboard
 */
@WebServlet("/SvDashboard")
public class SvDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SvDashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Recupera o cliente salvo antes no Login
		Client c = (Client) request.getSession().getAttribute("client");

		// caso esteja nulo, envia o usuario para a tela de login
		if(c == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
        // Recebe os tickets
		List<Ticket> ultimosTickets = Ticket.listarUltimosAbertos(c.getId());
		
		// Enviando para a view
		request.setAttribute("ultimosTickets", ultimosTickets);		
	    request.setAttribute("client", c);
		
	    
	    
		// Chamando um método static
		Long total = Ticket.contarTotal(c.getId());
		Long aberto = Ticket.contarPorStatus("ABERTO", c.getId());
        Long andamento = Ticket.contarPorStatus("EM_ANDAMENTO", c.getId());
        Long fechado = Ticket.contarPorStatus("FECHADO", c.getId());
		
        
        // enviando para a view
        request.setAttribute("total", total);
        request.setAttribute("aberto", aberto);
        request.setAttribute("andamento", andamento);
        request.setAttribute("fechado", fechado);

        // manda tudo para a tela menuClient
        request.getRequestDispatcher("menuClient.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
