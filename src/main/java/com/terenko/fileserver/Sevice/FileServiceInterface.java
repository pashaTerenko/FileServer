package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.DTO.FileDTO;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;
import java.util.List;

public interface FileServiceInterface {
    void  addFileToCatalog(CustomUser us, Catalog catalog, FileDTO filedto)throws AccessDeniedException, DbxException, IOException;
    void  deleteFile(CustomUser us,File file) throws DbxException, IOException;

    File  getFileByName(CustomUser us,String name);
    File  getFileByUuid(CustomUser us,String uuid);
    List<File> getFilesFromCatalog(CustomUser us,Catalog catalog);

    FileDTO downloadFile(CustomUser us,File file) throws IOException, DbxException;
}
