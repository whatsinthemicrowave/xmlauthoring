package com.whatsinthemicrowave.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.whatsinthemicrowave.security.RsaFile;

public class GenSigRsaFile {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java " + GenSigRsaFile.class.getSimpleName() + " <file to sign>");
			System.exit(1);
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("resources/customconfig.xml");
		
		RsaFile credential = (RsaFile) ctx.getBean("credential");
	
		PrivateKey key = credential.getPrivateKey();
		
		try {
			Signature rsaSignature = Signature.getInstance("SHA1withRSA");
			rsaSignature.initSign(key);
			
			BufferedInputStream bufis = new BufferedInputStream(new FileInputStream(args[0]));
			byte[] buffer = new byte[1024];
			int len;
			while (bufis.available() != 0) {
				len = bufis.read(buffer);
				rsaSignature.update(buffer, 0, len);
			};
			bufis.close();
			
            byte[] signature = rsaSignature.sign();
            
            byte[] base64EncodedSignature = Base64.encodeBase64(signature);
            System.out.println("BASE64 encoded signature is: \"" + new String(base64EncodedSignature) + "\"");
 
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
