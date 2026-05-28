package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;
import model.Team;
import model.Ticket;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Servlet implementation class SvCreateTicket
 */
@WebServlet("/SvCreateTicket")
public class SvCreateTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SvCreateTicket() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ================= SESSION ================= //

		Client client = (Client) request.getSession().getAttribute("client");

		Team team = (Team) request.getSession().getAttribute("team");

		// ================= VALIDAÇÃO ================= //

		if (client == null) {

			response.sendRedirect("login.jsp");

			return;
		}

		if (team == null) {

			request.setAttribute("msg", "Você precisa participar de um time.");

			request.getRequestDispatcher("teamSetup.jsp").forward(request, response);

			return;
		}

		// ================= FORM ================= //

		String titulo = request.getParameter("titulo");

		String descricao = request.getParameter("descricao");

		String categoria = request.getParameter("categoria");

		String tipo = request.getParameter("tipo");

		String prioridade = request.getParameter("prioridade");

		String complexidade = request.getParameter("complexidade");

		String prazoStr = request.getParameter("prazo");

		// ================= PRAZO ================= //

		LocalDateTime prazo = null;

		if (prazoStr != null && !prazoStr.isEmpty()) {

			prazo = LocalDateTime.parse(prazoStr);
		}

		// ================= CÓDIGO ================= //

		String prefixo = "TASK";

		if (tipo != null && !tipo.isBlank()) {

			prefixo = tipo.toUpperCase();
		}

		String codigo = prefixo + "-" + System.currentTimeMillis();

		// ================= TICKET ================= //

		Ticket ticket = new Ticket();

		ticket.setTitulo(titulo);

		ticket.setDescricao(descricao);

		ticket.setCategoria(categoria);

		ticket.setTipo(tipo);

		ticket.setPrioridade(prioridade);

		ticket.setComplexidade(complexidade);

		ticket.setPrazo(prazo);

		ticket.setCodigo(codigo);

		ticket.setCriador(client);

		ticket.setTeam(team);

		ticket.setStatus(Ticket.STATUS_ABERTO);

		// ================= SAVE ================= //

		if (!ticket.abrir()) {

			request.setAttribute("msg", ticket.getMsg());

			request.getRequestDispatcher("menuClient.jsp").forward(request, response);

			return;
		}

		response.sendRedirect("SvDashboard");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
