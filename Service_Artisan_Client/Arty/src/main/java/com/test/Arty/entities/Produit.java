package com.test.Arty.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nameProd;
    private String nameOwner;
    private String secteurActivite;
    private double montantProd;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String Status;
    private Date dateProd;
    private String imageFileName;
}
