package com.terenko.fileserver.Controller;

import com.terenko.fileserver.Sevice.MainService;
import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.util.command.ResponceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
public class ManageCatalogController {
    @Autowired
    MainService mSr;
    @Autowired
    UserService uSR;
    @Autowired
    Logger serverLogger;
    @PostMapping("/addCtlg")
    public ResponseEntity addCatalog(@RequestParam String name,@RequestParam boolean access){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            mSr.addCatalog(uSR.getUserByLogin(user.getUsername()),name,access);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponceAction(400,e.toString()).respoce(serverLogger);
        }
        return new ResponceAction(200,"success").respoce(serverLogger);
    }
   /* @PostMapping("/delCtlg")
    public ResponseEntity deleteCatalog(@RequestParam String name,@RequestParam boolean access){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            mSr.deleteCtalog(uSR.getUserByLogin(user.getUsername()),name);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }*/


}
