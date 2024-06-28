package com.test.Arty.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Client") // optional: specify the table name explicitly
@PrimaryKeyJoinColumn(name = "user_id")
public class Client extends User {

    private String clientField;

    // Constructors, getters, and setters


    @Override
    public String toString() {
        return super.toString() + "Client{" + "clientField=" + clientField + '}';

    }
}