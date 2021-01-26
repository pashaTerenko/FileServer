package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.security.EncryptorDecryptorAES;
import com.terenko.fileserver.util.*;
import com.terenko.fileserver.DTO.FileDTO;
import com.terenko.fileserver.Repository.CatalogRepository;
import com.terenko.fileserver.Repository.FileRepository;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import com.terenko.fileserver.util.command.*;
import lombok.SneakyThrows;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


@Service
public class FileService implements FileServiceInterface {
    final
    CatalogRepository catalogRepository;
    final
    UserRepository userRepository;
    final
    FileRepository fileRepository;
    final
    DropBoxService dropBoxService;
    final
    SecurityService securityService;
    final
    EncryptorDecryptorAES AES;

    public FileService(CatalogRepository catalogRepository, UserRepository userRepository, FileRepository fileRepository, DropBoxService dropBoxService, SecurityService securityService, EncryptorDecryptorAES aes) {
        this.catalogRepository = catalogRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.dropBoxService = dropBoxService;
        this.securityService = securityService;
        AES = aes;
    }

    @Override
    @SneakyThrows
    public void addFileToCatalog(CustomUser us, Catalog catalog, FileDTO filedto)  {

            if (securityService.getAccesssModificatorForCatalog(catalog, us) == AccessModificator.RESTRICTED)
                throw new AccessDeniedException("user is havent access");

            File file = new File(filedto.getName());
            catalog.addFile(file);
            DropboxCommand uploadAction=new UploadAction(file.getPath(),filedto.getData());
            uploadAction.execute(dropBoxService);

            new DBAction(file).setRepository(fileRepository).execute();
            new DBAction(catalog).setRepository(catalogRepository).execute();
            new DBAction(us).setRepository(userRepository).execute();


    }
@Override
@SneakyThrows
    public void addFileToCatalogWithEncryption(CustomUser us, Catalog catalog, FileDTO filedto,String key)  {

            if (securityService.getAccesssModificatorForCatalog(catalog, us) == AccessModificator.RESTRICTED)
                throw new AccessDeniedException("user is havent access");

            File file = new File(filedto.getName());
            file.setEncrypt(true);
            CipherCommand cipherCommand=new EncryptAction(key,filedto.getData());
            cipherCommand.execute(AES);
            byte[] data=cipherCommand.getResult();
            catalog.addFile(file);
            DropboxCommand uploadAction=new UploadAction(file.getPath(),data);
            uploadAction.execute(dropBoxService);

            new DBAction(file).setRepository(fileRepository).execute();
            new DBAction(catalog).setRepository(catalogRepository).execute();
            new DBAction(us).setRepository(userRepository).execute();



    }

    @Override
    @SneakyThrows
    public FileDTO downloadFile(CustomUser us, File file)  {
    if (file.isEncrypt())throw new AccessDeniedException("file is encrypt");

            if (securityService.getAccesssModificatorForFile(file, us) == AccessModificator.RESTRICTED)
                throw new AccessDeniedException("user is havent access");
            FileDTO fileDTO = new FileDTO();
            DropboxCommand downloadAction=new DownloadAction(file.getPath());
            downloadAction.execute(dropBoxService);

            fileDTO.setData( downloadAction.getResult());
            fileDTO.setName(file.getName());
            return fileDTO;

    }

    @Override
    @SneakyThrows
    public FileDTO downloadFileWithDecryption(CustomUser us, File file,String key)  {


            if (securityService.getAccesssModificatorForFile(file, us) == AccessModificator.RESTRICTED)
                throw new AccessDeniedException("user is havent access");
            FileDTO fileDTO = new FileDTO();
            DropboxCommand downloadAction=new DownloadAction(file.getPath());
            downloadAction.execute(dropBoxService);
            CipherCommand cipherCommand=new DecryptAction(key,downloadAction.getResult());
            cipherCommand.execute(AES);
            byte[] data=cipherCommand.getResult();
            fileDTO.setData( data);
            fileDTO.setName(file.getName());
            return fileDTO;

    }

    @Override
    @SneakyThrows
    public void deleteFile(CustomUser us, File file)  {
        if (file == null) return;
        if (securityService.getAccesssModificatorForFile(file, us) != AccessModificator.CREATOR)
            throw new AccessDeniedException("user is havent access");


            Catalog catalog = file.getCatalog();
            catalog.removeFile(file);
            file.setCatalog(null);

                DropboxCommand deleteAction=new DeleteAction(file.getPath());
                deleteAction.execute(dropBoxService);

            new DBAction(file).setRepository(fileRepository).execute();
            new DBAction(catalog).setRepository(catalogRepository).execute();
            new DBAction(us).setRepository(userRepository).execute();

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
