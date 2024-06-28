package com.test.Arty.repositories;

import com.test.Arty.entities.Artisant;
import com.test.Arty.entities.Produit;
import com.test.Arty.entities.Services;
import com.test.Arty.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ServiceRepository extends JpaRepository<Services, Integer> {

    List<Services> findUserBySecteurActivite(String secteurActivite);

    Page<Services> findByNameServiceContainsAndArtisant(String nameService, Artisant artisan, PageRequest pageable);
    Page<Services> findAllBySecteurActiviteAndArtisant(String secteurActivite, Artisant artisan, PageRequest pageable);
    //*********************************************
    List<Services> findByNameServiceContains(String NameService);

//    @Query("SELECT s FROM Services s WHERE s.id = ")
//    User findUserByEmail(@Param("email") String email);


}
