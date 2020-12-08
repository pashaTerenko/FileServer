package com.terenko.fileserver.Controller;


import com.terenko.fileserver.Exeption.AlreadyExistExeption;
import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.util.command.ResponceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/userManage")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/newuser")
    public ResponseEntity update(@RequestParam String login,
                                 @RequestParam String password,
                                 Model model) {
        try {
            if (userService.existsByLogin(login)) {
                model.addAttribute("exists", true);
               throw new AlreadyExistExeption();
            }

            PasswordEncoder encoder= new BCryptPasswordEncoder();
            String passHash = encoder.encode(password);

            CustomUser dbUser = new CustomUser(login, passHash);
            userService.addUser(dbUser);
        } catch (AlreadyExistExeption|IOException e) {
            return new ResponceAction(400,e.toString()).respoce();
        }

        return new ResponceAction(200,"success").respoce();

    }
    public String unauthorized(Model model){
        return "unauthorized";
    }
}
