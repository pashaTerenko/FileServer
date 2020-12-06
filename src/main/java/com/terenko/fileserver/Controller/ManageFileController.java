package com.terenko.fileserver.Controller;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.DTO.FileDTO;
import com.terenko.fileserver.Sevice.FileService;
import com.terenko.fileserver.Sevice.MainService;
import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.util.command.ResponceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class ManageFileController {
    @Autowired
    MainService mSr;
    @Autowired
    UserService uSr;
    @Autowired
    FileService fSr;
    @Autowired
    Logger serverLogger;
    @PostMapping("/addFile/{catalogID}")
    public ResponseEntity addFile(@RequestParam("file") MultipartFile file, @PathVariable(value = "catalogID") String toCatalogId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us = uSr.getUserByLogin(user.getUsername());

        try {
            FileDTO newFile = new FileDTO();
            newFile.setName(file.getOriginalFilename());
            newFile.setData(file.getBytes());
            fSr.addFileToCatalog(us, mSr.getCatalogByUuid(us, toCatalogId), newFile);
            return new ResponceAction(200,"success").respoce(serverLogger);
        } catch (IOException | DbxException | AccessDeniedException e) {

            return new ResponceAction(400,e.toString()).respoce(serverLogger);
        }

    }

    @GetMapping("/download/{fileID}")
    public FileDTO DownloadFile(String catalogId, @PathVariable(value = "fileID") String fileId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us = uSr.getUserByLogin(user.getUsername());
        try {
            return fSr.downloadFile(us, fSr.getFileByUuid(us, fileId));
        } catch (DbxException | IOException | AccessDeniedException e) {
            return null;
        }
    }

    @PostMapping("/delete/{fileID}")
    public ResponseEntity DeleteFile(String catalogId, @PathVariable(value = "fileID") String fileId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us = uSr.getUserByLogin(user.getUsername());
        try {
            fSr.deleteFile(us, fSr.getFileByUuid(us, fileId));
            return new ResponceAction(200,"success").respoce(serverLogger);

        } catch (DbxException | AccessDeniedException|IOException e) {
            return new ResponceAction(200,e.toString()).respoce(serverLogger);

        }
    }
    //REST API example
   /* @DeleteMapping("/delete")
    public ResponseEntity deleteFile(@RequestBody DropboxAction.Delete delete, BindingResult result) throws Exception {
        dropboxService.deleteFile(delete);

        DropboxAction.Response response = new DropboxAction.Response(200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/
}