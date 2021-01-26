package com.terenko.fileserver.util;

import com.terenko.fileserver.security.EncryptorDecryptorAES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;

public interface CipherCommand {
    void execute(EncryptorDecryptorAES aes) throws IOException;
     byte[]  getResult();
}
