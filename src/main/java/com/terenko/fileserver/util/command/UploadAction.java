package com.terenko.fileserver.util.command;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Sevice.DropBoxService;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.DropboxCommand;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Data

public class UploadAction extends Action implements DropboxCommand {
    private String filePath;
    private byte[] file;


    public UploadAction(String filePath, byte[] file) {
        this.filePath = filePath;
        this.file = file;

    }


    public void execute(DropBoxService dropBoxService ) {
        time = new Date();
        time.getTime();
        try {
            dropBoxService.uploadFile(this);
        } catch (IOException | DbxException e) {
            exeptionMessage = e.toString();

        } finally {
            serverLogger.info(this.toString());
        }
    }

    @Override
    public byte[] getResult() {
        return null;
    }
    @Override
    public String toString() {
        return "DownloadAction{" +
                "filePath='" + filePath + '\'' +
                ", time=" + time +
                ", exeptionMessage='" + exeptionMessage + '\'' +
                '}';
    }
}