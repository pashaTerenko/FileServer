package com.terenko.fileserver.Repository;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;


public interface CatalogRepository extends JpaRepository<Catalog,Long> {
    Catalog findByUuid(String uuid);
    List<Catalog> findByNameAndCreator(String name,CustomUser customUser);
    List<Catalog> findAllByCreator(CustomUser customUser);

}
