package com.terenko.fileserver.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class File extends BaseEntity {

    private String name;
    private String path;
    private boolean isEncrypt=false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="catalogId")
    private Catalog catalog;
    public File() {

    }

    public File(String name) {
        this.name = name;

        path="/"+getName();
    }

    public void setCatalog(Catalog catalog) {
        this.catalog=catalog;
        try {
            this.path = new StringBuilder().append("/").append(catalog.getCreator().getUuid()).append("/").append(catalog.getUuid()).append("/").append(this.getName()).toString();
        } catch (NullPointerException e) {
        }
    }
    @Override
    public String toString() {
        return new StringBuilder().append(this.getName()).toString();
    }
}
