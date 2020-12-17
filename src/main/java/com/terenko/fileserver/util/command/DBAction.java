package com.terenko.fileserver.util.command;

import com.terenko.fileserver.model.BaseEntity;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.DBCommand;


import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.Date;


public class DBAction extends Action implements DBCommand {

JpaRepository repository;
    BaseEntity model;

    public DBAction(BaseEntity model) {
        this.model = model;
    }

    @Override
    public  void execute() throws IOException {
        try {
            time = new Date();
            time.getTime();
            serverLogger.info("Entity:"+model.getClass().getName()+" with uuid:"+model.getUuid()+" perform command: "+this.toString());
            model.setUpdated(new Date());
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

