package com.terenko.fileserver.util;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Sevice.DropBoxService;

import java.io.IOException;
import java.util.logging.Logger;

public interface DropboxCommand {
     void execute(DropBoxService dropBoxService ,Logger serverLogger) throws IOException, DbxException;
     byte[]  getResult();
}
