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


public class DBAction extends Action implements DBCommand {

JpaRepository repository;
    ModelDB model;

    public DBAction(ModelDB model) {
        this.model = model;
    }

    @Override
    public  void execute() throws IOException {
        try {
            time = new Date();
            time.getTime();
            serverLogger.info("Entity:"+model.getClass().getName()+" with uuid:"+model.getUuid()+" perform command: "+this.toString());
            repository.save(model);
        } catch (Exception e) {
            exeptionMessage = e.toString();
            throw e;
        } finally {
            serverLogger.info(this.toString());
        }
    }

    @Override
    public DBCommand setRepository(JpaRepository repository) {
        this.repository=repository;
        return this;
    }

}

