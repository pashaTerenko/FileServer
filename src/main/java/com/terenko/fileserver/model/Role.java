package com.terenko.fileserver.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple domain object that represents application user's role - ADMIN, USER, etc.

 */

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {

    @Column(name = "name" ,unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<CustomUser> users=new HashSet<>();

    public Role(String name) {
        super();
    this.name = name;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getUuid() + ", " +
                "name: " + name + "}";
    }
}
