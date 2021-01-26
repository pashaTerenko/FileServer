package com.terenko.fileserver.Sevice;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.terenko.fileserver.util.command.DeleteAction;
import com.terenko.fileserver.util.command.DownloadAction;
import com.terenko.fileserver.util.command.UploadAction;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DropBoxService {


    @Autowired
    DbxClientV2 dropboxClient;


    public void uploadFile(UploadAction upload) throws IOException, DbxException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(upload.getFile());
        Metadata uploadMetaData = dropboxClient.files().uploadBuilder(upload.getFilePath()).uploadAndFinish(inputStream);

        inputStream.close();
    }


    public List<Map<String, Object>> getFileList(String target) throws JsonProcessingException, DbxException {

        List<Metadata> entries = dropboxClient.files().listFolder(target).getEntries();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Metadata entry : entries) {
            if (entry instanceof FileMetadata) {

            }
            String metaDataString = entry.toString();
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map = mapper.readValue(metaDataString, new TypeReference<Map<String, Object>>() {
            });
            result.add(map);
//			if ("file".equals(map.get(".tag"))) {
//				GetTemporaryLinkResult temp = dropboxClient.files().getTemporaryLink(entry.getPathLower());
//				logger.info("thumbnail ==> {}", temp);
//			}
        }

        return result;
    }


    public byte[] downloadFile(DownloadAction download) throws IOException, DbxException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        dropboxClient.files().downloadBuilder(download.getFilePath()).download(outputStream).getSize();
        return outputStream.toByteArray();
    }

    public void deleteFile(DeleteAction delete) throws DbxException {
        dropboxClient.files().delete(delete.getFilePath());
    }

}
