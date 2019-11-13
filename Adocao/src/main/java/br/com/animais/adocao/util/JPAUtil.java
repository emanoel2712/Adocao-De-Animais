package br.com.animais.adocao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory factory;
	
	static {
		factory = Persistence.createEntityManagerFactory("AdocaoAnimaisPU");
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
