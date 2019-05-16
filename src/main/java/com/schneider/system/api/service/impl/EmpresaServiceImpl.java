package com.schneider.system.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schneider.system.api.model.Empresa;
import com.schneider.system.api.repositories.EmpresaRepository;
import com.schneider.system.api.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	/**
	 * Logger para informar em tempo de execução o que o classe/método está fazendo.
	 * Isso é uma boa prática.
	 */
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscando uma empresa para o cnpj {}", cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));//offNullabe.. evita aparecer null pointer exception	
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Persistindo empresa {}", empresa);
		return this.empresaRepository.save(empresa);
	}

}
