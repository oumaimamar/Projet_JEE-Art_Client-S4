//package com.test.Arty.controller;
//
//import com.test.Arty.entities.Artisant;
//import com.test.Arty.entities.Services;
//import com.test.Arty.repositories.ArtisanRepository;
//import com.test.Arty.repositories.ServiceRepository;
//import com.test.Arty.services.ArtisanService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/Artisan")
//public class ArtisanServiceController {
//
//
//    @Autowired
//    private ArtisanService.ArtisantService artisantService;
//
//    @PostMapping("/services")
//    public Services createService(@RequestBody Services service) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Artisant artisant = artisantService.getArtisantByEmail(email);
//
//        Services createdService = artisantService.createService(service);
//        artisantService.addServiceToArtisant(artisant, createdService);
//
//        return createdService;
//    }
//
//    @GetMapping("/services")
//    public Set<Services> getArtisantServices() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        Artisant artisant = artisantService.getArtisantByEmail(email);
//
//        return artisant.getServices();
//    }
//
//}
