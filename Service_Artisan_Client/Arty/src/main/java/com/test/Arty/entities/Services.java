package com.test.Arty.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nameService;
    private String secteurActivite;
    private double montantService;

    @Column(columnDefinition = "TEXT")
    private String descriptionMertier;

    @Column(columnDefinition = "TEXT")
    private String descriptionService;

    private String Status;
    private Date dateService;
    private String imageFileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Artisant artisant;

}


