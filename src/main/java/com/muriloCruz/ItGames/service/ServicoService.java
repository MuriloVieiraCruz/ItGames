package com.muriloCruz.ItGames.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.ServicoSalvoDto;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.Servico;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface ServicoService {
	
	public Servico atualizar(
			@Valid
			@NotNull(message = "O jogo é obrigatório")
			ServicoSalvoDto servicoSalvoDto);
	
	public Page<Servico> listarPor(
			BigDecimal preco,
			Jogo jogo,
			Pageable paginacao);
	
	public Servico buscarPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
	
	public void atualizarStatusPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id,
			@NotNull(message = "O status é obrigatório")
			Status status);
	
	public Servico excluirPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);

}
