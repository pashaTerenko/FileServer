package com.terenko.fileserver.util;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.Repository.CatalogRepository;
import com.terenko.fileserver.Repository.FileRepository;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.ModelDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.logging.Logger;

import java.io.IOException;

public interface DBCommand {
    < T extends ModelDB> void execute(CustomUser us, T t, JpaRepository user,JpaRepository object,Logger serverLogger) throws IOException;
}