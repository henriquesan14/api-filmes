package br.com.apifilmes.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.apifilmes.models.Comentario;
import br.com.apifilmes.utils.JPAUtils;

public class ComentarioDaoImpl implements ComentarioDao{

	@Override
	public List<Comentario> getAllByFilme(Long idFilme) {
		EntityManager em  = JPAUtils.createEntityManager();
		TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.filme.id = ?1", Comentario.class);
		query.setParameter(1, idFilme);
		List<Comentario> list = query.getResultList();
		return list;
	}

	@Override
	public Comentario save(Comentario comentario) {
		EntityManager em  = JPAUtils.createEntityManager();
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
		EntityManager em = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		Comentario comentario = em.find(Comentario.class, id);
		em.remove(comentario);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public Comentario getById(Long idFilme, Long idComentario) {
		EntityManager em  = JPAUtils.createEntityManager();
		TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.filme.id = ?1 AND c.id = ?2", Comentario.class);
		query.setParameter(1, idFilme);
		query.setParameter(2, idComentario);
		Comentario comentario = query.getSingleResult();
		return comentario;
	}

}
