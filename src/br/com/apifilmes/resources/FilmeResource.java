package br.com.apifilmes.resources;

import java.net.URI;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.apifilmes.dao.FilmeDao;
import br.com.apifilmes.dao.FilmeDaoImpl;
import br.com.apifilmes.models.ErrorMessage;
import br.com.apifilmes.models.Filme;

@Path("/filmes")
public class FilmeResource {
	
	@Context
	private UriInfo uriInfo;
	
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
	public Response save(Filme filme) {
		filme.setId(null);
		Filme filmeCadastrado = filmeDao.save(filme);
		URI uri = uriInfo.getAbsolutePathBuilder().path(filmeCadastrado.getId().toString()).build();
		return Response.created(uri).build();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") Long id) {
		Filme filme = filmeDao.getById(id);
		if(filme == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + id)).build();
		}
		return Response.ok(filme).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, Filme filme) {
		if(filme.getId() != null && filme.getId() != id) {
			return Response.status(400).entity(new ErrorMessage("Requisição inválida, id's não conferem")).build();
		}
		if(filmeDao.getById(id) == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + id)).build();
		}
		filme.setId(id);
		filmeDao.save(filme);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") Long id) {
		if(filmeDao.getById(id) == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + id)).build();
		}
		return Response.noContent().build();
	}

}
