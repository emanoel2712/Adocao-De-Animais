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

import br.com.animais.adocao.animalDaoInterface.AnimalDaoInterface;
import br.com.animais.adocao.model.Animal;
import br.com.animais.adocao.util.JPAUtil;

/**
 *
 * @author emanoel.justino
 */
public class AnimalDao implements AnimalDaoInterface<Animal> {

	public Animal salvar(Animal animal) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trx = em.getTransaction();

		trx.begin();
		animal = em.merge(animal);
		trx.commit();
		em.close();

		return animal;

	}

	public void excluir(Animal animal) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction trx = manager.getTransaction();

		try {
			trx.begin();
			manager.remove(manager.find(Animal.class, animal.getId()));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			manager.close();
		}
	}

	public List<Animal> buscarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal", Animal.class);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisOng(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.ong.id = :id", Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisPessoa(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.pessoa.id = :id", Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisOngAdocao(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.ong.id = :id and a.status = 'adocao'",
				Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisPessoaAdocao(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.pessoa.id = :id and a.status = 'adocao'",
				Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisOngProcessoAdocao(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery(
				"from Animal a where a.ong.id = :id and (a.status = 'Processo de adocao' or a.status = 'Adotado')",
				Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisPessoaProcessoAdocao(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery(
				"from Animal a where a.pessoa.id = :id and (a.status = 'Processo de adocao' or a.status = 'Adotado')",
				Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisOngExcluido(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.ong.id = :id and a.status = 'excluido'",
				Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisPessoaExcluido(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.pessoa.id = :id and a.status = 'excluido'",
				Animal.class);
		query.setParameter("id", id);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public Animal buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();

		try {
			return em.find(Animal.class, id);

		} finally {
			em.close();
		}

	}

	public List<Animal> buscarAnimalPorStatus(String status) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.status = :status", Animal.class);
		query.setParameter("status", status);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimalPorTipo(String tipo) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.tipoAnimal = :tipo and a.status = 'adocao'",
				Animal.class);
		query.setParameter("tipo", tipo);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisTodos(String tipo, String raca, String estado) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery(
				"from Animal a where a.tipoAnimal = :tipo and a.raca.nome = :raca and a.estado = :estado and a.status = 'adocao'",
				Animal.class);
		query.setParameter("tipo", tipo);
		query.setParameter("raca", raca);
		query.setParameter("estado", estado);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisTipoRaca(String tipo, String raca) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery(
				"from Animal a where a.tipoAnimal = :tipo and a.raca.nome = :raca and a.status = 'adocao'",
				Animal.class);
		query.setParameter("tipo", tipo);
		query.setParameter("raca", raca);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisTipoEstado(String tipo, String estado) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery(
				"from Animal a where a.tipoAnimal = :tipo and a.estado = :estado and a.status = 'adocao'",
				Animal.class);
		query.setParameter("tipo", tipo);
		query.setParameter("estado", estado);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisRacaEstado(String raca, String estado) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery(
				"from Animal a where a.raca.nome = :raca and a.estado = :estado and a.status = 'adocao'", Animal.class);
		query.setParameter("raca", raca);
		query.setParameter("estado", estado);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisSomenteTipo(String tipo) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.tipoAnimal = :tipo and a.status = 'adocao'",
				Animal.class);
		query.setParameter("tipo", tipo);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

	public List<Animal> buscarAnimaisSomenteEstado(String estado) {
		EntityManager em = JPAUtil.getEntityManager();
		TypedQuery<Animal> query = em.createQuery("from Animal a where a.estado = :estado and a.status = 'adocao'",
				Animal.class);
		query.setParameter("estado", estado);

		try {
			return query.getResultList();

		} finally {
			em.close();
		}
	}

}
