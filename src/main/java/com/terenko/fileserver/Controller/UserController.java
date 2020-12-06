package com.terenko.fileserver.Controller;



import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController("/userManage")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/newuser")
    public String update(@RequestParam String login,
                         @RequestParam String password,

                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        PasswordEncoder encoder= new BCryptPasswordEncoder();
        String passHash = encoder.encode(password);

        CustomUser dbUser = new CustomUser(login, passHash);
        userService.addUser(dbUser);

        return "redirect:/";
    }
    public String unauthorized(Model model){
        return "unauthorized";
    }
}
