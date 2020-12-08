package com.terenko.fileserver.DTO;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.File;
import lombok.Data;

@Data
public class FileInfo implements DTO{
    String uuid;
    String name;
    String exetension;
    String catalogUuid;
    public FileInfo(){

    }
    public static FileInfo toDto(File file){
        FileInfo newDTO=new  FileInfo();
        newDTO.setName(file.getName());
        newDTO.setUuid(file.getUuid());
        newDTO.setCatalogUuid(file.getCatalog().getUuid());
        return newDTO;
    }
}
