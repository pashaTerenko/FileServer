package com.terenko.fileserver.model;

import com.terenko.fileserver.util.Status;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Base class with property 'id'.
 * Used as a base class for all objects that requires this property.
        */

@MappedSuperclass
@Data
public class BaseEntity {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String uuid;

    @CreatedDate
    @Column(name = "created")
    protected Date created;

    @LastModifiedDate
    @Column(name = "updated")
    protected Date updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    protected Status status;

    public BaseEntity() {
        setCreated(new Date());
        setUpdated(new Date());
    }
}
