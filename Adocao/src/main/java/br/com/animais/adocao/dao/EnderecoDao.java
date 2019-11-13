package br.com.animais.adocao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.com.animais.adocao.model.Endereco;
import br.com.animais.adocao.util.JPAUtil;

public class EnderecoDao {

	public Endereco salvar(Endereco endereco) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		endereco = em.merge(endereco);
		trx.commit();
		em.close();

		return endereco;

	}

	public List<Endereco> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Endereco> query = em.createQuery("from Endereco", Endereco.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public Endereco buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Endereco.class, id);

		} finally {
			em.close();
		}
	}

}
