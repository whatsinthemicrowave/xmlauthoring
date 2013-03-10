// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.springbean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class RsaFile {
	private String certificateFile;
	private String privateKeyFile;
	
	private PrivateKey      privateKey;
	private X509Certificate certificate;
	
	public void setCertificateFile(String certificateFile) {
		this.certificateFile = certificateFile;
		this.parseCertificate(this.certificateFile);
	}
	
	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
		this.parsePrivateKey(this.privateKeyFile);
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public X509Certificate getCertificate() {
		return certificate;
	}

	protected void parsePrivateKey(String keyFile) {
		try {
			FileInputStream ins = new FileInputStream(keyFile);
			byte[] encodedKey = new byte[ins.available()];
			ins.read(encodedKey);
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	protected void parseCertificate(String certFile) {
		try {
			InputStream ins = new FileInputStream(certFile);
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			certificate = (X509Certificate) certificateFactory.generateCertificate(ins);
			ins.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}
	
}
