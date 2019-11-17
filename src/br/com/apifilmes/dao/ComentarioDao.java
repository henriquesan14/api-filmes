package br.com.apifilmes.dao;

import java.util.List;

import br.com.apifilmes.models.Comentario;

public interface ComentarioDao {
	List<Comentario> getAllByFilme(Long idFilme);
	Comentario save(Comentario filme);
	void remove(Long id);
	Comentario getById(Long idFilme, Long idComentario);
}
