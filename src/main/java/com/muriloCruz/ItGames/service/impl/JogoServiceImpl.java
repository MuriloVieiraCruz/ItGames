package com.muriloCruz.ItGames.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.GeneroDoJogoRequestDto;
import com.muriloCruz.ItGames.dto.JogoRequestDto;
import com.muriloCruz.ItGames.dto.JogoSalvoDto;
import com.muriloCruz.ItGames.entity.Empresa;
import com.muriloCruz.ItGames.entity.Genero;
import com.muriloCruz.ItGames.entity.GeneroDoJogo;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.composite.GeneroDoJogoId;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EmpresaRepository;
import com.muriloCruz.ItGames.repository.GeneroRepository;
import com.muriloCruz.ItGames.repository.JogoRepository;
import com.muriloCruz.ItGames.service.JogoService;

@Service
public class JogoServiceImpl implements JogoService{
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private GeneroRepository generoRepository;

	@Override
	public Jogo salvar(JogoRequestDto jogoRequestDto) {
		Empresa empresaValida = getEmpresa(jogoRequestDto);
		Jogo jogo = new Jogo();
		jogo.setNome(jogoRequestDto.getNome());
		jogo.setDescricao(jogoRequestDto.getDescricao());
		jogo.setDataLanc(jogoRequestDto.getDataLanc());
		jogo.setImagemUrl(jogoRequestDto.getImagemUrl());
		jogo.setEmpresa(empresaValida);
		validarDuplicacao(jogoRequestDto.getGeneros());
		Jogo jogoSalvo = jogoRepository.save(jogo);
		for (GeneroDoJogoRequestDto generoDto: jogoRequestDto.getGeneros()) {
			Genero genero = getGeneroPor(generoDto.getIdDoGenero());
			GeneroDoJogoId id = new GeneroDoJogoId(genero.getId(), jogoSalvo.getId());
			GeneroDoJogo generoDoJogo = new GeneroDoJogo();
			generoDoJogo.setId(id);
			generoDoJogo.setTipoAssociacao(generoDto.getTipoAssociacao());
			generoDoJogo.setGenero(genero);
			generoDoJogo.setJogo(jogoSalvo);
			jogoSalvo.getGeneros().add(generoDoJogo);
		}
		this.jogoRepository.saveAndFlush(jogoSalvo);
		return this.jogoRepository.buscarPor(jogoSalvo.getId());
	}

	@Override
	public Jogo alterar(JogoSalvoDto jogoSalvoDto) {
		Empresa empresaEncontrada = empresaRepository
				.findById(jogoSalvoDto.getEmpresa().getId()).get();
		Jogo jogoEncontrado = jogoRepository.findById(jogoSalvoDto.getId()).get();
		Preconditions.checkNotNull(jogoEncontrado, 
				"Não foi encontrado jogo vinculados aos parâmetros passados");
		Preconditions.checkArgument(jogoEncontrado.isAtivo(), 
				"O jogo informado está inativo");
		jogoEncontrado.setNome(jogoSalvoDto.getNome());
		jogoEncontrado.setDescricao(jogoSalvoDto.getDescricao());
		jogoEncontrado.setDataLanc(jogoSalvoDto.getDataLanc());
		jogoEncontrado.setStatus(jogoSalvoDto.getStatus());
		jogoEncontrado.setImagemUrl(jogoSalvoDto.getImagemUrl());
		jogoEncontrado.setEmpresa(empresaEncontrada);
		Jogo jogoSalvo = jogoRepository.saveAndFlush(jogoEncontrado);
		return jogoSalvo;
	}

	@Override
	public Page<Jogo> listarPor(String nome, Genero genero, Pageable paginacao) {
		Preconditions.checkArgument(nome != null || genero != null, 
				"É preciso informar pelo menos um nome ou um genero para listagem");
		return this.jogoRepository.listarPor(nome, genero, paginacao);
	}

	@Override
	public Jogo buscarPor(String nome) {
		Preconditions.checkNotNull(nome, 
				"O nome é obrigatório");
		return this.jogoRepository.buscarPor(nome);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Jogo jogo = this.jogoRepository.findById(id).get();
		Preconditions.checkNotNull(jogo, 
				"Não foi encontrado nenhum jogo vinculado ao id informado");
		this.jogoRepository.alterarStatusPor(id, status);
	}
	
	private Empresa getEmpresa(JogoRequestDto jogoRequestDto) {
		Empresa empresaEncontrada = empresaRepository
				.findById(jogoRequestDto.getEmpresa().getId()).get();
		Preconditions.checkNotNull(empresaEncontrada, 
				"Não foi encontrado empresa vinculada aos parâmetros passados");
		Preconditions.checkArgument(empresaEncontrada.isAtivo(),
				"A empresa informada está inativa");
		return empresaEncontrada;
	}
	
	private Genero getGeneroPor(Integer idDoGenero) {
		Genero generoEncontrado = generoRepository.buscarPor(idDoGenero);
		Preconditions.checkNotNull(generoEncontrado, 
				"Não foi encontrado gênero vinculado aos parâmetros passados");
		Preconditions.checkArgument(generoEncontrado.isAtivo(),
				"O gênero informado está inativo");
		return generoEncontrado;
	}
	
	private void validarDuplicacao(List<GeneroDoJogoRequestDto> generoDoJogoDtoList) {
		for(GeneroDoJogoRequestDto generoDoJogo: generoDoJogoDtoList) {
			int quantidadeDuplicados = 0;
			for(GeneroDoJogoRequestDto outroGeneroDoJogoDto: generoDoJogoDtoList) {
				if (generoDoJogo.getIdDoGenero().equals(outroGeneroDoJogoDto.getIdDoGenero())) {
					quantidadeDuplicados++;
				}
			}
			Preconditions.checkArgument(quantidadeDuplicados >= 1,
					"Existem gêneros duplicados na lista");
		}
	}	
}
