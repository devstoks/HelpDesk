package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Client;

import java.io.IOException;

@WebServlet("/SvRegisterClient")
public class SvRegisterClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvRegisterClient() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ================= STEP 1 =================
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

		// VALIDAÇÃO DE DUPLICIDADE (EVITA ERRO DO BANCO)

		Client existenteEmail = Client.buscarPorEmail(email);
		if (existenteEmail != null) {

			request.setAttribute("msg", "Já existe uma conta com este e-mail.");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}

		Client existenteCpf = Client.buscarPorCPF(cpf);
		if (existenteCpf != null) {

			request.setAttribute("msg", "Já existe uma conta com este CPF. Por favor, reative sua conta");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		// ================= INSTANCIANDO ENTIDADE =================
		Client c = new Client();

		// STEP 1
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

		// ================= REGISTRO =================
		if (!c.register()) {

			request.setAttribute("msg", c.getMsg());
			request.getRequestDispatcher("register.jsp").forward(request, response);

		} else {

			request.setAttribute("msg", "Conta criada com sucesso!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}