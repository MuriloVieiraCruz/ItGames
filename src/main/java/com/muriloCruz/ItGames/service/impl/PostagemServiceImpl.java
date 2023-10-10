package com.muriloCruz.ItGames.service.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.PostagemRequestDto;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.Postagem;
import com.muriloCruz.ItGames.entity.Servico;
import com.muriloCruz.ItGames.entity.Usuario;
import com.muriloCruz.ItGames.repository.JogoRepository;
import com.muriloCruz.ItGames.repository.PostagemRepository;
import com.muriloCruz.ItGames.repository.UsuarioRepository;
import com.muriloCruz.ItGames.service.PostagemService;

@Service
public class PostagemServiceImpl implements PostagemService{
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Postagem salvar(PostagemRequestDto postagemRequestDto) {
		Jogo jogo = getJogoPor(postagemRequestDto.getServicoRequestDto().getIdDojogo());
		Usuario usuario = getUsuarioPor(postagemRequestDto.getServicoRequestDto().getIdDoUsuario());
		Servico servico = new Servico();
		servico.setDescricao(postagemRequestDto.getServicoRequestDto().getDescricao());
		servico.setPreco(postagemRequestDto.getServicoRequestDto().getPreco());
		servico.setJogo(jogo);
		servico.setUsuario(usuario);
		Postagem postagem = new Postagem();
		postagem.setImagemUrl(postagemRequestDto.getImagemUrl());
		postagem.setServico(servico);
		Postagem postagemSalva = this.postagemRepository.saveAndFlush(postagem);	
		return postagemSalva;
	}

	@Override
	public Page<Postagem> listarPor(Servico servico, Instant dataPostagem, Pageable paginacao) {
		Preconditions.checkArgument(servico != null || dataPostagem != null, 
				"É preciso informar pelo menos um serviço ou uma data para listagem");
		return this.postagemRepository.listarPor(servico, dataPostagem, paginacao);
	}

	@Override
	public Postagem buscarPor(Integer id) {
		Postagem postagemEncontrado = postagemRepository.buscarPor(id);
		Preconditions.checkNotNull(postagemEncontrado, 
				"Não foi encontrado nenhuma postagem vinculado ao id informado");
		Preconditions.checkArgument(postagemEncontrado.getServico().isPersistido() , 
				"O a postagem está inativa");
		return postagemEncontrado;
	}

	@Override
	public Postagem excluirPor(Integer id) {
		Postagem postagemEncontrado = postagemRepository.buscarPor(id);
		Preconditions.checkNotNull(postagemEncontrado, 
				"Não foi encontrado nenhuma postagem vinculado ao id informado");
		Preconditions.checkArgument(postagemEncontrado.getServico().isPersistido() , 
				"O a postagem está inativa");
		this.postagemRepository.deleteById(id);
		return postagemEncontrado;
	}
	
	private Jogo getJogoPor(Integer idDoJogo) {
		Jogo jogoEncontrado = jogoRepository.buscarPor(idDoJogo);
		Preconditions.checkNotNull(jogoEncontrado, 
				"Não foi encontrado gênero vinculado aos parâmetros passados");
		Preconditions.checkArgument(jogoEncontrado.isAtivo(),
				"O gênero informado está inativo");
		return jogoEncontrado;
	}

	private Usuario getUsuarioPor(Integer idDoUsuario) {
		Usuario usuarioEncontrado = usuarioRepository.findById(idDoUsuario).get();
		Preconditions.checkNotNull(usuarioEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros passados");
		Preconditions.checkArgument(usuarioEncontrado.isAtivo(),
				"O usuário informado está inativo");
		return usuarioEncontrado;
	}

}
