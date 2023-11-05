package com.muriloCruz.ItGames.excecoes;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.validation.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class HandlerErrorDefault {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, Object> handle(){
		return criarMapDeErro(ErroDaApi.BODY_INVALIDO, 
				"O corpo (body) da requisição possui erros ou não existe");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDefinitionException.class)
	public Map<String, Object> handle(InvalidDefinitionException ide){
		String atributo = ide.getPath().get(ide.getPath().size() - 1).getFieldName();		
		String msgDeErro = "O atributo '" + atributo + "' possui formato inválido";
	    return criarMapDeErro(ErroDaApi.FORMATO_INVALIDO, msgDeErro);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, Object> handle(ConstraintViolationException cve){

		JSONObject body = new JSONObject();

		JSONArray msgs = new JSONArray();

		body.put("erros", msgs);

		cve.getConstraintViolations().forEach((error) -> {

	    	String[] paths = error.getPropertyPath().toString().split("\\.");
	    	
	    	String atributo = paths[paths.length - 1];
	    	
	    	String errorMessage = error.getMessage();
	    	
	    	String mensagemCompleta = "\"O atributo '" + atributo + 
	    			"' apresentou o seguinte erro: '" + errorMessage + "'\"";

	    	String plainJsonError = "{ codigo:" + ErroDaApi.CONDICAO_VIOLADA.getCodigo() + 
	    			", mensagem: " + mensagemCompleta + " }";
	    	
	        msgs.put(new JSONObject(plainJsonError));

	    });

	    return body.toMap();

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public Map<String, Object> handle(IllegalArgumentException ie){
		return criarMapDeErro(ErroDaApi.PARAMETRO_INVALIDO, ie.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> handle(NullPointerException npe){
		return criarMapDeErro(ErroDaApi.PARAMETRO_INVALIDO, npe.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public Map<String, Object> handle(BusinessException be){
		return criarMapDeErro(ErroDaApi.REGRA_VIOLADA, be.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingPathVariableException.class)
	public Map<String, Object> handle(MissingPathVariableException mpve){
		return criarMapDeErro(ErroDaApi.PRECONDICAO_REQUERIDA, mpve.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Map<String, Object> handle(MethodArgumentTypeMismatchException matme){
		return criarMapDeErro(ErroDaApi.TIPO_PARAMETRO_INVALIDO, "A URI possui valores inválidos");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Map<String, Object> handle(HttpRequestMethodNotSupportedException hrmnse){
		return criarMapDeErro(ErroDaApi.METODO_HTTP_NAO_SUPORTADO, hrmnse.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Map<String, Object> handle(MissingServletRequestParameterException mrpe){
		return criarMapDeErro(ErroDaApi.PARAMETRO_OBRIGATORIO, mrpe.getMessage());
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public Map<String, Object> handle(RegistroNaoEncontradoException rnee){
		return criarMapDeErro(ErroDaApi.REGISTRO_NAO_ENCONTRADO, rnee.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConverterException.class)
	public Map<String, Object> handle(ConverterException ce){
		return criarMapDeErro(ErroDaApi.CONVERSAO_INVALIDA, ce.getMessage());
	}	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IntegracaoException.class)
	public Map<String, Object> handle(IntegracaoException ie){
		return criarMapDeErro(ErroDaApi.INTEGRACAO_INVALIDA, ie.getMessage());
	}	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public Map<String, Object> handle(InvalidDataAccessResourceUsageException ie){
		return criarMapDeErro(ErroDaApi.INTEGRACAO_INVALIDA, 
				"Ocorreu um erro de integração com a Api externa");
	}	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Map<String, Object> handlePSQLExceptions(
			DataIntegrityViolationException dve){
	    return criarMapDeErro(ErroDaApi.PARAMETRO_INVALIDO, 
	    		"Ocorreu um erro de integridade referencial na base de dados");
	}
	
	private Map<String, Object> criarMapDeErro(ErroDaApi erroDaApi, String msgDeErro){			
		
		JSONObject body = new JSONObject();					
		
		JSONObject detalhe = new JSONObject();
		detalhe.put("mensagem", msgDeErro);
		detalhe.put("codigo", erroDaApi.getCodigo());
		
		JSONArray detalhes = new JSONArray();
		detalhes.put(detalhe);
		
		body.put("erros", detalhes);
		
		return body.toMap();
		
	}
	
}
