package com.jars.itodolist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;


@Entity
@Table(name = "sec_user_entity")
public class SecUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email",  nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "session")
    private String session;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}