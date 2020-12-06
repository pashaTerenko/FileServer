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


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Date;

@Component
public class DBActionCatalog extends Action implements DBCommand {



    public DBActionCatalog() {
    }



    @Override
    public <T extends ModelDB> void execute(CustomUser us, T t, JpaRepository userRepository, JpaRepository catalogRepository) throws IOException {
        try {
            time = new Date();
            time.getTime();
            serverLogger.info("path: "+t.getUuid()+this.toString());
            userRepository.save(us);
            catalogRepository.save((Catalog) t);

        } catch (Exception e) {
            exeptionMessage = e.toString();
            throw e;
        } finally {
            serverLogger.info(this.toString());
        }
    }
}

