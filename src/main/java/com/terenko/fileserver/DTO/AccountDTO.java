package com.terenko.fileserver.DTO;

public class AccountDTO {

    private final String name;
    private final String pictureUrl;

    private AccountDTO(String name, String pictureUrl) {

        this.name = name;
        this.pictureUrl = pictureUrl;
    }

  /*  public static AccountDTO of(String email, String name, String pictureUrl) {
        return new AccountDTO( name,);
    }*/


    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
