/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.animais.adocao.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.animais.adocao.model.Ong;
import br.com.animais.adocao.util.JPAUtil;

/**
 *
 * @author emanoel.justino
 */
public class OngDao {

	public Ong salvar(Ong ong) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		ong = em.merge(ong);
		trx.commit();
		em.close();

		return ong;

	}

	public List<Ong> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Ong> query = em.createQuery("from Ong", Ong.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public Ong buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Ong.class, id);

		} finally {
			em.close();
		}

	}

	public Ong ongUsuario(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Ong> query = em.createQuery("from Ong o where o.usuario.id = :id", Ong.class);
		query.setParameter("id", id);

		try {
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	public Ong ongEmail(String email) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Ong> query = em.createQuery("from Ong o where o.email = :email", Ong.class);
		query.setParameter("email", email);

		try {
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

}
