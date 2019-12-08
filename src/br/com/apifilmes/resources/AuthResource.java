package br.com.apifilmes.resources;

import java.net.URI;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import br.com.apifilmes.dao.UsuarioDao;
import br.com.apifilmes.dao.UsuarioDaoImpl;
import br.com.apifilmes.models.ErrorMessage;
import br.com.apifilmes.models.Usuario;

@ApplicationPath("v1")
@Path("/")
public class AuthResource {
	
	@Context
	private UriInfo uriInfo;
	
	private UsuarioDao usuarioDao;
	
	@PostConstruct
	private void init() {
		this.usuarioDao = new UsuarioDaoImpl();
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Usuario usuario){
		try{
			usuario.setId(null);
			Usuario usuarioCadastrado = usuarioDao.save(usuario);
			URI uri = uriInfo.getAbsolutePathBuilder().path(usuarioCadastrado.getId().toString()).build();
			return Response.created(uri).build();
		}catch(Exception ex) {
			return Response.status(400).entity(new ErrorMessage("Houve um erro no servidor")).build();
		}
		
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(Usuario usuario){
		Usuario usuarioBuscado = this.usuarioDao.login(usuario.getEmail(), usuario.getSenha());
		if(usuarioBuscado != null) {
			return Response.ok(usuarioBuscado).build();
		}
		return Response.status(401).entity(new ErrorMessage("Email/senha inválido(s)")).build();
	}
}
