package com.muriloCruz.ItGames.exceptions;

public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public BusinessException(String mensagem) {
		super(mensagem);
	}
	
}
