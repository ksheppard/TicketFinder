/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * 
 */
public class Encryptor {

    private static MessageDigest md;

    private static byte[] keyBytes;
    private static byte[] initBytes;

    public static String mD5Encryption(String password) {

        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = password.getBytes();
            md.reset();
            byte[] digested = md.digest(passwordBytes);
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String encrypt(String message) throws Exception {
    	final MessageDigest md = MessageDigest.getInstance("md5");
    	final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
    			.getBytes("utf-8"));
    	final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
    	for (int j = 0, k = 16; j < 8;) {
    		keyBytes[k++] = keyBytes[j++];
    	}

    	final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
    	final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
    	final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    	cipher.init(Cipher.ENCRYPT_MODE, key, iv);

    	final byte[] plainTextBytes = message.getBytes("utf-8");
    	final byte[] cipherText = cipher.doFinal(plainTextBytes);
    	// final String encodedCipherText = new sun.misc.BASE64Encoder()
    	// .encode(cipherText);

    	return new String(Base64.encodeBase64(cipherText));
    }

    public static String decrypt(String inputString) throws Exception {
    	final byte[] message = new Base64().decode(inputString);
        final MessageDigest md = MessageDigest.getInstance("md5");
    	final byte[] digestOfPassword = md.digest("HG58YZ3CR9"
    			.getBytes("utf-8"));
    	final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
    	for (int j = 0, k = 16; j < 8;) {
    		keyBytes[k++] = keyBytes[j++];
    	}

    	final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
    	final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
    	final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    	decipher.init(Cipher.DECRYPT_MODE, key, iv);

    	// final byte[] encData = new
    	// sun.misc.BASE64Decoder().decodeBuffer(message);
    	final byte[] plainText = decipher.doFinal(message);

    	return new String(plainText, "UTF-8");
    }

}
