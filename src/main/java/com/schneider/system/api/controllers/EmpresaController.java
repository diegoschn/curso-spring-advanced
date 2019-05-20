package com.schneider.system.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schneider.system.api.dtos.EmpresaDto;
import com.schneider.system.api.model.Empresa;
import com.schneider.system.api.response.Response;
import com.schneider.system.api.service.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins="*")
public class EmpresaController {

	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	/**
	 * 
	 * Retorna uma empresa dado um CNPJ.
	 * 
	 * @param cnpj
	 * @return
	 */
	@GetMapping(value="/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj){
		log.info("Buscando empresa por CNPJ: {}", cnpj);
		Response<EmpresaDto> response = new Response<>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		
		if(!empresa.isPresent()) {
			log.info("Empresa não encontrada para o CNPJ {}", cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * 
	 * Popula um DTO com os dados de uma empresa.
	 * 
	 * @param empresa
	 * @return dto
	 */
	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		EmpresaDto dto = new EmpresaDto();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		return dto;
	}
}
