package br.com.apifilmes.dao;

import java.util.List;
import br.com.apifilmes.models.Filme;

public interface FilmeDao {
	List<Filme> getAll();
	void save(Filme filme);
	void remove(Long id);

}
