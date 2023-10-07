package com.muriloCruz.ItGames.service;

import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.NovoGeneroDoJogoRequestDto;
import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.GeneroDoJogo;
import com.muriloCruz.ItGames.entity.Jogo;

import jakarta.validation.constraints.NotNull;

@Validated
public interface GeneroDoJogoService {

	public GeneroDoJogo salvar(
			@NotNull(message = "Ambos os ID e o tipo de associação são obrigatórios ")
			NovoGeneroDoJogoRequestDto novoGeneroDoJogoRequestDto);
	
	public GeneroDoJogo atualizar(
			@NotNull(message = "O gênero do jogo é obrigatório")
			GeneroDoJogo generoDoJogo);
	
	public GeneroDoJogo buscarPor(
			@NotNull(message = "O gênero é obrigatório")
			Genero genero, 
			@NotNull(message = "O jogo é obrigatório")
			Jogo jogo);
}
