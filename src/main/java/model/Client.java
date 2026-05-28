package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.query.Query;

import util.HibernateUtil;

@Entity
@Table(name = "clients")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Client {

	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_DEACTIVATED = "DEACTIVATED";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column
	private String sobrenome;

	@Column(unique = true)
	private String email;

	@Column
	private String senha;

	@Column(unique = true, length = 14)
	private String cpf;

	@Column
	private String telefone;

	@Column
	private String dataNascimento;

	@Column
	private String cep;

	@Column
	private String endereco;

	@Column
	private String cidade;

	@Column
	private String estado;

	@Column
	private String bairro;

	@Column
	private String numero;

	@Column
	private String complemento;

	@Column
	private String status;

	@Transient
	private String msg;

	// ================= CONSTRUTORES ================= //

	public Client(Long id, String nome, String sobrenome, String email, String senha, String cpf, String telefone,
			String dataNascimento, String cep, String endereco, String cidade, String estado, String bairro,
			String numero, String complemento, String msg) {

		super();

		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.cep = cep;
		this.endereco = endereco;
		this.cidade = cidade;
		this.estado = estado;
		this.bairro = bairro;
		this.numero = numero;
		this.complemento = complemento;
		this.msg = msg;
	}

	public Client() {
		super();
	}

	// ================= GETTERS E SETTERS ================= //

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	// ================= REGISTER ================= //

	public boolean register() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();

			// ================= NORMALIZAÇÃO ================= //
			this.nome = this.nome != null ? this.nome.trim() : null;
			this.sobrenome = this.sobrenome != null ? this.sobrenome.trim() : null;
			this.email = this.email != null ? this.email.trim().toLowerCase() : null;
			this.cpf = this.cpf != null ? this.cpf.trim() : null;
			this.senha = this.senha != null ? this.senha.trim() : null;

			// ================= VALIDAÇÕES OBRIGATÓRIAS ================= //

			if (this.nome == null || this.nome.length() < 2) {
				this.msg = "Nome inválido!";
				return false;
			}

			if (this.sobrenome == null || this.sobrenome.length() < 2) {
				this.msg = "Sobrenome inválido!";
				return false;
			}

			if (this.email == null || !this.email.contains("@") || !this.email.contains(".")) {
				this.msg = "Email inválido!";
				return false;
			}

			if (this.senha == null || this.senha.length() < 6) {
				this.msg = "Senha deve ter no mínimo 6 caracteres!";
				return false;
			}

			if (this.cpf == null || this.cpf.length() < 11) {
				this.msg = "CPF inválido!";
				return false;
			}

			// ================= DUPLICIDADE EMAIL ================= //
			Query<Client> emailQuery = session.createQuery("from Client where email = :email", Client.class);
			emailQuery.setParameter("email", this.email);

			if (!emailQuery.getResultList().isEmpty()) {
				this.msg = "Este email já está cadastrado!";
				return false;
			}

			// ================= DUPLICIDADE CPF ================= //
			Query<Client> cpfQuery = session.createQuery("from Client where cpf = :cpf", Client.class);
			cpfQuery.setParameter("cpf", this.cpf);

			if (!cpfQuery.getResultList().isEmpty()) {
				this.msg = "Este CPF já está cadastrado!";
				return false;
			}

			// ================= STATUS INICIAL ================= //
			this.status = STATUS_ACTIVE;

			// ================= PERSISTÊNCIA ================= //
			session.save(this);
			session.getTransaction().commit();

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			this.msg = "Erro ao criar conta: " + e.getMessage();
			return false;

		} finally {
			session.close();
		}
	}

	// ================= LOGIN ================= //

	public boolean login(String email, String senha) {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			Query<Client> query = session.createQuery("from Client where email = :email and senha = :senha",
					Client.class);

			query.setParameter("email", email);

			query.setParameter("senha", senha);

			List<Client> lista = query.getResultList();

			if (lista.isEmpty()) {

				this.msg = "Login inválido!";

				return false;
			}

			Client aux = lista.get(0);

			// ================= STATUS ================= //

			if (!aux.contaAtiva()) {

				this.msg = "Sua conta está desativada!";

				return false;
			}

			// ================= PREENCHE OBJETO ================= //

			this.id = aux.getId();

			this.nome = aux.getNome();

			this.sobrenome = aux.getSobrenome();

			this.email = aux.getEmail();

			this.senha = aux.getSenha();

			this.cpf = aux.getCpf();

			this.telefone = aux.getTelefone();

			this.dataNascimento = aux.getDataNascimento();

			this.cep = aux.getCep();

			this.endereco = aux.getEndereco();

			this.cidade = aux.getCidade();

			this.estado = aux.getEstado();

			this.bairro = aux.getBairro();

			this.numero = aux.getNumero();

			this.complemento = aux.getComplemento();

			this.status = aux.getStatus();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			this.msg = e.getMessage();

			return false;

		} finally {

			session.close();
		}
	}

	// ================= BUSCAR PERFIL ================= //

	public boolean buscarDadosPerfil(Long clientId) {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			Query<Client> query = session.createQuery("from Client where id = :id", Client.class);

			query.setParameter("id", clientId);

			List<Client> lista = query.getResultList();

			if (lista.isEmpty()) {

				this.msg = "Cliente não encontrado!";

				return false;
			}

			Client aux = lista.get(0);

			this.id = aux.getId();

			this.nome = aux.getNome();

			this.sobrenome = aux.getSobrenome();

			this.email = aux.getEmail();

			this.senha = aux.getSenha();

			this.cpf = aux.getCpf();

			this.telefone = aux.getTelefone();

			this.dataNascimento = aux.getDataNascimento();

			this.cep = aux.getCep();

			this.endereco = aux.getEndereco();

			this.cidade = aux.getCidade();

			this.estado = aux.getEstado();

			this.bairro = aux.getBairro();

			this.numero = aux.getNumero();

			this.complemento = aux.getComplemento();

			this.status = aux.getStatus();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			this.msg = e.getMessage();

			return false;

		} finally {

			session.close();
		}
	}

	// ================= CONTA ATIVA ================= //

	public boolean contaAtiva() {

		return STATUS_ACTIVE.equals(this.status);
	}

	// ================= DESATIVAR CONTA ================= //

	public boolean desativarConta() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			session.beginTransaction();

			Client client = session.get(Client.class, this.id);

			if (client == null) {

				this.msg = "Usuário não encontrado!";

				return false;
			}

			client.setStatus(STATUS_DEACTIVATED);

			session.getTransaction().commit();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			this.msg = e.getMessage();

			return false;

		} finally {

			session.close();
		}
	}

	// ================= UPDATE STATUS ================= //

	public boolean updateStatus() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			session.beginTransaction();

			session.update(this);

			session.getTransaction().commit();

			this.msg = "Status atualizado.";

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			this.msg = "Erro ao atualizar status.";

			return false;

		} finally {

			session.close();
		}
	}

	// ================= BUSCAR POR EMAIL ================= //

	public static Client buscarPorEmail(String email) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Query<Client> query = session.createQuery("from Client where email = :email", Client.class);

			query.setParameter("email", email);

			return query.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		} finally {

			session.close();
		}
	}

	// ================= UPDATE PROFILE ================= //

	public boolean atualizarPerfil() {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			session.beginTransaction();

			Client original = session.get(Client.class, this.id);

			if (original == null) {

				this.msg = "Usuário não encontrado!";

				session.close();

				return false;
			}

			// ================= VALIDAÇÕES ================= //

			// NOME
			if (this.nome == null || this.nome.trim().isEmpty()) {

				this.nome = original.getNome();

			} else {

				this.nome = this.nome.trim();

				if (this.nome.length() < 2) {

					this.msg = "Nome inválido!";

					session.close();

					return false;
				}
			}

			// SOBRENOME
			if (this.sobrenome == null || this.sobrenome.trim().isEmpty()) {

				this.sobrenome = original.getSobrenome();

			} else {

				this.sobrenome = this.sobrenome.trim();

				if (this.sobrenome.length() < 2) {

					this.msg = "Sobrenome inválido!";

					session.close();

					return false;
				}
			}

			// EMAIL
			if (this.email == null || this.email.trim().isEmpty()) {

				this.email = original.getEmail();

			} else {

				this.email = this.email.trim();

				if (!this.email.contains("@") || !this.email.contains(".")) {

					this.msg = "Email inválido!";

					session.close();

					return false;
				}

				Query<Client> query = session.createQuery("from Client where email = :email and id != :id",
						Client.class);

				query.setParameter("email", this.email);
				query.setParameter("id", this.id);

				if (!query.getResultList().isEmpty()) {

					this.msg = "Este email já está em uso!";

					session.close();

					return false;
				}
			}

			// SENHA
			if (this.senha == null || this.senha.trim().isEmpty()) {

				this.senha = original.getSenha();

			} else {

				if (this.senha.length() < 6) {

					this.msg = "Senha muito curta!";

					session.close();

					return false;
				}
			}

			// CPF
			if (this.cpf == null || this.cpf.trim().isEmpty()) {

				this.cpf = original.getCpf();

			} else {

				this.cpf = this.cpf.trim();

				if (this.cpf.length() < 11) {

					this.msg = "CPF inválido!";

					session.close();

					return false;
				}
			}

			// TELEFONE
			if (this.telefone == null || this.telefone.trim().isEmpty()) {

				this.telefone = original.getTelefone();

			} else {

				this.telefone = this.telefone.trim();

				if (this.telefone.length() < 10) {

					this.msg = "Telefone inválido!";

					session.close();

					return false;
				}
			}

			// DATA NASCIMENTO
			if (this.dataNascimento == null || this.dataNascimento.trim().isEmpty()) {
				this.dataNascimento = original.getDataNascimento();
			}

			// CEP
			if (this.cep == null || this.cep.trim().isEmpty()) {
				this.cep = original.getCep();
			}

			// ENDEREÇO
			if (this.endereco == null || this.endereco.trim().isEmpty()) {
				this.endereco = original.getEndereco();
			}

			// CIDADE
			if (this.cidade == null || this.cidade.trim().isEmpty()) {
				this.cidade = original.getCidade();
			}

			// BAIRRO
			if (this.bairro == null || this.bairro.trim().isEmpty()) {
				this.bairro = original.getBairro();
			}

			// ESTADO
			if (this.estado == null || this.estado.trim().isEmpty()) {
				this.estado = original.getEstado();
			}

			// NUMERO
			if (this.numero == null || this.numero.trim().isEmpty()) {
				this.numero = original.getNumero();
			}

			// COMPLEMENTO
			if (this.complemento == null || this.complemento.trim().isEmpty()) {
				this.complemento = original.getComplemento();
			}

			// ================= UPDATE ================= //

			original.setNome(this.nome);
			original.setSobrenome(this.sobrenome);
			original.setEmail(this.email);
			original.setSenha(this.senha);
			original.setCpf(this.cpf);
			original.setTelefone(this.telefone);

			original.setDataNascimento(this.dataNascimento);

			original.setCep(this.cep);
			original.setEndereco(this.endereco);
			original.setCidade(this.cidade);
			original.setBairro(this.bairro);
			original.setEstado(this.estado);
			original.setNumero(this.numero);
			original.setComplemento(this.complemento);

			// IMPORTANTE
			session.update(original);

			session.getTransaction().commit();

			// atualiza o próprio objeto também
			this.nome = original.getNome();
			this.sobrenome = original.getSobrenome();
			this.email = original.getEmail();
			this.senha = original.getSenha();
			this.cpf = original.getCpf();
			this.telefone = original.getTelefone();
			this.dataNascimento = original.getDataNascimento();
			this.cep = original.getCep();
			this.endereco = original.getEndereco();
			this.cidade = original.getCidade();
			this.estado = original.getEstado();
			this.bairro = original.getBairro();
			this.numero = original.getNumero();
			this.complemento = original.getComplemento();
			this.status = original.getStatus();

			this.msg = "Perfil atualizado com sucesso!";

			session.close();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			this.msg = e.toString();

			session.close();

			return false;
		}
	}

	// busca por CPF
	public static Client buscarPorCPF(String cpf) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {

			Query<Client> query = session.createQuery("from Client where cpf = :cpf", Client.class);

			query.setParameter("cpf", cpf);

			return query.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			session.close();
		}
	}
}