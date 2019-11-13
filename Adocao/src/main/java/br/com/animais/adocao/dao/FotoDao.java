package br.com.animais.adocao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.animais.adocao.model.Foto;
import br.com.animais.adocao.util.JPAUtil;

public class FotoDao {
	public Foto salvar(Foto foto) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		em.flush();
		foto = em.merge(foto);
		trx.commit();
		em.close();

		return foto;

	}

	public Foto buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Foto.class, id);

		} finally {
			em.close();
		}

	}

	public List<Foto> buscarListaFotos(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Foto> query = em.createQuery("from Foto f where f.animal.id = :id", Foto.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}
	
	public Foto buscarListaEventoFotos(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Foto> query = em.createQuery("from Foto f where f.evento.id = :id", Foto.class);
		query.setParameter("id", id);

		try {
			return query.getSingleResult();

		} finally {
			em.close();
		}
	}

	public List<Foto> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Foto> query = em.createQuery("from Foto", Foto.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}
}
