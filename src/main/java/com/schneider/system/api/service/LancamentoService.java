package com.schneider.system.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.schneider.system.api.model.Lancamento;

public interface LancamentoService {

	/**
	 * 
	 * Retorna uma lista paginada de lançamentos de um determinado funcionário
	 * 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 */
	Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	
	/**
	 * 
	 * Retorna um lancamento por ID
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 */
	Optional<Lancamento> buscarPorId(Long id);
	
	/**
	 * 
	 * Persiste um lancamento na base de dados
	 * 
	 * @param lancamento
	 * @return lancamento
	 */
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * 
	 * Remove um lancamento da base de dados.
	 * 
	 * @param id
	 */
	void remover(Long id);
}
