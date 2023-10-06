package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.JogoRequestDto;
import com.muriloCruz.ItGames.dto.JogoSalvoDto;
import com.muriloCruz.ItGames.entity.Empresa;
import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EmpresaRepository;
import com.muriloCruz.ItGames.repository.JogoRepository;
import com.muriloCruz.ItGames.service.JogoService;

@Service
public class JogoServiceImpl implements JogoService{
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	

	@Override
	public Jogo salvar(JogoRequestDto jogoRequestDto) {
		return null;
	}

	@Override
	public Jogo alterar(JogoSalvoDto jogoSalvoDto) {
		return null;
	}

	@Override
	public Page<Jogo> listarPor(String nome, Genero genero, Pageable paginacao) {
		return null;
	}

	@Override
	public Jogo buscarPor(String nome) {
		return null;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		
	}
	
	private Empresa getEmpresa(JogoRequestDto jogoRequestDto) {
		Empresa empresaEncontrada = empresaRepository
				.findById(jogoRequestDto.getEmpresa().getId()).get();
		Preconditions.checkNotNull(empresaEncontrada, 
				"Não existe empresa vinculada aos parâmetros passados");
		Preconditions.checkArgument(empresaEncontrada.isAtivo(),
				"A empresa informada está inativa");
		return empresaEncontrada;
	}
}
