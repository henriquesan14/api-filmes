package br.com.apifilmes.utils;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAUtils implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ApiFilmes");
	
	@Produces
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
		
}
