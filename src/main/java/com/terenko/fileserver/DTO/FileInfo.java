package com.terenko.fileserver.DTO;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.File;
import lombok.Data;

@Data
public class FileInfo {
    String uuid;
    String name;
    String exetension;
    Catalog catalog;
    public FileInfo(){

    }
    public static FileInfo toDto(File file){
        FileInfo newDTO=new  FileInfo();
        newDTO.setName(file.getName());
        newDTO.setCatalog(file.getCatalog());
        return newDTO;
    }
}
