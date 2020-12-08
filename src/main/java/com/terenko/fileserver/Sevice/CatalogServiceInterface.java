package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;


public interface CatalogServiceInterface {
    void addCatalog(CustomUser us,String catalogName,boolean accsess) throws IOException;
    void deleteCatalog(CustomUser us,Catalog catalog) throws DbxException, IOException;
    Catalog getCatalogByName(CustomUser us,String CatalogName);
    List<Catalog> getCatalogsByName(CustomUser us,String CatalogName);
    Catalog getCatalogByUuid(CustomUser us,String uuid) throws AccessDeniedException;
    List<Catalog> getCatalogsByUser(CustomUser us);
    void  addAccessToUser(CustomUser creator,CustomUser newAccess,Catalog catalog) throws IOException;
    void removeAccess(CustomUser creator,CustomUser delAccess,Catalog catalog) throws IOException;


}
