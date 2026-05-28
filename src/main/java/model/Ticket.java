package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

@Entity
public class Ticket {

	public static final String STATUS_ABERTO = "ABERTO";
	public static final String STATUS_EM_ANDAMENTO = "EM_ANDAMENTO";
	public static final String STATUS_FECHADO = "FECHADO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// ========= IDENTIFICAÇÃO ========= //

	@Column(nullable = false)
	private String titulo;

	@Column(length = 2000, nullable = false)
	private String descricao;

	@Column
	private String codigo; // GAME-01, TASK-22... indentificação rapida

	// ========= ORGANIZAÇÃO ========= //

	@Column(nullable = false)
	private String categoria;

	@Column(nullable = false)
	private String prioridade; // BAIXA, MEDIA, ALTA, URGENTE

	@Column(nullable = false)
	private String status; // ABERTO, EM_ANDAMENTO, FECHADO

	@Column
	private String tipo; // BUG, TASK, FEATURE, SUPPORT

	// ========= PRODUTIVIDADE ========= //

	@Column
	private String complexidade; // SIMPLES, MEDIA, COMPLEXA

	@Column
	private LocalDateTime prazo;

	// ========= DATAS ========= //

	@Column
	private LocalDateTime dataCriacao; // Automática ao abrir um ticket

	@Column
	private LocalDateTime dataAtualizacao; // Automática ao selecionar/editar um ticket

	@Column
	private LocalDateTime dataFechamento; // Automático ao fechar o Ticket

	// ========= COMUNICAÇÃO ========= //

	@Column(length = 1000)
	private String resposta; // resposta do agente

	// ========= RELACIONAMENTOS ========= //

	// Quem criou o ticket
	@ManyToOne
	@JoinColumn(name = "criador_id")
	private Client criador;

	// Quem assumiu/responsável
	@ManyToOne
	@JoinColumn(name = "responsavel_id")
	private Client responsavel;

	// Time relacionado ao ticket
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	// ========= CONTROLE ========= //

	@Transient
	private String msg;

	// ========= CONSTRUTORES ========= //

	public Ticket() {
		super();
	}

	public Ticket(Long id, String titulo, String descricao, String codigo, String categoria, String prioridade,
			String status, String tipo, String complexidade, LocalDateTime prazo, LocalDateTime dataCriacao,
			LocalDateTime dataAtualizacao, LocalDateTime dataFechamento, String resposta, Client criador,
			Client responsavel, Team team) {

		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.codigo = codigo;
		this.categoria = categoria;
		this.prioridade = prioridade;
		this.status = status;
		this.tipo = tipo;
		this.complexidade = complexidade;
		this.prazo = prazo;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
		this.dataFechamento = dataFechamento;
		this.resposta = resposta;
		this.criador = criador;
		this.responsavel = responsavel;
		this.team = team;
	}

	// ========= GETTERS e SETTERS ========= //

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getComplexidade() {
		return complexidade;
	}

	public void setComplexidade(String complexidade) {
		this.complexidade = complexidade;
	}

	public LocalDateTime getPrazo() {
		return prazo;
	}

	public void setPrazo(LocalDateTime prazo) {
		this.prazo = prazo;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public Client getCriador() {
		return criador;
	}

	public void setCriador(Client criador) {
		this.criador = criador;
	}

	public Client getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Client responsavel) {
		this.responsavel = responsavel;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	// ========================== MÉTODOS =================================== //

	// ========================== TODOS OS LISTAR
	// =================================== //

	// ------ LISTAR POR CLIENTE --------- //
	public static List<Ticket> listarPorCliente(Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			Query<Ticket> query = session.createQuery("from Ticket where criador.id = :id", Ticket.class);

			query.setParameter("id", clientId);

			List<Ticket> lista = query.getResultList();
			session.close();

			return lista;

		} catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
	}

	// ------ LISTAR ÚLTIMOS TICKETS ABERTOS ---- //
	public static List<Ticket> listarUltimosAbertos(Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {
			Query<Ticket> query = session.createQuery(
					"from Ticket where status = :status and criador.id = :id order by dataCriacao desc", Ticket.class);

			query.setParameter("status", "ABERTO");
			query.setParameter("id", clientId);

			query.setMaxResults(5); // Recebe apenas 5 itens no máximp

			List<Ticket> lista = query.getResultList();

			session.close();
			return lista;

		} catch (Exception e) {
			session.close();
			e.printStackTrace();
			return null;
		}
	}

	// ------- CONTADOR DE TICKETS ABERTOS ----- //

	public static Long contarTotal(Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		Long total = (Long) session.createQuery("select count(*) from Ticket where criador.id = :id")
				.setParameter("id", clientId).uniqueResult();

		session.close();
		return total;
	}

	// ----- CONTADOR DE TICKETS POR STATUS ----- //
	// ABERTO/EM_ANDAMENTO,FECHADO
	public static Long contarPorStatus(String status, Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		Long total = (Long) session
				.createQuery("select count(*) from Ticket where status = :status and criador.id = :id")
				.setParameter("status", status).setParameter("id", clientId).uniqueResult();

		session.close();
		return total;
	}

	// ========================== TODOS OS PRINCIPAIS
	// =================================== //

	// ------- ABRIR TICKET --------- //
	public boolean abrir() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			if (titulo == null || titulo.trim().isEmpty()) {
				this.msg = "Título inválido!";
				return false;
			}

			if (descricao == null || descricao.trim().isEmpty()) {
				this.msg = "Descrição inválida!";
				return false;
			}

			if (categoria == null || categoria.trim().isEmpty()) {
				this.msg = "Categoria inválida!";
				return false;
			}

			if (prioridade == null || prioridade.trim().isEmpty()) {
				this.msg = "Prioridade inválida!";
				return false;
			}

			this.status = STATUS_ABERTO;

			this.dataCriacao = LocalDateTime.now();
			this.dataAtualizacao = LocalDateTime.now();

			session.beginTransaction();

			session.save(this);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {

			this.msg = e.toString();

			session.close();

			return false;
		}
	}

	// ----- UM USUÁRIO QUER ASSUMIR UMA TASK ---- //
	public void assumir(Client responsavel) {

		// NÃO PERMITE ASSUMIR TICKET FECHADO
		if ("FECHADO".equalsIgnoreCase(this.status)) {

			throw new IllegalStateException("Não é possível assumir um ticket fechado.");
		}

		// NÃO PERMITE ASSUMIR NOVAMENTE
		if (this.responsavel != null) {

			throw new IllegalStateException("Este ticket já possui responsável.");
		}

		this.responsavel = responsavel;

		this.status = STATUS_EM_ANDAMENTO;

		this.dataAtualizacao = LocalDateTime.now();
	}

	// ------ FINALIZAR TICKET ----- //
	public void fechar(String resposta) {

		this.resposta = resposta;

		this.status = STATUS_FECHADO;

		this.dataFechamento = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	public static List<Ticket> listarTicketsEquipe(Long clientId, Long teamId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			Query<Ticket> query = session.createQuery("from Ticket " + "where criador.id = :clientId "
					+ "or team.id = :teamId " + "order by dataCriacao desc", Ticket.class);

			query.setParameter("clientId", clientId);
			query.setParameter("teamId", teamId);

			List<Ticket> lista = query.getResultList();

			session.close();

			return lista;

		} catch (Exception e) {

			session.close();
			e.printStackTrace();

			return null;
		}
	}

	// -------- BUSCAR TICKET POR ID -------- //
	public static Ticket buscarPorId(Long id) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			Ticket ticket = session.get(Ticket.class, id);

			session.close();

			return ticket;

		} catch (Exception e) {

			session.close();

			e.printStackTrace();

			return null;
		}
	}

	// -------- UPDATE TICKET -------- //
	public boolean update() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			this.dataAtualizacao = LocalDateTime.now();

			session.beginTransaction();

			session.update(this);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			session.close();

			this.msg = e.toString();

			return false;
		}
	}

	public boolean excluir(Client deletadoPor, String motivoDelecao) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		try {

			TicketDeletado deletado = this.converterParaDeletado(deletadoPor, motivoDelecao);

			session.beginTransaction();

			// salva histórico
			session.save(deletado);

			// remove original
			session.delete(this);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			session.close();

			return false;
		}
	}

	public TicketDeletado converterParaDeletado(Client deletadoPor, String motivoDelecao) {

		TicketDeletado deletado = new TicketDeletado();

		deletado.setTitulo(this.titulo);

		deletado.setDescricao(this.descricao);

		deletado.setCodigo(this.codigo);

		deletado.setCategoria(this.categoria);

		deletado.setPrioridade(this.prioridade);

		deletado.setStatus(this.status);

		deletado.setTipo(this.tipo);

		deletado.setComplexidade(this.complexidade);

		deletado.setDataCriacao(this.dataCriacao);

		deletado.setDataAtualizacao(this.dataAtualizacao);

		deletado.setDataFechamento(this.dataFechamento);

		deletado.setResposta(this.resposta);

		deletado.setCriador(this.criador);

		deletado.setResponsavel(this.responsavel);

		deletado.setTeam(this.team);

		deletado.setDataDelecao(LocalDateTime.now());

		deletado.setDeletadoPor(deletadoPor);

		deletado.setMotivoDelecao(motivoDelecao);

		return deletado;
	}

}