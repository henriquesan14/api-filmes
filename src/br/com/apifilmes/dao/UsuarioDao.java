package br.com.apifilmes.dao;

import java.util.List;

import br.com.apifilmes.models.Usuario;

public interface UsuarioDao {
	Usuario login(String login, String senha);
	List<Usuario> getAll();
	Usuario save(Usuario usuario);
	void remove(Long id);
	Usuario getById(Long id);
}
