package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.GeneroRepository;
import com.muriloCruz.ItGames.service.GeneroService;

@Service
public class GeneroServiceImpl implements GeneroService{
	
	@Autowired
	private GeneroRepository generoRepository;

	@Override
	public Genero salvar(Genero genero) {
		Genero generoEncontrado = generoRepository.buscarPor(genero.getNome());
		if (generoEncontrado != null) {
			if (genero.isPersistido()) {
				Preconditions.checkArgument(generoEncontrado.equals(genero),
						"Já existe um gênero cadastrado com esse nome");
			}
		}
		Genero generoSalvo = generoRepository.save(genero);
		return generoSalvo;
	}

	@Override
	public Genero buscarPor(Integer id) {
		Genero generoEncontrada = generoRepository.findById(id).get();
		Preconditions.checkNotNull(generoEncontrada, 
				"Não foi encontrado empresa vinculada aos parâmetros informados");
		Preconditions.checkArgument(generoEncontrada.isAtivo(), 
				"A empresa informada está inativa");
		return generoEncontrada;
	}

	@Override
	public Page<Genero> listarPor(String nome, Pageable paginacao) {
		return this.generoRepository.listarPor(nome + "%", paginacao);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Genero generoEncontrada = generoRepository.findById(id).get();
		Preconditions.checkNotNull(generoEncontrada, 
				"Não foi encontrado empresa vinculada aos parâmetros informados");
		Preconditions.checkArgument(generoEncontrada.isAtivo(), 
				"A empresa informada está inativa");
		this.generoRepository.atualizarStatusPor(id, status);
	}

	@Override
	public Genero excluirPor(Integer id) {
		Genero generoEncontrada = generoRepository.findById(id).get();
		Preconditions.checkNotNull(generoEncontrada, 
				"Não foi encontrado empresa vinculada aos parâmetros informados");
		Preconditions.checkArgument(generoEncontrada.isAtivo(), 
				"A empresa informada está inativa");
		this.generoRepository.delete(generoEncontrada);
		return generoEncontrada;
	}

}
