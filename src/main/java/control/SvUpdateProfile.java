package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;

import java.io.IOException;

/**
 * Servlet implementation class SvUpdateProfile
 */
@WebServlet("/SvUpdateProfile")
public class SvUpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvUpdateProfile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ================= STEP 1 =================
		Long id = Long.parseLong(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String dataNascimento = request.getParameter("dataNascimento");
		String cpf = request.getParameter("cpf");

		// ================= STEP 2 =================
		String email = request.getParameter("email");
		String telefone = request.getParameter("telefone");
		String senha = request.getParameter("senha");

		// ================= STEP 3 =================
		String cep = request.getParameter("cep");
		String endereco = request.getParameter("endereco");
		String cidade = request.getParameter("cidade");
		String bairro = request.getParameter("bairro");
		String estado = request.getParameter("estado");
		String numero = request.getParameter("numero");
		String complemento = request.getParameter("complemento");

		// ================= INSTANCIANDO ENTIDADE =================
		Client c = new Client();

		// STEP
		c.setId(id);
		c.setNome(nome);
		c.setSobrenome(sobrenome);
		c.setDataNascimento(dataNascimento);
		c.setCpf(cpf);

		// STEP 2
		c.setEmail(email);
		c.setTelefone(telefone);
		c.setSenha(senha);

		// STEP 3
		c.setCep(cep);
		c.setEndereco(endereco);
		c.setCidade(cidade);
		c.setBairro(bairro);
		c.setEstado(estado);
		c.setNumero(numero);
		c.setComplemento(complemento);

		// ================= UPDATE =================

		if (!c.atualizarPerfil()) {
			System.out.println(c.getMsg());
			request.setAttribute("msg", c.getMsg());

			request.getRequestDispatcher("SvClient_Perfil").forward(request, response);

			return;

		} else {

			// atualiza sessão
			request.getSession().setAttribute("client", c);

			request.setAttribute("msg", "Perfil atualizado com sucesso!");

			request.getRequestDispatcher("SvClient_Perfil").forward(request, response);

			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
