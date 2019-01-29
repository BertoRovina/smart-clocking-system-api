package com.humbertorovina.clockingsystem.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

	public PasswordUtils() {
	}

	/**
	 * Generates a hash with BCrypt.
	 * 
	 * @param senha
	 * @return String
	 */
	public static String gerarBCrypt(String password) {
		if (password == null) {
			return password;
		}

		log.info("Generating hash with BCrypt.");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(password);
	}

}