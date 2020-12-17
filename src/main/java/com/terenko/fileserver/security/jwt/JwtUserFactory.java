package com.terenko.fileserver.security.jwt;

import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.Role;
import com.terenko.fileserver.util.Status;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(CustomUser user) {
        return new JwtUser(
                user.getUuid(),
                user.getLogin(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
