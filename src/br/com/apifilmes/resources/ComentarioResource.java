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

import br.com.apifilmes.dao.ComentarioDao;
import br.com.apifilmes.dao.ComentarioDaoImpl;
import br.com.apifilmes.dao.FilmeDao;
import br.com.apifilmes.dao.FilmeDaoImpl;
import br.com.apifilmes.models.Comentario;
import br.com.apifilmes.models.ErrorMessage;
import br.com.apifilmes.models.Filme;

@Path("/filmes/{idFilme}/comentarios")
public class ComentarioResource {
	
	@Context
	private UriInfo uriInfo;
	
	private FilmeDao filmeDao;
	private ComentarioDao comentarioDao;
	
	@PostConstruct
	private void init() {
		this.filmeDao = new FilmeDaoImpl();
		this.comentarioDao = new ComentarioDaoImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comentario> getAllByFilme(@PathParam("idFilme") Long idFilme){
		return comentarioDao.getAllByFilme(idFilme);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(@PathParam("idFilme") Long idFilme, Comentario comentario) {
		comentario.setId(null);
		Filme filme = filmeDao.getById(idFilme);
		comentario.setFilme(filme);
		Comentario comentarioCadastrado = comentarioDao.save(comentario);
		filme.getComentarios().add(comentario);
		filme.calculaMediaNota();
		this.filmeDao.save(filme);
		URI uri = uriInfo.getAbsolutePathBuilder().path(comentarioCadastrado.getId().toString()).build();
		return Response.created(uri).build();
	}
	
	
	@GET
	@Path("/{idComentario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("idFilme") Long idFilme, @PathParam("idComentario") Long idComentario) {
		Filme filme = filmeDao.getById(idFilme);
		if(filme == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + idFilme)).build();
		}
		Comentario comentario = comentarioDao.getById(idFilme, idComentario);
		if(comentario == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado comentário com id: " + idComentario)).build();
		}
		comentario.setFilme(filme);
		return Response.ok(comentario).build();
	}
	
	@PUT
	@Path("/{idComentario}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("idFilme") Long idFilme, @PathParam("idComentario") Long idComentario, Comentario comentario) {
		Filme filme = filmeDao.getById(idFilme);
		comentario.setId(idComentario);
		comentario.setFilme(filme);
		if(filme == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + idFilme)).build();
		}
		if((comentario.getFilme().getId() != null && comentario.getFilme().getId() != idFilme) ||
				comentario.getId() != null && comentario.getId() != idComentario) {
			return Response.status(400).entity(new ErrorMessage("Requisição inválida, id's não conferem")).build();
		}
		comentarioDao.save(comentario);
		filme.calculaMediaNota();
		this.filmeDao.save(filme);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{idComentario}")
	public Response remove(@PathParam("idFilme") Long idFilme, @PathParam("idComentario") Long idComentario) {
		Comentario comentario = comentarioDao.getById(idFilme, idComentario);
		if(comentario == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado comentário com id: " + idComentario)).build();
		}
		this.comentarioDao.remove(idComentario);
		return Response.noContent().build();
	}

}
