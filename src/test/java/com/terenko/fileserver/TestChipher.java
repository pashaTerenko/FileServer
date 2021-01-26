package com.terenko.fileserver;

import com.terenko.fileserver.security.EncryptorDecryptorAES;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

public class TestChipher {
    EncryptorDecryptorAES encryptorDecryptorAES;
    String password="uuuq111asA";
    byte[] data;
    byte[] result1;
    byte[] result2;
    @Before
    public void setUp() throws IOException {
       encryptorDecryptorAES=new EncryptorDecryptorAES();
         data = password.getBytes();
    }
    @Test
    public void  testEncrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException {
    result1= encryptorDecryptorAES.encrypt(password,data);
        result2=encryptorDecryptorAES.decrypt(password,result1);
        Assert.assertArrayEquals(result2,data);

    }
    @Test
    public void  testkey() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException {
   Assert.assertEquals(EncryptorDecryptorAES.getAESKeyFromPassword(password),EncryptorDecryptorAES.getAESKeyFromPassword(password));
    }



}
