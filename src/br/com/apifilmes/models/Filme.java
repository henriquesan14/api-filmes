package br.com.apifilmes.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Filme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String sinopse;
	
	private double nota;
	
	private String urlImagem;
	
	@OneToMany(mappedBy = "filme")
	@JsonbTransient
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	public Filme() {
		super();
	}


	public Filme(Long id, String titulo, String sinopse, double nota, String urlImagem) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.sinopse = sinopse;
		this.nota = nota;
		this.urlImagem = urlImagem;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getSinopse() {
		return sinopse;
	}



	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}



	public double getNota() {
		return nota;
	}



	public void setNota(double nota) {
		this.nota = nota;
	}



	public String getUrlImagem() {
		return urlImagem;
	}



	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}



	public List<Comentario> getComentarios() {
		return comentarios;
	}



	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filme other = (Filme) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Filme [id=" + id + ", titulo=" + titulo + ", sinopse=" + sinopse + ", nota=" + nota + "]";
	}
	
	public void calculaMediaNota() {
		int soma = 0;
		for (Comentario comentario : this.comentarios) {
			soma += comentario.getNota();
		}
		double media = soma / this.comentarios.size();
		this.nota = media;
	}

}
