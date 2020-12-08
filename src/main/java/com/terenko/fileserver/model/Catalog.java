package com.terenko.fileserver.model;

import com.terenko.fileserver.util.AccessMode;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data

public class Catalog implements ModelDB{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")

    private String uuid;
    private String name;
    private AccessMode accessMode;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creatorId", nullable = false)
    private CustomUser creator;
    @OneToMany(mappedBy = "catalog")
    private List<File> fileList=new ArrayList<>();
    @ElementCollection(fetch = FetchType.LAZY)
    Set<CustomUser> haveAccess=new HashSet<>();

    public Catalog(String name, boolean access, CustomUser us) {

        this.name = name;

        this.creator = us;
        this.accessMode = access ? AccessMode.PRIVATE : AccessMode.PUBLIC;

    }

    public Catalog() {
    }


    public void addFile(File file) {
        file.setCatalog(this);
        fileList.add(file);

    }

    public void removeFile(File file) {
        fileList.remove(file);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.getName()).append(" ").append(this.getUuid()).append(" ").append(this.getCreator().getUuid()).toString();
    }
    public void addAccess( CustomUser us) {

        haveAccess.add(us);

    }

    public void removeAccess(CustomUser us) {
        haveAccess.remove(us);
    }
}
