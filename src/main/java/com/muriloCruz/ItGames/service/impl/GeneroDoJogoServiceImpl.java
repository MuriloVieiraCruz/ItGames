package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.NovoGeneroDoJogoRequestDto;
import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.GeneroDoJogo;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.composite.GeneroDoJogoId;
import com.muriloCruz.ItGames.repository.GeneroDoJogoRepository;
import com.muriloCruz.ItGames.repository.GeneroRepository;
import com.muriloCruz.ItGames.repository.JogoRepository;
import com.muriloCruz.ItGames.service.GeneroDoJogoService;

@Service
public class GeneroDoJogoServiceImpl implements GeneroDoJogoService{
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private GeneroDoJogoRepository generoDoJogoRepository;

	@Override
	public GeneroDoJogo salvar(NovoGeneroDoJogoRequestDto novoGeneroDoJogoRequestDto) {
		Genero generoEncontrado = getGeneroPor(novoGeneroDoJogoRequestDto
				.getGeneroDoJogoRequestDto().getIdDoGenero());
		Jogo jogoEncontrado = getJogoPor(novoGeneroDoJogoRequestDto.getIdDoJogo());
		GeneroDoJogoId id = new GeneroDoJogoId(generoEncontrado.getId(), jogoEncontrado.getId());
		GeneroDoJogo generoDoJogo = new GeneroDoJogo();
		generoDoJogo.setId(id);
		generoDoJogo.setTipoAssociacao(novoGeneroDoJogoRequestDto
				.getGeneroDoJogoRequestDto().getTipoAssociacao());
		generoDoJogo.setGenero(generoEncontrado);
		generoDoJogo.setJogo(jogoEncontrado);
		GeneroDoJogo generoDoJogoSlavo = generoDoJogoRepository.saveAndFlush(generoDoJogo);
		return generoDoJogoSlavo;
	}

	@Override
	public GeneroDoJogo atualizar(GeneroDoJogo generoDoJogo) {
		Preconditions.checkNotNull(generoDoJogo.getId(), 
				"O id do gênero do jogo é obrigatório");
		GeneroDoJogo generoDoJogoEncontrado = generoDoJogoRepository.findById(generoDoJogo.getId()).get();
		Preconditions.checkNotNull(generoDoJogoEncontrado, 
				"Não foi encontrado gênero de jogo vinculado aos parâmetros informados");
		Genero generoEncontrado = getGeneroPor(generoDoJogo.getGenero().getId());
		Jogo jogoEncontrado = getJogoPor(generoDoJogo.getJogo().getId());
		generoDoJogoEncontrado.setGenero(generoEncontrado);
		generoDoJogoEncontrado.setJogo(jogoEncontrado);
		generoDoJogoEncontrado.setTipoAssociacao(generoDoJogo.getTipoAssociacao());
		GeneroDoJogo generoDoJogoAtualizado = this.generoDoJogoRepository.saveAndFlush(generoDoJogoEncontrado);
		return generoDoJogoAtualizado;
	}

	@Override
	public GeneroDoJogo buscarPor(Genero genero, Jogo jogo) {
		Genero generoEncontrado = getGeneroPor(genero.getId());
		Jogo jogoEncontrado = getJogoPor(jogo.getId());
		GeneroDoJogo generoDoJogoEncontrado = this.generoDoJogoRepository.buscarPor(generoEncontrado, jogoEncontrado);
		Preconditions.checkNotNull(generoDoJogoEncontrado,
				"Não foi encontrado gênero de jogo vinculados aos parâmetros informados");
		return generoDoJogoEncontrado;
	}
	
	private Genero getGeneroPor(Integer idDoGenero) {
		Genero generoEncontrado = generoRepository.findById(idDoGenero).get();
		Preconditions.checkNotNull(generoEncontrado, 
				"Não foi encontrado gênero vinculado aos parâmetros passados");
		Preconditions.checkArgument(generoEncontrado.isAtivo(),
				"O gênero informado está inativo");
		return generoEncontrado;
	}
	
	private Jogo getJogoPor(Integer idDoJogo) {
		Jogo jogoEncontrado = jogoRepository.buscarPor(idDoJogo);
		Preconditions.checkNotNull(jogoEncontrado, 
				"Não foi encontrado gênero vinculado aos parâmetros passados");
		Preconditions.checkArgument(jogoEncontrado.isAtivo(),
				"O gênero informado está inativo");
		return jogoEncontrado;
	}

}
