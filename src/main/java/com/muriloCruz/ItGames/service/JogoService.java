package com.muriloCruz.ItGames.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.JogoRequestDto;
import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface JogoService {

	public Jogo salvar(
			@Valid
			@NotNull(message = "O jogo é obrigatório")
			JogoRequestDto jogoRequestDto);
	
	public Jogo alterar();
	
	public Page<Jogo> listarPor(
			String nome, 
			Genero genero, 
			Pageable paginacao);
	
	public Jogo buscarPor(
			@Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
			@NotBlank(message = "O nome é obrigatório")
			String nome);
	
	public void atualizarStatusPor(
			@Positive(message = "O id do jogo precisa ser maior que 0")
			@NotNull(message = "O id do jogo é obrigatório")
			Integer id, 
			Status status);
}
