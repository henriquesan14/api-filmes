package br.com.apifilmes.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();
		return usuario;
	}

}
