package com.example.mosip.mosipAuthDemo.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.jose4j.jws.JsonWebSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.EncryptionRequestDto;
import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.EncryptionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.kernel.core.util.HMACUtils2;




public class signHelper {

	private static final String SIGN_ALGO = "RS256";
	 /** The Constant KEY_STORE. */
    private static final String KEY_STORE = "PKCS12";
    /** The Constant TEMP_P12_PWD. */
    private static final char[] TEMP_P12_PWD = "1234".toCharArray();

    /** The Constant KEY_ALIAS. */
    private static final String KEY_ALIAS = "1234";
    /** The Constant PARTNER_P12_FILE_NAME. */
	private static final String PARTNER_P12_FILE_NAME = "-partner.p12";
	private static final String PARTNER_CER_FILE_NAME = "-partner.cer";
	private ObjectMapper objMapper = new ObjectMapper();
   	
	public String generateSignatureWithRequest(String Request,  String partnerId) {

	       String singResponse = null;

	       //call sing()

	       try {

	       singResponse =  sign(Request, false, true, false, null, getKeysDirPath(), partnerId);

	       } catch (Exception e) {
	              e.printStackTrace();

	       }

	       return singResponse;

	}
	
	public String sign(String dataToSign, boolean includePayload,
			boolean includeCertificate, 
			boolean includeCertHash,
			String certificateUrl,
			String dirPath,
			String partnerId) 
					throws Exception {
		
		//KeyMgrUtil keyMgrUtil = new KeyMgrUtil();
		JsonWebSignature jwSign = new JsonWebSignature();
		PrivateKeyEntry keyEntry = getKeyEntry(dirPath, partnerId);
		if (Objects.isNull(keyEntry)) {
			throw new KeyStoreException("Key file not available for partner type: " + partnerId);
		}
		PrivateKey privateKey = keyEntry.getPrivateKey();
		X509Certificate x509Certificate = getCertificateEntry(getKeysDirPath(), partnerId);
		if(x509Certificate == null) {
			x509Certificate = (X509Certificate) keyEntry.getCertificate();
		}
		if (includeCertificate)
			jwSign.setCertificateChainHeaderValue(new X509Certificate[] { x509Certificate });
		if (includeCertHash)
			jwSign.setX509CertSha256ThumbprintHeaderValue(x509Certificate);
		if (Objects.nonNull(certificateUrl))
			jwSign.setHeader("x5u", certificateUrl);
		jwSign.setPayload(dataToSign);
		jwSign.setAlgorithmHeaderValue(SIGN_ALGO);
		jwSign.setKey(privateKey);
		jwSign.setDoKeyValidation(false);
		if (includePayload)
			return jwSign.getCompactSerialization();
		return jwSign.getDetachedContentCompactSerialization();
	}
	public X509Certificate getCertificateEntry(String dirPath, String partnerId) throws KeyStoreException, IOException, CertificateException {
        
		
         
         Resource resource = new ClassPathResource("cert/"+partnerId + PARTNER_CER_FILE_NAME);
         if(resource.exists() && resource.getFile().exists())
         {
        	 File file = resource.getFile();
        	 String cert = new String(Files.readAllBytes(file.toPath()));
 			cert = trimBeginEnd(cert);
 			CertificateFactory cf = CertificateFactory.getInstance("X.509");
 			X509Certificate certificate = (X509Certificate) cf
 					.generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(cert)));
 			return certificate;
        	 
         }
         return null;
	}
	      
		
		
		

	

	public String getKeysDirPath() {
		return "cert";
	}
	public static String trimBeginEnd(String pKey) {
		pKey = pKey.replaceAll("-*BEGIN([^-]*)-*(\r?\n)?", "");
		pKey = pKey.replaceAll("-*END([^-]*)-*(\r?\n)?", "");
		pKey = pKey.replaceAll("\\s", "");
		return pKey;
	}
	public PrivateKeyEntry getKeyEntry(String dirPath, String parterId) throws Exception {
			String filePrepend = parterId;

			String partnerFilePath = dirPath + '/' + filePrepend + PARTNER_P12_FILE_NAME;
			return getPrivateKeyEntry(partnerFilePath);
	}
	private PrivateKeyEntry getPrivateKeyEntry(String filePath) throws NoSuchAlgorithmException, UnrecoverableEntryException, 
    KeyStoreException, IOException, CertificateException{
		
		Resource resource = new ClassPathResource(filePath);
	       InputStream in = resource.getInputStream();
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
	            keyStore.load(in, getP12Pass());
	            return (PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, new PasswordProtection (getP12Pass()));
            
    }
	
	private char[] getP12Pass() {
		String pass = System.getProperty("p12.password");
		return  pass == null ? TEMP_P12_PWD : pass.toCharArray();
	}
	
	public static String digestAsPlainText(byte[] data) {
		return DatatypeConverter.printHexBinary(data).toUpperCase();
	}
	public EncryptionResponseDto kernelEncrypt(EncryptionRequestDto encryptionRequestDto, String refId)
			throws Exception {
	
		String identityBlock = objMapper.writeValueAsString(encryptionRequestDto.getIdentityRequest());
		SecretKey secretKey = CryptoUtility.genSecKey();
		EncryptionResponseDto encryptionResponseDto = new EncryptionResponseDto();
		byte[] encryptedIdentityBlock = CryptoUtility.symmetricEncrypt(identityBlock.getBytes(StandardCharsets.UTF_8),
				secretKey, null);
		encryptionResponseDto.setEncryptedIdentity(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(encryptedIdentityBlock));
		X509Certificate x509Cert = getCertificateEntry(getKeysDirPath(),refId);
		PublicKey publicKey = x509Cert.getPublicKey();
		byte[] encryptedSessionKeyByte = CryptoUtility.asymmetricEncrypt(publicKey,secretKey.getEncoded() );
		encryptionResponseDto.setEncryptedSessionKey(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(encryptedSessionKeyByte));
		byte[] byteArr = CryptoUtility.symmetricEncrypt(
				digestAsPlainText(HMACUtils2.generateHash(identityBlock.getBytes(StandardCharsets.UTF_8))).getBytes(),
				secretKey,null);
		encryptionResponseDto.setRequestHMAC(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(byteArr));
		return encryptionResponseDto;
}
}
