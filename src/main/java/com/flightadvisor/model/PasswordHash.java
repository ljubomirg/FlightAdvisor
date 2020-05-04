package com.flightadvisor.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PasswordHash {

	public static String createHash(String password) {
		String hashPassword = "";
		try {
			byte[] salt = new byte[64];
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 64);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = f.generateSecret(spec).getEncoded();
			Base64.Encoder enc = Base64.getEncoder();
			hashPassword = enc.encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			ex.printStackTrace();
		}
		return hashPassword;
	}
}
