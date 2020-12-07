package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Repository.CatalogRepository;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.util.AccessModificator;

import com.terenko.fileserver.util.command.DBAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class MainService implements ServiceInterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FileService fileService;
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    SecurityService securityService;
    @Override
    public void addCatalog(CustomUser us, String catalogName,boolean access) throws IllegalArgumentException, IOException {

        try {

            Catalog newCatalog= new Catalog(catalogName,access,us);
            us.addCatalog((Catalog)newCatalog);
            new DBAction().setRepository(catalogRepository).execute(newCatalog);
            new DBAction().setRepository(userRepository).execute(us);


        } catch (IllegalArgumentException | IOException e) {
           throw  e;
        }

    }

    @Override
    public void deleteCatalog(CustomUser us,Catalog toDel) throws IOException {

        if(securityService.getAccesssModificatorForCatalog(toDel,us)!= AccessModificator.CREATOR)
            throw new AccessDeniedException("user is not creator");
            fileService.getFilesFromCatalog(us,toDel).forEach(x-> {
                try {
                    fileService.deleteFile(us,x);
                } catch (DbxException | IOException e) {
                  e.printStackTrace();
                }
            });
        new DBAction().setRepository(catalogRepository).execute(toDel);
        new DBAction().setRepository(userRepository).execute(us);



    }

    @Override
    public Catalog getCatalogByName(CustomUser us, String CatalogName) {

       return catalogRepository.findByNameAndCreator(CatalogName,us).get(0);
    }

    @Override
    public List<Catalog> getCatalogsByName(CustomUser us, String CatalogName) {
        return catalogRepository.findByNameAndCreator(CatalogName,us);
    }

    @Override
    public Catalog getCatalogByUuid(CustomUser us, String uuid) throws AccessDeniedException {
        Catalog catalog=catalogRepository.findByUuid(uuid);
        if(securityService.getAccesssModificatorForCatalog(catalog,us)== AccessModificator.RESTRICTED)
            throw new AccessDeniedException("user is not creator");
        return catalog;
    }



    public List<Catalog> getCatalogsByUser(CustomUser us) {

        return catalogRepository.findAllByCreator(us);
    }

}
