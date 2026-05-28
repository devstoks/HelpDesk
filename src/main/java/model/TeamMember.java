package model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import util.HibernateUtil;

@Entity
public class TeamMember {

	public static final String ROLE_LIDER = "LIDER";
	public static final String ROLE_AGENT = "AGENT";
	public static final String ROLE_MEMBRO = "MEMBRO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@Column(nullable = false)
	private String role;

	@Column
	private LocalDateTime dataEntrada;

	@Transient
	private String msg;

	// construtores
	public TeamMember() {
	}

	// getters e setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static boolean possuiTeam(Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			Query<TeamMember> query = session.createQuery("from TeamMember where client.id = :id", TeamMember.class);

			query.setParameter("id", clientId);

			TeamMember membro = query.uniqueResult();

			session.close();

			return membro != null;

		} catch (Exception e) {

			e.printStackTrace();
			session.close();

			return false;
		}
	}

	public boolean createMember() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			if (team == null) {

				this.msg = "Time inválido.";

				return false;
			}

			if (client == null) {

				this.msg = "Cliente inválido.";

				return false;
			}

			if (role == null || role.trim().isEmpty()) {

				this.msg = "Cargo inválido.";

				return false;
			}

			TeamMember existente = buscarMembro(client.getId(), team.getId());

			if (existente != null) {

				this.msg = "Usuário já pertence ao time.";

				return false;
			}

			session.beginTransaction();

			session.save(this);

			session.getTransaction().commit();

			this.msg = "Membro criado com sucesso!";

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			this.msg = "Erro ao criar membro.";

			session.close();
			sf.close();

			return false;
		}
	}

	public static Team buscarTeamDoClient(Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			Query<TeamMember> query = session.createQuery("from TeamMember where client.id = :id", TeamMember.class);

			query.setParameter("id", clientId);

			TeamMember tm = query.uniqueResult();

			session.close();

			if (tm != null) {
				return tm.getTeam();
			}

			return null;

		} catch (Exception e) {

			session.close();
			e.printStackTrace();

			return null;
		}
	}

	public static List<TeamMember> listarMembros(Long teamId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		try {

			Query<TeamMember> query = session.createQuery("from TeamMember tm where tm.team.id = :teamId",
					TeamMember.class);

			query.setParameter("teamId", teamId);

			return query.getResultList();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {
			session.close();
		}
	}

	public static TeamMember buscarMembro(Long clientId, Long teamId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		try {

			Query<TeamMember> query = session.createQuery(
					"from TeamMember tm " + "where tm.client.id = :clientId " + "and tm.team.id = :teamId",
					TeamMember.class);

			query.setParameter("clientId", clientId);
			query.setParameter("teamId", teamId);

			return query.uniqueResult();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			session.close();
		}
	}

	// -------- BUSCAR ROLE DO CLIENT -------- //
	public static String buscarRole(Long clientId) {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session session = sf.openSession();

		try {

			Query<String> query = session.createQuery("select role from TeamMember where client.id = :id",
					String.class);

			query.setParameter("id", clientId);

			String role = query.uniqueResult();

			session.close();

			return role;

		} catch (Exception e) {

			e.printStackTrace();

			session.close();

			return null;
		}
	}

	// ================= REMOVE MEMBRO ================= //

	public boolean removerMembro(Long clientId) {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			session.beginTransaction();

			Query<TeamMember> query = session.createQuery("from TeamMember where client.id = :clientId",
					TeamMember.class);

			query.setParameter("clientId", clientId);

			List<TeamMember> lista = query.getResultList();

			for (TeamMember tm : lista) {

				session.delete(tm);
			}

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {

			this.msg = e.toString();

			session.close();

			return false;
		}
	}

	// ================= UPDATE ROLE ================= //

	public static boolean atualizarRole(Long memberId, String role) {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			session.beginTransaction();

			TeamMember membro = session.get(TeamMember.class, memberId);

			if (membro == null) {

				return false;
			}

			membro.setRole(role);

			session.update(membro);

			session.getTransaction().commit();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			if (session.getTransaction() != null) {

				session.getTransaction().rollback();
			}

			return false;

		} finally {

			session.close();
		}
	}

	// ================= BUSCAR POR ID ================= //

	public static TeamMember buscarPorId(Long id) {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			return session.get(TeamMember.class, id);

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		} finally {

			session.close();
		}
	}

	// ================= REMOVER POR ID ================= //

	public static boolean removerPorId(Long id) {

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.openSession();

		try {

			TeamMember membro = session.get(TeamMember.class, id);

			if (membro == null) {

				return false;
			}

			session.beginTransaction();

			session.delete(membro);

			session.getTransaction().commit();

			return true;

		} catch (Exception e) {

			e.printStackTrace();

			return false;

		} finally {

			session.close();
		}
	}

}
