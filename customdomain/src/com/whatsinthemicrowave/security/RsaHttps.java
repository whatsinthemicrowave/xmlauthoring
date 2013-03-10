// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class RsaHttps {
	private String certificateURL;
	private String privateKeyURL;
	
	private PrivateKey      privateKey;
	private X509Certificate certificate;
	
	public void setCertificateURL(String certificateURL) {
		this.certificateURL = certificateURL;
		this.parseCertificate(this.certificateURL);
	}
	
	public void setPrivateKeyURL(String privateKeyURL) {
		this.privateKeyURL = privateKeyURL;
		this.parsePrivateKey(this.privateKeyURL);
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public X509Certificate getCertificate() {
		return certificate;
	}

	protected void parsePrivateKey(String keyURL) {
		try {
			HttpClient httpClient = new HttpClient();
			GetMethod httpGet = new GetMethod(keyURL); 
			
			httpClient.executeMethod(httpGet);
			
			byte[] encodedKey = httpGet.getResponseBody();
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void parseCertificate(String certURL) {
		try {
			HttpClient httpClient = new HttpClient();
			GetMethod httpGet = new GetMethod(certURL); 
			
			httpClient.executeMethod(httpGet);
			
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			certificate = (X509Certificate) certificateFactory.generateCertificate(httpGet.getResponseBodyAsStream());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}
	
}
