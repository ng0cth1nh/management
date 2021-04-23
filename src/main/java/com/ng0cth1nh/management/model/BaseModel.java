package com.ng0cth1nh.management.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.GenerationType;
import java.io.Serializable;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public BaseModel() {

    }
    public BaseModel(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
