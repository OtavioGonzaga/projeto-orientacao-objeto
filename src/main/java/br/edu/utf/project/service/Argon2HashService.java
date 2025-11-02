package br.edu.utf.project.service;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("argon2")
public class Argon2HashService implements HashService {

	private static final int SALT_LENGTH = 16; // 16 bytes
	private static final int HASH_LENGTH = 32; // 32 bytes
	private static final int PARALLELISM = 1; // 1 thread
	private static final int MEMORY = 65536; // 64 MB
	private static final int ITERATIONS = 3; // 3 passes

	private final PasswordEncoder encoder;

	public Argon2HashService() {
		this.encoder = new Argon2PasswordEncoder(
				SALT_LENGTH, HASH_LENGTH, PARALLELISM, MEMORY, ITERATIONS);
	}

	@Override
	public String cypherString(String string) {
		return encoder.encode(string);
	}

	@Override
	public boolean compareString(String string, String hash) {
		return encoder.matches(string, hash);
	}
}
