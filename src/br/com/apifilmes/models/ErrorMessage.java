package br.com.apifilmes.models;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public ErrorMessage(String message){
		this.message = message;
	}
	
	public ErrorMessage() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
