package com.terenko.fileserver.util.command;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Repository.CatalogRepository;
import com.terenko.fileserver.Repository.FileRepository;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.Sevice.DropBoxService;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.ModelDB;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.DBCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
@Component
public class DBActionCatalog extends Action implements DBCommand {

    private String message;
    private Date time;
    private String exeptionMessage;

    public DBActionCatalog() {
    }



    @Override
    public <T extends ModelDB> void execute(CustomUser us, T t, JpaRepository userRepository, JpaRepository catalogRepository, Logger serverLogger) throws IOException {
        try {
            time = new Date();
            time.getTime();
            serverLogger.info("user: " + t.toString() + "Path" + this.toString());
            userRepository.save(us);
            catalogRepository.save((Catalog) t);

        } catch (Exception e) {
            exeptionMessage = e.toString();
            throw e;
        } finally {
            serverLogger.info(this::toString);
        }
    }
}

