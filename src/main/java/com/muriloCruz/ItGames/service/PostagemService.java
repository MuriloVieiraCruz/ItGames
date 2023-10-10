package com.muriloCruz.ItGames.service;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.PostagemRequestDto;
import com.muriloCruz.ItGames.entity.Postagem;
import com.muriloCruz.ItGames.entity.Servico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface PostagemService {

	public Postagem salvar(
			@Valid
			@NotNull(message = "A postagem é obrigatória")
			PostagemRequestDto postagemRequestDto);
	
	public Page<Postagem> listarPor(
			Servico servico, 
			Instant dataPostagem, 
			Pageable paginacao);
	
	public Postagem buscarPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
	
	public Postagem excluirPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
}
