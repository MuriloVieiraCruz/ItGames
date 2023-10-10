package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.UsuarioRequestDto;
import com.muriloCruz.ItGames.dto.UsuarioSalvoDto;
import com.muriloCruz.ItGames.entity.Usuario;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.UsuarioRepository;
import com.muriloCruz.ItGames.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Usuario salvar(UsuarioRequestDto usuarioRequestDto) {
		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioRequestDto.getLogin());
		usuario.setSenha(usuarioRequestDto.getSenha());
		usuario.setEmail(usuarioRequestDto.getEmail());
		usuario.setCpf(usuarioRequestDto.getCpf());
		usuario.setDataNasc(usuarioRequestDto.getDataNasc());
		Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
		return usuarioSalvo;
	}

	@Override
	public Usuario atualizar(UsuarioSalvoDto usuarioSalvoDto) {
		Usuario usuarioEncontrado = usuarioRepository.findById(usuarioSalvoDto.getId()).get();
		Preconditions.checkNotNull(usuarioEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(usuarioEncontrado.isAtivo(), 
				"O usuário está inativo");
		usuarioEncontrado.setLogin(usuarioSalvoDto.getLogin());
		usuarioEncontrado.setSenha(usuarioSalvoDto.getSenha());
		usuarioEncontrado.setEmail(usuarioSalvoDto.getEmail());
		usuarioEncontrado.setCpf(usuarioSalvoDto.getCpf());
		usuarioEncontrado.setDataNasc(usuarioSalvoDto.getDataNasc());
		Usuario usuarioAtualizado = usuarioRepository.saveAndFlush(usuarioEncontrado);
		return usuarioAtualizado;
	}

	@Override
	public Page<Usuario> listarPor(String login, Pageable paginacao) {
		return usuarioRepository.listarPor(login, paginacao);
	}

	@Override
	public Usuario buscarPor(Integer id) {
		Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
		Preconditions.checkNotNull(usuarioEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(usuarioEncontrado.isAtivo(), 
				"O usuário está inativo");
		return usuarioEncontrado;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
		Preconditions.checkNotNull(usuarioEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(usuarioEncontrado.isAtivo(), 
				"O usuário está inativo");
		this.usuarioRepository.atualizarStatusPor(id, status);
	}

	@Override
	public Usuario excluirPor(Integer id) {
		Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
		Preconditions.checkNotNull(usuarioEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(usuarioEncontrado.isAtivo(), 
				"O usuário está inativo");
		this.usuarioRepository.delete(usuarioEncontrado);
		return usuarioEncontrado;
	}

}
