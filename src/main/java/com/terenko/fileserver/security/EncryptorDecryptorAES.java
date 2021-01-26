package com.terenko.fileserver.security;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

@Component
public class EncryptorDecryptorAES {

    public byte[] encrypt(String password, byte[] data) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidParameterSpecException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        SecretKey key = getAESKeyFromPassword(password);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key,
                new IvParameterSpec(new byte[16]));
        byte[] ciphertext = cipher.doFinal(data);
        return  ciphertext;
    }
    public byte[] decrypt(String password, byte[] data) throws InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidParameterSpecException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        SecretKey key = getAESKeyFromPassword(password);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key,
                new IvParameterSpec(new byte[16]));

        byte[] destText = cipher.doFinal(data);
        return  destText;
    }
    public static SecretKey getAESKeyFromPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt=password.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // iterationCount = 65536
        // keyLength = 256
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }
}
