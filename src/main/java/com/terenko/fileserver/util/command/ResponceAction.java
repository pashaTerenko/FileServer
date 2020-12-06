package com.terenko.fileserver.util.command;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Sevice.DropBoxService;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.ResponceCommand;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Data

public class ResponceAction extends Action implements ResponceCommand {
    private int statusCode;
    private String message;


    public ResponceAction(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponceAction(int statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public ResponseEntity respoce() {
        try {
            time = new Date();
            time.getTime();
            serverLogger.info(this.toString());
            return new ResponseEntity(this, HttpStatus.valueOf(statusCode));
        } catch (Exception e) {
            exeptionMessage = e.toString();
            throw e;
        } finally {
            serverLogger.info(this.toString());
        }
        }

}
