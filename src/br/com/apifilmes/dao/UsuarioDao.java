package br.com.apifilmes.dao;

import br.com.apifilmes.models.Usuario;

public interface UsuarioDao {
	Usuario login(String login, String senha);
	Usuario save(Usuario usuario);
}
