package com.terenko.fileserver.util.command;

import com.terenko.fileserver.security.EncryptorDecryptorAES;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.CipherCommand;
import lombok.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;

@Data

public class DecryptAction extends Action implements CipherCommand {
    private String key;
    private byte[] data;
    private byte[] result;
    public DecryptAction(String key, byte[] data) {
         this.key=key;
         this.data=data;
    }
  @Override
    public void execute(EncryptorDecryptorAES aes ) throws IOException {

        time = new Date();
        time.getTime();
        try {
          result=   aes.decrypt(this.key,this.data);

        } catch (InvalidKeySpecException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            exeptionMessage = e.toString();

        } finally {
            serverLogger.info(this.toString());
        }
    }


    @Override
    public String toString() {
        return "Decrypt Action"
                +" time=" + time +
                ", exeptionMessage='" + exeptionMessage + '\'' +
                '}';
    }
}