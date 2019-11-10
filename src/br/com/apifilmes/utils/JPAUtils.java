package br.com.apifilmes.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ApiFilmes");
	
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
		
}
