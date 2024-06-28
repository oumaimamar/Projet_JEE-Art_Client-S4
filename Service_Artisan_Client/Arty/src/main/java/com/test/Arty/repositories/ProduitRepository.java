package com.test.Arty.repositories;

import com.test.Arty.entities.Produit;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    //  @Query("SELECT p FROM Produit p WHERE p.secteurActivite = :secteur ")
    // List<Produit>  findUserBySecteurActivite(@Param("secteur") String secteurActivite);


    List<Produit> findUserBySecteurActivite(String secteurActivite);


    Page<Produit> findByNameProdContains (String owner, PageRequest pageable);

    // New method for searching by secteurActivite
    Page<Produit> findAllBySecteurActivite(String secteurActivite, PageRequest pageable);

}
