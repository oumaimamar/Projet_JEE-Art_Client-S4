package com.test.Arty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Artisan")
public class ArtisanController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "/Artisan/index";
    }

    @RequestMapping(value = "/blog-single",method = RequestMethod.GET)
    public String blog_single(){
        return "/Artisan/blog-single";
    }



}
