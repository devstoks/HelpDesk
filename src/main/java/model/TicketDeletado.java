package model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class TicketDeletado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// ========= IDENTIFICAÇÃO ========= //

	@Column(nullable = false)
	private String titulo;

	@Column(length = 2000, nullable = false)
	private String descricao;

	@Column
	private String codigo;

	// ========= ORGANIZAÇÃO ========= //

	@Column(nullable = false)
	private String categoria;

	@Column(nullable = false)
	private String prioridade;

	@Column(nullable = false)
	private String status;

	@Column
	private String tipo;

	// ========= PRODUTIVIDADE ========= //

	@Column
	private String complexidade;

	@Column
	private LocalDateTime prazo;

	// ========= DATAS ========= //

	@Column
	private LocalDateTime dataCriacao;

	@Column
	private LocalDateTime dataAtualizacao;

	@Column
	private LocalDateTime dataFechamento;

	@Column
	private LocalDateTime dataDelecao;

	// ========= COMUNICAÇÃO ========= //

	@Column(length = 1000)
	private String resposta;

	@Column(length = 1000)
	private String motivoDelecao;

	// ========= RELACIONAMENTOS ========= //

	@ManyToOne
	@JoinColumn(name = "criador_id")
	private Client criador;

	@ManyToOne
	@JoinColumn(name = "responsavel_id")
	private Client responsavel;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne
	@JoinColumn(name = "deletado_por_id")
	private Client deletadoPor;

	// ========= CONTROLE ========= //

	@Transient
	private String msg;

	// ========= CONSTRUTORES ========= //

	public TicketDeletado() {
		super();
	}

	// ========= GETTERS E SETTERS ========= //

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

	public LocalDateTime getDataDelecao() {
		return dataDelecao;
	}

	public void setDataDelecao(LocalDateTime dataDelecao) {
		this.dataDelecao = dataDelecao;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getMotivoDelecao() {
		return motivoDelecao;
	}

	public void setMotivoDelecao(String motivoDelecao) {
		this.motivoDelecao = motivoDelecao;
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

	public Client getDeletadoPor() {
		return deletadoPor;
	}

	public void setDeletadoPor(Client deletadoPor) {
		this.deletadoPor = deletadoPor;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}