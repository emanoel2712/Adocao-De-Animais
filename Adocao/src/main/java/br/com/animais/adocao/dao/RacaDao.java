package br.com.animais.adocao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.animais.adocao.model.Raca;
import br.com.animais.adocao.util.JPAUtil;

public class RacaDao {
	public Raca salvar(Raca raca) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		raca = em.merge(raca);
		trx.commit();
		em.close();

		return raca;

	}

	public List<Raca> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Raca> query = em.createQuery("from Raca", Raca.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}
	
	public List<Raca> buscarRacaEspecifica(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Raca> query = em.createQuery("from Raca r where r.tipoRaca = :tipoRaca", Raca.class);
		query.setParameter("tipoRaca", nome);
		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public Raca buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Raca.class, id);

		} finally {
			em.close();
		}

	}

}
