package br.com.apifilmes.resources;

import java.net.URI;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.apifilmes.dao.UsuarioDao;
import br.com.apifilmes.dao.UsuarioDaoImpl;
import br.com.apifilmes.models.ErrorMessage;
import br.com.apifilmes.models.Usuario;

@ApplicationPath("v1")
@Path("/users")
public class UsuarioResource extends Application {

	@Context
	private UriInfo uriInfo;
	
	private UsuarioDao usuarioDao;
	
	@PostConstruct
	private void init() {
		this.usuarioDao = new UsuarioDaoImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getAll(){
		return usuarioDao.getAll();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Usuario usuario) {
		usuario.setId(null);
		Usuario usuarioCadastrado = usuarioDao.save(usuario);
		URI uri = uriInfo.getAbsolutePathBuilder().path(usuarioCadastrado.getId().toString()).build();
		return Response.created(uri).build();
	}
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id") Long id) {
		Usuario usuario = usuarioDao.getById(id);
		if(usuario == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + id)).build();
		}
		return Response.ok(usuario).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, Usuario usuario) {
		if(usuario.getId() != null && usuario.getId() != id) {
			return Response.status(400).entity(new ErrorMessage("Requisição inválida, id's não conferem")).build();
		}
		if(usuarioDao.getById(id) == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + id)).build();
		}
		usuario.setId(id);
		usuarioDao.save(usuario);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") Long id) {
		if(usuarioDao.getById(id) == null) {
			return Response.status(404).entity(new ErrorMessage("Não foi encontrado filme com id: " + id)).build();
		}
		usuarioDao.remove(id);
		return Response.noContent().build();
	}
}
