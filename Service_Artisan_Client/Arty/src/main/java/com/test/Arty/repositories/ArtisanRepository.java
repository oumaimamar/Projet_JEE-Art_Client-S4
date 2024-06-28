package com.test.Arty.repositories;

import com.test.Arty.entities.Artisant;
import com.test.Arty.entities.Produit;
import com.test.Arty.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtisanRepository extends JpaRepository<Artisant, Long> {

    @Override
    List<Artisant> findAll();

    Artisant findByEmail(String email);


    @Query("SELECT a FROM Artisant a WHERE a.email = :email")
    Artisant findArtisantByEmail(@Param("email") String email);


    //**************************************************************
    List<Artisant>findByUserNameContaining(String name);

}
