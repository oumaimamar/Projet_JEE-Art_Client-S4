package com.test.Arty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dashboard_Admin")
public class dashboard_AdminController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "/dashboard_Admin/index";
    }

    @RequestMapping(value = "/Edite_Produit",method = RequestMethod.GET)
    public String Edite_Produit(){
        return "/dashboard_Admin/Edite_Produit";
    }

    @RequestMapping(value = "/List_Produit",method = RequestMethod.GET)
    public String List_Produit(){
        return "/dashboard_Admin/List_Produit";
    }


//    @RequestMapping(value = "/new_Produit",method = RequestMethod.GET)
//    public String new_Produit(){
//        return "/dashboard_Admin/new_Produit";
//    }

}
