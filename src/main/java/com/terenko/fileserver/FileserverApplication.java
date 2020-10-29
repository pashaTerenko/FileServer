package com.terenko.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableWebMvc
@EnableScheduling
public class FileserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileserverApplication.class, args);
    }

}
