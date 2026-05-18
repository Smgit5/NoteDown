package com.suman.notedown;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class NotedownApplication {

	public static void main(String[] args) {
//		SecretKey secretKey = Jwts.SIG.HS256.key().build();
//		String secret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//		System.out.println("Secret Key = " + secret);

		SpringApplication.run(NotedownApplication.class, args);
	}

}
