package com.maciejbihun.models;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Basic(optional = false)
    @Column(name = "NAME", updatable = true, length = 30)
    private String name;

    @Basic(optional = false)
    @Column(name = "SURNAME", updatable = true, length = 30)
    private String surname;

    @Basic(optional = false)
    @Column(name = "LOGIN", updatable = true, length = 30)
    private String login;

    @Basic(optional = false)
    @Column(name = "PASSWORD", updatable = true, length = 60 /* length 60 for BCrypt */)
    private String password;

}
