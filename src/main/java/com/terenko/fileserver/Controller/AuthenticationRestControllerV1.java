package com.terenko.fileserver.Controller;

import com.terenko.fileserver.DTO.AuthenticationRequestDto;
import com.terenko.fileserver.Exeption.AlreadyExistExeption;
import com.terenko.fileserver.Sevice.UserService;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.security.jwt.JwtTokenProvider;

import com.terenko.fileserver.util.command.ResponceAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping(value = "/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }
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
        } catch (AlreadyExistExeption| IOException e) {
            return new ResponceAction(400,e.toString()).respoce();
        }

        return new ResponceAction(200,"success").respoce();

    }
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        try {


            CustomUser user = userService.getUserByLogin(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, new ArrayList<>(user.getRoles()));
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
