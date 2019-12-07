package br.com.apifilmes.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.apifilmes.models.Comentario;
import br.com.apifilmes.utils.JPAUtils;

public class ComentarioDaoImpl implements ComentarioDao, Serializable{
	
	@Inject
	private EntityManager em;

	private static final long serialVersionUID = 1L;

	@Override
	public List<Comentario> getAllByFilme(Long idFilme) {
		em  = JPAUtils.createEntityManager();
		TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.filme.id = ?1", Comentario.class);
		query.setParameter(1, idFilme);
		List<Comentario> list = query.getResultList();
		return list;
	}

	@Override
	public Comentario save(Comentario comentario) {
		em  = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		if(comentario.getId() == null) {
			em.persist(comentario);
		}else {
			em.merge(comentario);
		}
		em.getTransaction().commit();
		em.close();
		return comentario;
	}

	@Override
	public void remove(Long id) {
		em = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Comentario c WHERE c.id = ?1")	
			.setParameter(1, id)
				.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Comentario getById(Long idFilme, Long idComentario) {
		em  = JPAUtils.createEntityManager();
		TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.filme.id = ?1 AND c.id = ?2", Comentario.class);
		query.setParameter(1, idFilme);
		query.setParameter(2, idComentario);
		Comentario comentario = query.getSingleResult();
		return comentario;
	}

}
