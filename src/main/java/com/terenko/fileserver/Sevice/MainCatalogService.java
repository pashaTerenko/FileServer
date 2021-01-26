package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Repository.CatalogRepository;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.util.AccessModificator;

import com.terenko.fileserver.util.command.DBAction;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class MainCatalogService implements CatalogServiceInterface {

    final
    UserRepository userRepository;
    final
    FileService fileService;
    final
    CatalogRepository catalogRepository;
    final
    SecurityService securityService;

    public MainCatalogService(UserRepository userRepository, FileService fileService, CatalogRepository catalogRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.catalogRepository = catalogRepository;
        this.securityService = securityService;
    }

    @Override
    @SneakyThrows
    public void addCatalog(CustomUser us, String catalogName,boolean access)  {



            Catalog newCatalog= new Catalog(catalogName,access,us);
            us.addCatalog((Catalog)newCatalog);
            new DBAction(newCatalog).setRepository(catalogRepository).execute();
            new DBAction(us).setRepository(userRepository).execute();




    }

    @Override
    @SneakyThrows
    public void deleteCatalog(CustomUser us,Catalog toDel) {

        if(securityService.getAccesssModificatorForCatalog(toDel,us)!= AccessModificator.CREATOR)
            throw new AccessDeniedException("user is not creator");
            fileService.getFilesFromCatalog(us,toDel).forEach(x-> {

                    fileService.deleteFile(us,x);

            });

        new DBAction(toDel).setRepository(catalogRepository).execute();
        new DBAction(us).setRepository(userRepository).execute();



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
    @SneakyThrows
    public Catalog getCatalogByUuid(CustomUser us, String uuid)  {
        Catalog catalog=catalogRepository.findByUuid(uuid);
        if(securityService.getAccesssModificatorForCatalog(catalog,us)== AccessModificator.RESTRICTED)
            throw new AccessDeniedException("user havent access");
        return catalog;
    }



    public List<Catalog> getCatalogsByUser(CustomUser us) {

        return catalogRepository.findAllByCreator(us);
    }

    @Override
    @SneakyThrows
    public void addAccessToUser(CustomUser creator, CustomUser newAccess, Catalog catalog)  {
        if(securityService.getAccesssModificatorForCatalog(catalog,creator)!= AccessModificator.CREATOR)
            throw new AccessDeniedException("user is not creator");
        catalog.addAccess(newAccess);
        new DBAction(catalog).setRepository(catalogRepository).execute();
    }

    @Override
    @SneakyThrows
    public void removeAccess(CustomUser creator, CustomUser delAccess, Catalog catalog)  {
        if(securityService.getAccesssModificatorForCatalog(catalog,creator)!= AccessModificator.CREATOR)
            throw new AccessDeniedException("user is not creator");
        catalog.removeAccess(delAccess);
        new DBAction(catalog).setRepository(catalogRepository).execute();
    }

}
