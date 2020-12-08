package com.terenko.fileserver.DTO;

import com.terenko.fileserver.model.CustomUser;
import lombok.Data;

@Data
public class AccountDTO implements DTO{

    private final String login;
    private final String uuid;
    private final String pictureUrl;

    public AccountDTO(String name, String uuid, String pictureUrl) {
        this.login = name;
        this.uuid = uuid;
        this.pictureUrl = pictureUrl;
    }
    public static AccountDTO  toDto(CustomUser us){
       return new AccountDTO(us.getLogin(),us.getUuid(), us.getPicUrl());

    }

}
