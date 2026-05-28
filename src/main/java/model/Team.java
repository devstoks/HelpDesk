package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


@Entity(name = "teams")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column
	private String codigoConvite;

	// líder do time
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Client owner;

	@Transient
	private String msg;

	// ========= CONSTRUTORES ========= //

	public Team() {
		super();
	}

	public Team(Long id, String nome, Client owner) {
		super();
		this.id = id;
		this.nome = nome;
		this.owner = owner;
	}

	// ========= GETTERS E SETTERS ========= //

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

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCodigoConvite() {
		return codigoConvite;
	}

	public void setCodigoConvite(String codigoConvite) {
		this.codigoConvite = codigoConvite;
	}

	public boolean createTeam() {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			if (nome == null || nome.trim().isEmpty()) {
				return false;
			}

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

	// Gera um código de grupo aleatório
	public static String gerarCodigoConvite() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		try {

			while (true) {

				String codigo = "TEAM-";

				for (int i = 0; i < 4; i++) {

					int index = (int) (Math.random() * caracteres.length());

					codigo += caracteres.charAt(index);
				}

				Query<Team> query = session.createQuery("from teams where codigoConvite = :codigo", Team.class);

				query.setParameter("codigo", codigo);

				Team team = query.uniqueResult();

				// se NÃO encontrou nenhum time
				if (team == null) {

					session.close();

					return codigo;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

			session.close();

			return null;
		}
	}

	public static Team buscarTeam(String codigo) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			Query<Team> query = session.createQuery(
				"from teams where codigoConvite = :codigo",
				Team.class
			);

			query.setParameter("codigo", codigo);

			Team team = query.uniqueResult();

			// NÃO encontrou
			if (team == null) {

				Team t = new Team();
				t.setMsg("Nenhum time encontrado com esse código.");

				session.close();

				return t;
			}

			session.close();

			return team;

		} catch (Exception e) {

			e.printStackTrace();

			Team t = new Team();

			t.setMsg("Erro ao buscar time.");

			session.close();

			return t;
		}
	}

}