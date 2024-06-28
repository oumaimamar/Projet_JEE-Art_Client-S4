package com.test.Arty.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Artisan") // optional: specify the table name explicitly
@PrimaryKeyJoinColumn(name = "user_id")
public class Artisant extends User {

    @Column(name = "ARTISAN_SERVICE")
    private String artisanService;

    @Column(name = "DESCRIPTION")
    private String description;



    @OneToMany(mappedBy = "artisant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Services> services;

    @Override
    public String toString() {
        return super.toString() +
                "artisanService='" + artisanService + '\'' +
                ", description='" + description + '\'' +
                ", services=" + services;
    }
}
