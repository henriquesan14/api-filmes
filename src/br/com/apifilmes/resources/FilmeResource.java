package br.com.apifilmes.resources;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.apifilmes.dao.FilmeDao;
import br.com.apifilmes.dao.FilmeDaoImpl;
import br.com.apifilmes.models.Filme;

@Path("/filmes")
public class FilmeResource {
	
	private FilmeDaoImpl filmeDao;
	
	@PostConstruct
	private void init() {
		this.filmeDao = new FilmeDaoImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Filme> getAll(){
		return filmeDao.getAll();
	}

}
