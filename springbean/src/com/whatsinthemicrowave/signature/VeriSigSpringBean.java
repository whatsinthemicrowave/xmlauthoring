// Copyright: whatsinthemicrowave.com
// For illustration only

package com.whatsinthemicrowave.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.whatsinthemicrowave.springbean.RsaFile;

public class VeriSigSpringBean {

	public static void main(String[] args) {

		if (args.length <2) {
			System.out.println("Usage: java " + VeriSigSpringBean.class.getSimpleName() + " <signature> <signed file>");
			System.exit(1);
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("resources/springbeanconfig.xml");
		
		RsaFile credential = (RsaFile) ctx.getBean("credential");
	
		X509Certificate certificate = credential.getCertificate();
		
		try {

            byte[] signitureToVerify = Base64.decodeBase64(args[0].getBytes());
 
            Signature rsaSigniture = Signature.getInstance("SHA1withRSA");
            rsaSigniture.initVerify(certificate);
 
            BufferedInputStream bufis = new BufferedInputStream(new FileInputStream(args[1]));
 
            byte[] buffer = new byte[1024];
            int len;
            while (bufis.available() != 0) {
                len = bufis.read(buffer);
                rsaSigniture.update(buffer, 0, len);
                };
 
            bufis.close();
 
            if (rsaSigniture.verify(signitureToVerify)) {
            	System.out.println("The signature is verified.");
            } else {
            	System.out.println("Signiture verification fails.");
            }
 
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
