package com.terenko.fileserver.model;

import com.terenko.fileserver.DTO.AccountDTO;
import com.terenko.fileserver.util.Status;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


@Entity
@Data

public class CustomUser  extends BaseEntity {
@Column(unique =true)
    private String login;
    private String password;
    private String picUrl;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creator")
    private Set<Catalog> catalogs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "uuid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "uuid")})
    private Set<Role> roles=new HashSet<>();
    public CustomUser(String login, String password) {


        this.login = login;
        this.password = password;
        super.setStatus(Status.NOT_ACTIVE);


    }

    public CustomUser() {
    }

    public static CustomUser fromDTO(AccountDTO accountDTO) {
        return new CustomUser(accountDTO.getLogin(), accountDTO.getPictureUrl());
    }


    public void addCatalog(Catalog cat) throws IllegalArgumentException {
        if (catalogs.stream().anyMatch(x -> x.getName().equals(cat.getName())))
            throw new IllegalArgumentException("name invalid");
        catalogs.add(cat);
    }

    public void deleteCatalog(String uuid) {
        Catalog pl = null;
        for (Catalog p : catalogs) {
            if (p.getUuid().equals(uuid)) {
                pl = p;
            }
        }
        catalogs.remove(pl);
    }
@Override
    public String toString(){
        return new StringBuilder().append(this.login).append(" ").append(this.getUuid()).toString();
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUser that = (CustomUser) o;
        return uuid.equals(that.uuid) &&
                login.equals(that.login) &&
                password.equals(that.password) &&
                Objects.equals(picUrl, that.picUrl) &&
                Objects.equals(catalogs, that.catalogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, login);
    }
    public  void addRole(Role role){
        role.getUsers().add(this);
        roles.add(role);

    }
}
