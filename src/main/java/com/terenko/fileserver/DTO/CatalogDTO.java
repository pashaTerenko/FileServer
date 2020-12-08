package com.terenko.fileserver.DTO;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import com.terenko.fileserver.util.AccessMode;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CatalogDTO implements DTO {
    private String uuid;
    private String name;
    private AccessMode accessMode;
    private AccountDTO creator;
    private List<FileInfo> fileList;
    Set<String> haveAccess;
    public CatalogDTO(String uuid, String name, AccessMode accessMode, AccountDTO creator, List<FileInfo> fileList,Set<String> haveAccess) {
        this.uuid = uuid;
        this.name = name;
        this.accessMode = accessMode;
        this.creator = creator;
        this.fileList = fileList;
        this.haveAccess=haveAccess;
    }
    public static CatalogDTO toDto(Catalog catalog){
        List<FileInfo> fileInfos=new ArrayList<>();
        Set<String> accessUuid=new HashSet<String>() ;
        catalog.getFileList().forEach(x->fileInfos.add(FileInfo.toDto(x)));
        catalog.getHaveAccess().forEach(x->accessUuid.add(x.getUuid()));
        return new CatalogDTO(catalog.getUuid(),catalog.getName(),catalog.getAccessMode(),AccountDTO.toDto(catalog.getCreator()),fileInfos,accessUuid);
    }
}
