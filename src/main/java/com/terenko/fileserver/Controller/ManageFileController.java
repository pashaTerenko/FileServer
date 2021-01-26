package com.terenko.fileserver.Controller;

import com.dropbox.core.DbxException;
import com.terenko.fileserver.DTO.FileDTO;
import com.terenko.fileserver.Sevice.FileService;
import com.terenko.fileserver.Sevice.MainCatalogService;
import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.security.jwt.JwtUser;
import com.terenko.fileserver.util.command.ResponceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ManageFileController {
    @Autowired
    MainCatalogService mSr;
    @Autowired
    UserService uSr;
    @Autowired
    FileService fSr;

    @PostMapping("/addFile/{catalogID}")
    public ResponseEntity addFile(@RequestParam("file") MultipartFile file, @PathVariable(value = "catalogID") String toCatalogId ,@RequestParam(required = false) boolean isEncrypt,@RequestParam(required = false) String key) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us = uSr.getUserByLogin(user.getUsername());

        try {
            FileDTO newFile = new FileDTO();
            newFile.setName(file.getOriginalFilename());
            newFile.setData(file.getBytes());
            if(isEncrypt&&key!=null)
                fSr.addFileToCatalogWithEncryption(us, mSr.getCatalogByUuid(us, toCatalogId), newFile,key);
            else
            fSr.addFileToCatalog(us, mSr.getCatalogByUuid(us, toCatalogId), newFile);
            return new ResponceAction(200,"success").respoce();
        } catch (IOException  | AccessDeniedException e) {

            return new ResponceAction(400,e.toString()).respoce();
        }

    }

    @GetMapping("/download/{CatalogID}/{fileID}")
    public  ResponseEntity DownloadFile( @PathVariable(value = "CatalogID") String catalogId, @PathVariable(value = "fileID") String fileId,@RequestParam(required = false) String key) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUser us = uSr.getUserByLogin(user.getUsername());
        try {
            if(key!=null)
                return  new ResponceAction(200,"success",fSr.downloadFileWithDecryption(us, fSr.getFileByUuid(us, fileId),key)).respoce();

            else
            return  new ResponceAction(200,"success",fSr.downloadFile(us, fSr.getFileByUuid(us, fileId))).respoce();

        } catch ( AccessDeniedException e) {
            return new ResponceAction(400,e.toString()).respoce();
        }
    }

    @PostMapping("/delete/{CatalogID}/{fileID}")
    public ResponseEntity DeleteFile(@PathVariable(value = "CatalogID") String catalogId, @PathVariable(value = "fileID") String fileId) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us = uSr.getUserByLogin(user.getUsername());
        try {
            fSr.deleteFile(us, fSr.getFileByUuid(us, fileId));
            return new ResponceAction(200,"success").respoce();

        } catch (  AccessDeniedException e) {
            return new ResponceAction(400,e.toString()).respoce();

        }
    }
    //REST API example
   /* @DeleteMapping("/delete")
    public ResponseEntity deleteFile(@RequestBody Action delete, BindingResult result) throws Exception {
        dropboxService.deleteFile(delete);

        DropboxAction.Response response = new DropboxAction.Response(200, "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/
}
