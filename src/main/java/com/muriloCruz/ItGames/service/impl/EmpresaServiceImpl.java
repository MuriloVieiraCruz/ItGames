package com.muriloCruz.ItGames.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.muriloCruz.ItGames.entity.Empresa;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.repository.EmpresaRepository;
import com.muriloCruz.ItGames.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public Empresa salvar(Empresa empresa) {
		Empresa empresaEncontrada = empresaRepository.buscarPor(empresa.getNome());
		if (empresaEncontrada != null) {
			if (empresa.isPersistido()) {
				Preconditions.checkArgument(empresaEncontrada.equals(empresa),
						"Já existe uma empresa cadastrada com este nome");
			}
		}
		Empresa empresaSalva = empresaRepository.saveAndFlush(empresa);
		return empresaSalva;
	}

	@Override
	public Empresa buscarPor(Integer id) {
		Empresa empresaEncontrada = empresaRepository.findById(id).get();
		Preconditions.checkNotNull(empresaEncontrada, 
				"Não foi encontrado empresa vinculada aos parâmetros informados");
		Preconditions.checkArgument(empresaEncontrada.isAtivo(), 
				"A empresa informada está inativa");
		return empresaEncontrada;
	}

	@Override
	public Page<Empresa> listarPor(String nome, Pageable paginacao) {
		return this.empresaRepository.listarPor(nome + "%", paginacao);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Empresa empresaEncontrado = this.empresaRepository.findById(id).get();
		Preconditions.checkNotNull(empresaEncontrado, 
				"Não foi encontrado nenhum jogo vinculado ao id informado");
		Preconditions.checkArgument(empresaEncontrado.getStatus() != status , 
				"O status informado já está atribuido");
		this.empresaRepository.atualizarStatusPor(id, status);
	}

	@Override
	public Empresa excluirPor(Integer id) {
		Empresa empresaEncontrada = empresaRepository.findById(id).get();
		Preconditions.checkNotNull(empresaEncontrada, 
				"Não foi encontrado empresa vinculada aos parâmetros informados");
		Preconditions.checkArgument(empresaEncontrada.isAtivo(), 
				"A empresa informada está inativa");
		this.empresaRepository.delete(empresaEncontrada);
		return empresaEncontrada;
	}

}
