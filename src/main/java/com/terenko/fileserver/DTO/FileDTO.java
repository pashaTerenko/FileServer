package com.terenko.fileserver.DTO;

import com.terenko.fileserver.model.File;
import lombok.Data;

@Data
public class FileDTO implements DTO {
    String name;
    byte[] data;
    public FileDTO(){}
}
