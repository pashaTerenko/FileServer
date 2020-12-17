package com.terenko.fileserver.Sevice;

import com.terenko.fileserver.Repository.RoleRepository;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.Role;
import com.terenko.fileserver.util.Status;
import com.terenko.fileserver.util.command.DBAction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
final
UserRepository userRepository;
final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
        if(roleRepository.findByName("USER")==null)
        roleRepository.save(new Role("USER"));
    }


    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    public CustomUser getUserByUuid(String uuid){
        return userRepository.findByUuid(uuid);
    }
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void addUser(CustomUser customUser) throws IOException {
         Role roleUser = roleRepository.findByName("USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);

        customUser.setRoles(userRoles);
        customUser.setStatus(Status.ACTIVE);

        new DBAction(customUser).setRepository(userRepository).execute();
    }

}
