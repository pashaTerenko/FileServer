package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.util.*;
import com.terenko.fileserver.DTO.FileDTO;
import com.terenko.fileserver.Repository.CatalogRepository;
import com.terenko.fileserver.Repository.FileRepository;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import com.terenko.fileserver.util.command.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FileService implements FileServiceInterface {
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    DropBoxService dropBoxService;
    @Autowired
    SecurityService securityService;

    @Override

    public void addFileToCatalog(CustomUser us, Catalog catalog, FileDTO filedto) throws AccessDeniedException, DbxException, IOException {
        try {
            if (securityService.getAccesssModificatorForCatalog(catalog, us) == AccessModificator.RESTRICTED)
                throw new AccessDeniedException("user is havent access");

            File file = new File(filedto.getName());
            catalog.addFile(file);
            DropboxCommand uploadAction=new UploadAction(file.getPath(),filedto.getData());
            uploadAction.execute(dropBoxService);

            new DBActionFile().execute(us,file,userRepository,fileRepository);
            new DBActionCatalog().execute(us,catalog,userRepository,catalogRepository);
        } catch (AccessDeniedException | DbxException | IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public FileDTO downloadFile(CustomUser us, File file) throws IOException, DbxException, AccessDeniedException {

        try {
            if (securityService.getAccesssModificatorForFile(file, us) == AccessModificator.RESTRICTED)
                throw new AccessDeniedException("user is havent access");
            FileDTO fileDTO = new FileDTO();
            DropboxCommand downloadAction=new DownloadAction(file.getPath());
            downloadAction.execute(dropBoxService);

            fileDTO.setData( downloadAction.getResult());
            fileDTO.setName(file.getName());
            return fileDTO;
        } catch (DbxException | IOException e) {
            throw e;


        }
    }


    @Override
    public void deleteFile(CustomUser us, File file) throws DbxException, AccessDeniedException, IOException {
        if (file == null) return;
        if (securityService.getAccesssModificatorForFile(file, us) != AccessModificator.CREATOR)
            throw new AccessDeniedException("user is havent access");

        try {
            Catalog catalog = file.getCatalog();
            catalog.removeFile(file);
            file.setCatalog(null);
            try {
                DropboxCommand deleteAction=new DeleteAction(file.getPath());
                deleteAction.execute(dropBoxService);
            } catch (DeleteErrorException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            new DBActionFile().execute(us,file,userRepository,fileRepository);
            new DBActionCatalog().execute(us,catalog,userRepository,catalogRepository);
        } catch (DbxException | IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public File getFileByName(CustomUser us, String name) {
        return fileRepository.findByNameAndCatalog_Creator(name, us);
    }

    @Override
    public File getFileByUuid(CustomUser us, String uuid) {
        return fileRepository.findByUuid(uuid);
    }


    @Override
    public List<File> getFilesFromCatalog(CustomUser us, Catalog catalog) {
        return fileRepository.findAllByCatalog(catalog);
    }


}
