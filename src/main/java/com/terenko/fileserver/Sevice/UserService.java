package com.terenko.fileserver.Sevice;

import com.terenko.fileserver.DTO.AccountDTO;
import com.terenko.fileserver.Repository.UserRepository;
import com.terenko.fileserver.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
@Autowired
    UserRepository userRepository;

    public CustomUser getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void addUser(CustomUser customUser) {
        userRepository.save(customUser);
    }
}
