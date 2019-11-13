package br.com.animais.adocao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import br.com.animais.adocao.model.Evento;
import br.com.animais.adocao.util.JPAUtil;

public class EventoDao {

	public Evento salvar(Evento evento) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		evento = em.merge(evento);
		trx.commit();
		em.close();

		return evento;

	}

	public void excluir(Evento evento) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Evento.class, evento.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}

	public List<Evento> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Evento> query = em.createQuery("from Evento", Evento.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Evento> buscarEventosOng(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Evento> query = em.createQuery("from Evento e where e.ong.id = :id", Evento.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Evento> buscarEventosPessoa(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Evento> query = em.createQuery("from Evento e where e.pessoa.id = :id", Evento.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Evento> buscarEventoNome(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Evento> query = em.createQuery("from Evento e where e.nome like :nome", Evento.class);
		query.setParameter("nome", "%" + nome + "%");

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public Evento buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(Evento.class, id);

		} finally {
			em.close();
		}
	}

}
