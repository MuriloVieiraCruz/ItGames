package com.muriloCruz.ItGames.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.dto.ServicoSalvoDto;
import com.muriloCruz.ItGames.entity.Jogo;
import com.muriloCruz.ItGames.entity.Servico;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.JogoRepository;
import com.muriloCruz.ItGames.repository.ServicoRepository;
import com.muriloCruz.ItGames.service.ServicoService;

@Service
public class ServicoServiceImpl implements ServicoService{
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@Override
	public Servico atualizar(ServicoSalvoDto servicoSalvoDto) {
		Servico servicoEncontrado = servicoRepository.buscarPor(servicoSalvoDto.getId());
		Jogo jogo = getJogoPor(servicoSalvoDto.getIdDojogo());
		Preconditions.checkNotNull(servicoEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(servicoEncontrado.isAtivo(), 
				"O usuário está inativo");
		servicoEncontrado.setDescricao(servicoSalvoDto.getDescricao());
		servicoEncontrado.setDisponibilidade(servicoSalvoDto.getDisponibilidade());
		servicoEncontrado.setPreco(servicoSalvoDto.getPreco());
		servicoEncontrado.setJogo(jogo);
		Servico servicoAtualizado = servicoRepository.saveAndFlush(servicoEncontrado);
		return servicoAtualizado;
	}

	@Override
	public Page<Servico> listarPor(BigDecimal preco, Jogo jogo, Pageable paginacao) {
		Preconditions.checkArgument(preco != null || jogo != null, 
				"É preciso informar pelo menos um preço ou um jogo para listagem");
		return servicoRepository.listarPor(preco, jogo, paginacao);
	}

	@Override
	public Servico buscarPor(Integer id) {
		Servico servicoEncontrado = servicoRepository.buscarPor(id);
		Preconditions.checkNotNull(servicoEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(servicoEncontrado.isAtivo(), 
				"O usuário está inativo");
		return servicoEncontrado;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Servico servicoEncontrado = this.servicoRepository.findById(id).get();
		Preconditions.checkNotNull(servicoEncontrado, 
				"Não foi encontrado nenhum jogo vinculado ao id informado");
		Preconditions.checkArgument(servicoEncontrado.getStatus() != status , 
				"O status informado já está atribuido");
		this.servicoRepository.atualizarStatusPor(id, status);
		
	}

	@Override
	public Servico excluirPor(Integer id) {
		Servico servicoEncontrado = servicoRepository.buscarPor(id);
		Preconditions.checkNotNull(servicoEncontrado, 
				"Não foi encontrado usuário vinculado aos parâmetros");
		Preconditions.checkArgument(servicoEncontrado.isAtivo(), 
				"O usuário está inativo");
		this.servicoRepository.delete(servicoEncontrado);
		return servicoEncontrado;
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
