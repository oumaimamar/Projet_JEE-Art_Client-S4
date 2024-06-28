package com.test.Arty.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TEL")
    private String telephone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "VILLE")
    private String ville;

    @Column(name = "ROLE")
    private String role;

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName= userName;
    }
}

