package com.test.Arty.controller;

import com.test.Arty.entities.Artisant;
import com.test.Arty.entities.Produit;
import com.test.Arty.entities.Services;
import com.test.Arty.repositories.ArtisanRepository;
import com.test.Arty.repositories.ProduitRepository;
import com.test.Arty.repositories.ServiceRepository;
import groovy.lang.GString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.List;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired ProduitRepository produitRepository;
    @Autowired
    private ArtisanRepository artisanRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/index" )
    public String showProduitList(Model model)
    {

        List<Produit> produitMobilier = produitRepository.findUserBySecteurActivite("Mobilier");
        model.addAttribute("produitMobilier",produitMobilier);

        List<Produit> produitDecoration = produitRepository.findUserBySecteurActivite("Decoration");
        model.addAttribute("produitDecoration",produitDecoration);

        List<Produit> produitMode = produitRepository.findUserBySecteurActivite("Mode");
        model.addAttribute("produitMode",produitMode);

        List<Produit> produitArt = produitRepository.findUserBySecteurActivite("Art");
        model.addAttribute("produitArt",produitArt);

        return "/main/index";


      //  return "/main/index";

    }

    @RequestMapping(value = "/nav",method = RequestMethod.GET)
    public String nav() { return "/main/nav"; }


    @RequestMapping(value = "/portfl_details",method = RequestMethod.GET)
    public String portfl_details(){
        return "/main/portfolio-details";
    }

    @RequestMapping(value = "/service-details",method = RequestMethod.GET)
    public String service_details(){
        return "/main/service-details";
    }

    @RequestMapping(value = "/starter-page",method = RequestMethod.GET)
    public String starter_page(){
        return "/main/starter-page";
    }

//********************************************************************************

    @RequestMapping(value = "/list-servece",method = RequestMethod.GET)
    public String list_servece(Model model , @RequestParam(name = "typeservice", defaultValue = "Art") String typeservice) {

            List<Artisant> artisans = artisanRepository.findAll();
            model.addAttribute("artisans", artisans);
            model.addAttribute("typeservice", typeservice);



        return "/main/list-servece";
    }

    @RequestMapping(value = "/service-details02",method = RequestMethod.GET)
    public String service_details02(){
        return "/main/service-details02";
    }

//    *******************************************************************************************
//    *******************************************************************************************

}
