package com.terenko.fileserver.util.command;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Sevice.DropBoxService;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.ModelDB;
import com.terenko.fileserver.util.Action;
import com.terenko.fileserver.util.DropboxCommand;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Data

public class DeleteAction  extends Action implements DropboxCommand {
    private String filePath;

    public DeleteAction(String filePath) {
        this.filePath = filePath;
    }
  @Override
    public void execute(DropBoxService dropBoxService ) throws IOException, DbxException {

        time = new Date();
        time.getTime();
        try {
            dropBoxService.deleteFile(this);

        } catch (DbxException e) {
            exeptionMessage = e.toString();
            throw e;
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