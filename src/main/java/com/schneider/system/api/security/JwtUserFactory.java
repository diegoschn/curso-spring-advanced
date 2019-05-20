package com.schneider.system.api.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.schneider.system.api.enums.PerfilEnum;
import com.schneider.system.api.model.Funcionario;

public class JwtUserFactory {

	private JwtUserFactory() {}
	
	
	/**
	 * 
	 * Converte e gera um JwtUser com base nos dados de um funcionário.
	 * 
	 * @param funcionario
	 * @return JwtUser
	 */
	public static JwtUser create(Funcionario funcionario) {
		return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(),
				mapToGrantedAuthorities(funcionario.getPerfil()));
	}


	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfil) {

		return null;
	}
}
