package com.terenko.fileserver.Sevice;

import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.util.command.DBAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
@Autowired
    UserRepository userRepository;

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
        new DBAction(customUser).setRepository(userRepository).execute();
    }
}
