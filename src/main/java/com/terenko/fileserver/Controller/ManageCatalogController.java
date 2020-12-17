package com.terenko.fileserver.Controller;

import com.terenko.fileserver.DTO.CatalogDTO;
import com.terenko.fileserver.Sevice.MainCatalogService;
import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.security.jwt.JwtUser;
import com.terenko.fileserver.util.command.ResponceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ManageCatalogController {
    @Autowired
    MainCatalogService mSr;
    @Autowired
    UserService uSR;

    @PostMapping("/addCtlg")
    public ResponseEntity addCatalog(@RequestParam String name,@RequestParam boolean access){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us=uSR.getUserByLogin(user.getUsername());
        try {
            mSr.addCatalog(us,name,access);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponceAction(400,e.toString()).respoce();
        }
        return new ResponceAction(200,"success").respoce();
    }
    @PostMapping("/delCtlg/{catalogId}")
    public ResponseEntity deleteCatalog( @PathVariable(value = "catalogId") String uuid){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us=uSR.getUserByLogin(user.getUsername());
        try {
            mSr.deleteCatalog(us,mSr.getCatalogByUuid(us,uuid));
        } catch (IllegalArgumentException |IOException e) {
            return new ResponceAction(400,e.toString()).respoce();
        }
        return new ResponceAction(200,"success").respoce();
    }
@GetMapping("/getCtlg/{catalogId}")
public ResponseEntity getCatalog( @PathVariable(value = "catalogId") String uuid) {
    JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomUser us=uSR.getUserByLogin(user.getUsername());
    try {
        return new ResponceAction(200,"success", CatalogDTO.toDto(mSr.getCatalogByUuid(us,uuid)) ).respoce();

    } catch (IllegalArgumentException |IOException e) {
        return new ResponceAction(400,e.toString()).respoce();
    }

}
@PostMapping("/addAccess/{catalogId}")
    public ResponseEntity addAccessToCatalog(@PathVariable(value = "catalogId") String uuid,@RequestParam String addAccessTo){
    JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomUser us=uSR.getUserByLogin(user.getUsername());
    try {
        mSr.addAccessToUser(us,uSR.getUserByUuid(addAccessTo),mSr.getCatalogByUuid(us,uuid));

    } catch (IllegalArgumentException |IOException e) {
        return new ResponceAction(400,e.toString()).respoce();
    }
         return new ResponceAction(200,"success").respoce();

}
    @PostMapping("/removeAccess/{catalogId}")
    public ResponseEntity removeAccessfromCatalog(@PathVariable(value = "catalogId") String uuid,@RequestParam String removeAccessFrom){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser us=uSR.getUserByLogin(user.getUsername());
        try {
            mSr.removeAccess(us,uSR.getUserByUuid(removeAccessFrom),mSr.getCatalogByUuid(us,uuid));

        } catch (IllegalArgumentException |IOException e) {
            return new ResponceAction(400,e.toString()).respoce();
        }
        return new ResponceAction(200,"success").respoce();

    }
}
