package br.com.apifilmes.resources;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.apifilmes.dao.FilmeDao;
import br.com.apifilmes.dao.FilmeDaoImpl;
import br.com.apifilmes.models.Filme;

@Path("/filmes")
public class FilmeResource {
	
	private FilmeDao filmeDao;
	
	@PostConstruct
	private void init() {
		this.filmeDao = new FilmeDaoImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Filme> getAll(){
		return filmeDao.getAll();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Filme save(Filme filme) {
		return filmeDao.save(filme);
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Filme getById(@PathParam("id") Long id) {
		return filmeDao.getById(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") Long id, Filme filme) {
		filme.setId(id);
		filmeDao.save(filme);
	}
	
	@DELETE
	@Path("/{id}")
	public void remove(@PathParam("id") Long id) {
		filmeDao.remove(id);
	}

}
