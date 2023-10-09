package com.muriloCruz.ItGames.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muriloCruz.ItGames.dto.UsuarioRequestDto;
import com.muriloCruz.ItGames.dto.UsuarioSalvoRequestDto;
import com.muriloCruz.ItGames.entity.Usuario;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Override
	public Usuario salvar(UsuarioRequestDto usuarioRequestDto) {
		return null;
	}

	@Override
	public Usuario atualizar(UsuarioSalvoRequestDto usuarioSalvoRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Usuario> listarPor(String login, Pageable paginacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscarPor(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		
	}

	@Override
	public Usuario excluirPor(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
