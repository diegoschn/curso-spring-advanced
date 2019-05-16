package com.schneider.system.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.schneider.system.api.utils.PasswordUtils;

public class PasswordUtilsTest {
	
	private static final String SENHA = "123456";
	
	private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() throws Exception{
		assertNull(PasswordUtils.gerarBCrypt(null));
	}
	
	@Test
	public void testarGerarSenha() throws Exception{
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		
		assertTrue(bCrypt.matches(SENHA, hash));
	}

}
