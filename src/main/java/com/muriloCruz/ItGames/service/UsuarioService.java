package com.muriloCruz.ItGames.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import com.muriloCruz.ItGames.dto.UsuarioRequestDto;
import com.muriloCruz.ItGames.dto.UsuarioSalvoRequestDto;
import com.muriloCruz.ItGames.entity.Usuario;
import com.muriloCruz.ItGames.entity.enums.Status;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface UsuarioService {
	
	public Usuario salvar(
			@Valid
			@NotNull(message = "O jogo é obrigatório")
			UsuarioRequestDto usuarioRequestDto);
	
	public Usuario atualizar(
			@Valid
			@NotNull(message = "O jogo é obrigatório")
			UsuarioSalvoRequestDto usuarioSalvoRequestDto);
	
	public Page<Usuario> listarPor(
			@Size(max = 250, min = 3, message = "O nome deve conter entre 3 e 250 caracteres")
			@NotBlank(message = "O login é obrigatório")
			String login,  
			Pageable paginacao);
	
	public Usuario buscarPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
	
	public void atualizarStatusPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id,
			@NotNull(message = "O status é obrigatório")
			Status status);
	
	public Usuario excluirPor(
			@Positive(message = "O ID precisa ser maior que 0")
			@NotNull(message = "O ID é obrigatório")
			Integer id);
}
