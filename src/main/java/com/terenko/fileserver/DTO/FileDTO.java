package com.terenko.fileserver.DTO;

import com.terenko.fileserver.model.File;
import lombok.Data;

@Data
public class FileDTO {
    String name;
    byte[] data;
    public FileDTO(){}
}
