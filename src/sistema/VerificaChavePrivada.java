package sistema;

import java.io.BufferedReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class VerificaChavePrivada {

	public static boolean VerificaChave(File chave,String frase, String login) {
		try 
		{

			byte[] texto;
			FileInputStream fis = new FileInputStream(chave);
			texto = new byte[(int) chave.length()];
			fis.read(texto);
			fis.close();

			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); // cryptograph. secure random 
			random.setSeed(frase.getBytes());
			keyGen.init(56,random); 
			SecretKey key = keyGen.generateKey();

			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(texto);
			KeyFactory keyFact = KeyFactory.getInstance("RSA");
			String privKey =new String(decrypted,"ASCII").replace("-----BEGIN PRIVATE KEY-----\n", "");
			privKey = privKey.replace("-----END PRIVATE KEY-----\n", "");
			KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(privKey.getBytes()));
			PrivateKey privateKey = keyFact.generatePrivate(keySpec);

			Banco bd = new Banco();
			ByteArrayInputStream fin = new ByteArrayInputStream(bd.getCertificado(login));
			CertificateFactory f = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
			fin.close();

			PublicKey pk = certificate.getPublicKey();
			byte[] teste;
			byte[] data = new byte[512];
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(data);
			Signature s = Signature.getInstance("MD5withRSA");
			s.initSign(privateKey);
			s.update(data);
			teste = s.sign();
			s.initVerify(pk);
			s.update(data);
			return s.verify(teste);

		} catch (Exception e) {
			return false;
		}
	}


}
