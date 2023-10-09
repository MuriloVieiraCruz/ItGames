package com.muriloCruz.ItGames.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface GeneroService {

	public Genero salvar(
			@Valid
			@NotNull(message = "O gênero é obrigatória")
			Genero genero);
	
	public Genero buscarPor(
			@Positive(message = "O ID deve ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
	
	public Page<Genero> listarPor(
			@Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
			@NotBlank(message = "O nome é obrigatório")
			String nome, 
			Pageable paginacao);
	
	public void atualizarStatusPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id,
			@NotNull(message = "O status é obrigatório")
			Status status);
	
	public Genero excluirPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
}
