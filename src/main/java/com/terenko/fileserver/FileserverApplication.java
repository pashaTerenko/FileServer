package com.terenko.fileserver;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableWebMvc
@EnableScheduling


public class FileserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileserverApplication.class, args);
    }

    @Bean("dropboxClient")
    public DbxClientV2 dropboxClient() throws DbxException {
        String ACCESS_TOKEN = "klNzdkiaDUEAAAAAAAAAAcTk2bsQyvTtrY-RQG4VGTqexbRZSSxbP4XwtPgMSbM0";
        DbxRequestConfig config = new DbxRequestConfig("dropbox/FileStorageTerenko");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }


}
