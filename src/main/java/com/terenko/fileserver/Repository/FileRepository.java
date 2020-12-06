package com.terenko.fileserver.Repository;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FileRepository extends JpaRepository<File,String> {
File findByUuid(String uuid);
File findByNameAndCatalog_Creator(String name, CustomUser creator);
List<File> findAllByName(String name);
List<File> findAllByCatalog(Catalog catalog);
}
