package br.com.apifilmes.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.apifilmes.models.Filme;
import br.com.apifilmes.models.Usuario;
import br.com.apifilmes.utils.JPAUtils;

public class UsuarioDaoImpl implements UsuarioDao {
	
	@Inject
	private EntityManager em;

	@Override
	public Usuario login(String email, String senha) {
		em = JPAUtils.createEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.senha = ?2", Usuario.class);
		query.setParameter(1, email);
		query.setParameter(2, senha);
		try{
			Usuario usuario = query.getSingleResult();
			return usuario;
		}catch(NoResultException ex) {
			return null;
		}
	}

	@Override
	public Usuario save(Usuario usuario) {
		em  = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		if(usuario.getId() == null) {
			em.persist(usuario);
		}else {
			em.merge(usuario);
		}
		em.getTransaction().commit();
		em.close();
		return usuario;
	}
	
	public List<Usuario> getAll(){
		em = JPAUtils.createEntityManager();
		List<Usuario> result = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
		return result;
	}
	
	
	public void remove(Long id) {
		em = JPAUtils.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Usuario u WHERE u.id = ?1")	
			.setParameter(1, id)
				.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	public Usuario getById(Long id) {
		em  = JPAUtils.createEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = ?1", Usuario.class);
		query.setParameter(1, id);
		try{
			Usuario usuario = query.getSingleResult();
			return usuario;
		}catch(NoResultException ex) {
			return null;
		}
	}

}
