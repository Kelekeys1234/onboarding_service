package com.calvary.onboarding.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@Slf4j
public class KeyUtils {

	@Value("${access-token.private}")
	private String accessTokenPrivateKeyPath;

	@Value("${access-token.public}")
	private String accessTokenPublicKeyPath;

	@Value("${refresh-token.private}")
	private String refreshTokenPrivateKeyPath;

	@Value("${refresh-token.public}")
	private String refreshTokenPublicKeyPath;

	private KeyPair _accessTokenKeyPair;
	private KeyPair _refreshTokenKeyPair;

	private KeyPair getAccessTokenKeyPair() {
		if (Objects.isNull(_accessTokenKeyPair)) {
			_accessTokenKeyPair = getKeyPair(accessTokenPublicKeyPath, accessTokenPrivateKeyPath);
		}
		return _accessTokenKeyPair;
	}

	private KeyPair getRefreshTokenKeyPair() {
		if (Objects.isNull(_refreshTokenKeyPair)) {
			_refreshTokenKeyPair = getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath);
		}
		return _refreshTokenKeyPair;
	}

	private KeyPair getKeyPair(String publicKeyPath, String privateKeyPath) {
		KeyPair keyPair;

		File publicKeyFile = new File(publicKeyPath);
		File privateKeyFile = new File(privateKeyPath);

		if (publicKeyFile.exists() && privateKeyFile.exists()) {
			log.info("loading keys from file: {}, {}", publicKeyPath, privateKeyPath);
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");

				byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
				EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
				PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

				byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
				PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
				PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

				keyPair = new KeyPair(publicKey, privateKey);
				return keyPair;
			} catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}

		}

		File directory = new File("access-refresh-token-keys");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		try {
			log.info("Generating new public and private keys: {}, {}", publicKeyPath, privateKeyPath);
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
			try (FileOutputStream fos = new FileOutputStream(publicKeyPath)) {
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
				fos.write(keySpec.getEncoded());
			}

			try (FileOutputStream fos = new FileOutputStream(privateKeyPath)) {
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
				fos.write(keySpec.getEncoded());
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			log.info("Error while writing private key: NoSuchAlgorithmException - " + e.getMessage());
			throw new RuntimeException("Error while writing private key: NoSuchAlgorithmException - " + e.getMessage());
		}

		return keyPair;
	}

	public RSAPublicKey getAccessTokenPublicKey() {
		return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
	};

	public RSAPrivateKey getAccessTokenPrivateKey() {
		return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
	};

	public RSAPublicKey getRefreshTokenPublicKey() {
		return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
	};

	public RSAPrivateKey getRefreshTokenPrivateKey() {
		return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
	};
}
