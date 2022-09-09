package com.example.mosip.mosipAuthDemo.sendOTPAuthRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.MGF1ParameterSpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Decryptkyv_new {

	public String decrypt(String encdata) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		int tagLength = 128;
		//String encdata = "O-m-ofm2VDspFHgkB9I1_QfN1nXXIF5-Mea7puUILm74pNRqdyuSLqg3-ErvE63CX3sDKByeKGEagEkDSao6c7NBK3iJ0odRPqEFds32mD59tbYzNM6Or3911ZctXB-dlJAD6RriMUVRml1MZ2VXBU7T58j_6tVLUfi5iUjHzF5r7luQixAStSvvPglXYzUvsxvA9vpr-4QjYjCjOdzD7_xfP8xBZFc2pVztrJDITZcljWJowqpau0wlHvFWHQImDEHfm86ifiUog9czZcQY36zCPiLUR65_K_0Sw_qfPMpAOAL_TrQK25oNgSw8jfn-j8o-yHyRA9Cl2JKBF6XarSNLRVlfU1BMSVRURVIjW9jFqeFx5LHzndn_WZRtLsgA7d28217BFbJW7F_8ijZ7ShhRF3JQCuwr4_CrrgEPXXI4k_a4F9jPA3A7p13KQzh0yN77J7xCT_Xaocy_G5CuXzdOoszLPbAPDpyOBc0kupbzaEf_nIIJqNuifAINnUZIqSBzjE7gQiJcN84V4AFVswR29Q8rgUqmpvbtI1AJe9tv1-OMcCKEnDvNOAD-MSmDxiFahHYx-R5CYPiQ4stxnI13Y_p8KvMMwdWvJ4P1xRGvHl-ccAJw1eNISyLqcixc8Rsgv6cm4rNMrYQ_d_4BCRUf33xu4jHM3ujrlAeaMHJYTscDGjrnmeZEBeqeODP7p_WN4B4TmfQCS_isGrTGEHLT0wlebf-v88ZSMw9D3ANvQ0SWgKGw7dIo13Uu7yHRlWn8qutPbCWH5C0of3kiB_trrSk7nYETTIM7OMOqfLsUsh2kk8TXM30B5wNZTw46Mkyf6ObahtAjwrqc2hyFsgghWYtXtbTojBPsSjPegsOUnYjRU9aHoV3vDMqhAu4LQS1HO0LhHlOHxtLqsg";
		//String encdata ="QhIxKbKw37vnhOAD-yq751P_IBuwv82kX3p_mKcfdEhrAYaKnp2FemGH5rsRjdFzNr534n1yz7zwyrQ0t0DjOIeUFPGJMCkREurNsCAclcCotrhw885VldWYu4pa04AZ5tQ7XpDgpByFFRLVefnV6AJPWK4aDmLeJiLS3nE3wAYrKom7gMyXDttJcajoZgCbVmdF_t84JvnTR8QK_Z6-zPwP5hb8iThZAKoQhEunsXFG40rBSJumg327DVSGNkqlwMrnEgaDltWYriNnUtYnl4jIrGb7hgwMGKrsbr-uAI40Adawivq9hXVppc7d1tcpndUhC5PdxOVB1oM8TNsGpCNLRVlfU1BMSVRURVIjG91VcgftFxV6B6iwA7judJe34SbRW6n-0Nx1fyUDOLePq4jY96XourFbsTN_HYeLhQVYm0QMBLL4sL-oMSCTxxY5j0r9Z4U0UzdAm_F9WP54ILqYKKNR9aK-Sd7sr9zXr0FkuektRhAvveSzkw1HOjXNcLd53ge4ig8T4ZvcNKm3SbtE54-25vTboYpTF86h_8iCejT2CV2nQy6tZDWDGpqUM7hT7wxHpXjQcE5GkpDwN4f7y0fbK4s8V8gHpSl30xmssHt--k2ado8OW58QLZcjODXS1jEacw1N8xhQkFHGkGBcISDRaGHOwpaL59GyYlra2uO67Nja1go58YuaDvyRqIUl144U1Mprr1-sRhnx9aXKw31ZowjAKqzIzUq93MXPixVLkuxv565xM0e-Ib3DUK2aIi7TLFdM1ENfCDRB3V2a72t_pkjpfXiDB_xgv3vc8PUxKDRTY_i7kADYne54I7Jyt0WkxPRtk814w5Nz86p6PCftc8BPTmHfcsjqwTwsaN6E42eFdl85Xt5IPJTL9It0xM0FS9NJMI3d9A";
		
		byte[] b64Data = Base64.getUrlDecoder().decode (encdata.getBytes());
		System.out.println(new String(b64Data));

		byte[] keysplitter = "#KEY_SPLITTER#".getBytes();
		String p12Password = "123456";
		int pos = indexOf(b64Data,0,keysplitter, 0,0);
		System.out.println(pos);

		byte[] encKey = Arrays.copyOf(b64Data,pos);
		System.out.println(encKey.length);
		//byte[] encData = Arrays.copyOfRange(b64Data, encKey.length + keysplitter.length-1, b64Data.length);
		byte[] encData = Arrays.copyOfRange(b64Data, encKey.length + keysplitter.length, b64Data.length);
		
		Decryptkyv_new inst = new Decryptkyv_new();
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		
		
		 Resource resource = new ClassPathResource("certificates/abc_bank.p12");
	       InputStream in = resource.getInputStream();
		
		
		
		
		
//		ClassPathResource res = new ClassPathResource("certificates/abc_bank.p12");   
//		File file = new ClassPathResource("certificates/abc_bank.p12").getFile();
//		String content = new String(Files.readAllBytes(file.toPath()));
//		
//		File file = new File(res.getPath());
//		InputStream in = new FileInputStream(file);
		
		System.out.println("data.len=" + encData.length);
		
		keystore.load(in, p12Password.toCharArray());
		PrivateKey pvtkey = (PrivateKey)keystore.getKey("abc_bank", p12Password.toCharArray());

		System.out.println("cypher-data-len="+ encKey.length);
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING"); //"RSA/ECB/PKCS1Padding");

		OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
				PSpecified.DEFAULT);
		
	      //Initializing a Cipher object
	     cipher.init(Cipher.DECRYPT_MODE, pvtkey,oaepParams);
		
	     //symmetric key
	     byte[] decipheredText = cipher.doFinal(encKey);
	     //decode the data using this symmetric key
	     System.out.println(new String(decipheredText));
	     SecretKey key =  new SecretKeySpec(decipheredText, 0, decipheredText.length, "AES");
	     
	     //AES/GCM/PKCS5Padding
	    cipher = Cipher.getInstance("AES/GCM/PKCS5Padding"); //NoPadding
	    System.out.println("Cypherblocksize=" + cipher.getBlockSize());
	    byte[] randomIV = Arrays.copyOfRange(encData, encData.length - cipher.getBlockSize(), encData.length);
	    byte[] finalencData = Arrays.copyOf(encData, encData.length - cipher.getBlockSize());
	    System.out.println("IVLen=" + randomIV.length);
	    /*byte[] randomIV = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomIV);
        */
	    SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(tagLength, randomIV); 
		  
		  
				//randomIV);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

		//byte[] encDataTag = Arrays.copyOf(encData, 512);
		System.out.println("FInal datalen=" +finalencData.length);
		byte[] identity = cipher.doFinal(finalencData);
		
	     System.out.println(new String(identity));
	     
	     return new String(identity);
	     
	}
	static int indexOf(byte[] source, int sourceOffset, byte[] target, int targetOffset, int fromIndex) {
	  int sourceCount = source.length;
	  int targetCount = target.length;
	  
		if (fromIndex >= sourceCount) {
	        return (targetCount == 0 ? sourceCount : -1);
	    }
	    if (fromIndex < 0) {
	        fromIndex = 0;
	    }
	    if (targetCount == 0) {
	        return fromIndex;
	    }

	    byte first = target[targetOffset];
	    int max = sourceOffset + (sourceCount - targetCount);

	    for (int i = sourceOffset + fromIndex; i <= max; i++) {
	        /* Look for first character. */
	        if (source[i] != first) {
	            while (++i <= max && source[i] != first)
	                ;
	        }

	        /* Found first character, now look at the rest of v2 */
	        if (i <= max) {
	            int j = i + 1;
	            int end = j + targetCount - 1;
	            for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++)
	                ;

	            if (j == end) {
	                /* Found whole string. */
	                return i - sourceOffset;
	            }
	        }
	    }
	    return -1;
	}

}
