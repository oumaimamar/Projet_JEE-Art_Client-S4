//package com.test.Arty.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "artisan_services")
//public class ArtisanService {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Integer id;
//
//        @ManyToOne
//        @JoinColumn(name = "artisan_id")
//        private Artisant artisan;
//
//        @ManyToOne
//        @JoinColumn(name = "service_id")
//        private Services service;
//
//        // Optional fields to store additional information about the association (e.g., price variation)
//        @Override
//        public String toString() {
//            return super.toString() +
//                    "artisan='" + artisan + '\'' +
//                    ", service='" + service  ;
//        }
//
//}
