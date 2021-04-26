package com.ng0cth1nh.management.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_company")
public class Company extends BaseModel{

    private String name;

    private Boolean active;

    @OneToMany(cascade = {CascadeType.REMOVE},fetch = FetchType.EAGER,mappedBy = "companyId")
    private Set<User> users = new HashSet<>();

    public Company() {
    }
    public Company(String name, Boolean active, Set<User> users) {
        this.name = name;
        this.active = active;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
