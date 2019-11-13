package br.com.animais.adocao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.animais.adocao.model.Usuario;
import br.com.animais.adocao.util.JPAUtil;

public class UsuarioDao {

	public Usuario verificaUsuario(String login, String senha) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("from Usuario u where u.login = :login " + "and u.senha = :senha",
				Usuario.class);
		query.setParameter("login", login);
		query.setParameter("senha", senha);

		try {
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public Usuario salvar(Usuario usuario) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		usuario = em.merge(usuario);
		trx.commit();
		em.close();

		return usuario;

	}

	public List<Usuario> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("from Usuario", Usuario.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public Usuario buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Usuario.class, id);

		} finally {
			em.close();
		}

	}

}
