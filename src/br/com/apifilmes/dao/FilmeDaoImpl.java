package br.com.apifilmes.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.apifilmes.models.Filme;
import br.com.apifilmes.models.Usuario;
import br.com.apifilmes.utils.JPAUtils;

public class FilmeDaoImpl implements FilmeDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	
	public List<Filme> getAll(){
		em = JPAUtils.createEntityManager();
		List<Filme> result = em.createQuery("SELECT f FROM Filme f", Filme.class).getResultList();
		return result;
	}
	
	
	public Filme save(Filme filme) {
		em  = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		if(filme.getId() == null) {
			em.persist(filme);
		}else {
			em.merge(filme);
		}
		em.getTransaction().commit();
		em.close();
		return filme;
	}
	
	public void remove(Long id) {
		em = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Filme f WHERE f.id = ?1")	
			.setParameter(1, id)
				.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	public Filme getById(Long id) {
		em  = JPAUtils.createEntityManager();
		TypedQuery<Filme> query = em.createQuery("SELECT f FROM Filme f WHERE f.id = ?1", Filme.class);
		query.setParameter(1, id);
		try{
			Filme filme = query.getSingleResult();
			return filme;
		}catch(NoResultException ex) {
			return null;
		}
	}

}
