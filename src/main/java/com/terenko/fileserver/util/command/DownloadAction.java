package com.terenko.fileserver.util.command;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Sevice.DropBoxService;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.DropboxCommand;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Data

public class DownloadAction extends Action implements DropboxCommand {
    private String filePath;
    private byte[] result;
    public DownloadAction(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void execute(DropBoxService dropBoxService ) throws IOException, DbxException {
        this.filePath = filePath;
        time = new Date();
        time.getTime();
        try {
            result=dropBoxService.downloadFile(this);

        } catch (IOException | DbxException e) {
            exeptionMessage = e.toString();
            throw e;
        } finally {
            serverLogger.info(this.toString());
        }
    }

    @Override
    public String toString() {
        return "DownloadAction{" +
                "filePath='" + filePath + '\'' +
                ", time=" + time +
                ", exeptionMessage='" + exeptionMessage + '\'' +
                '}';
    }

    public byte[] getResult() {
        return result;
    }


}