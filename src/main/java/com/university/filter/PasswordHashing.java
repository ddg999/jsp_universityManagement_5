package com.university.filter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashing {
	private static final int SALT_LENGTH = 16;

	public static String hashPassword(String password) {
		String salt = generateSalt();
		return hashPassword(password, salt);
	}

	public static String hashPassword(String password, String salt) {
		String hashedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] hashedPasswordBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt + "$" + hashedPassword;
	}

	private static String generateSalt() {
		byte[] salt = new byte[SALT_LENGTH];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static boolean checkPassword(String password, String hashedPassword) {
		String[] saltAndPassword = hashedPassword.split("\\$");
		String salt = saltAndPassword[0];
		String hash = hashPassword(password, salt).split("\\$")[1];
		return hash.equals(saltAndPassword[1]);
	}

	public static void main(String[] args) {
		String password = "password123";
		String hashedPassword = hashPassword(password);
		System.out.println("Hashed password: " + hashedPassword);
		System.out.println("Is password correct? " + checkPassword(password, hashedPassword));
	}
}
