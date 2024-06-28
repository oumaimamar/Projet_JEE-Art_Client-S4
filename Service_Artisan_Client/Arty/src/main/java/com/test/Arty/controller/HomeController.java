package com.test.Arty.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class HomeController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer != null && referrer.contains("login_Art")) {
            return "redirect:/dashboard_Art/index";
        } else {
            return "redirect:/main/index";
        }
    }


    @ResponseBody
    @RequestMapping(value = "/about",method = RequestMethod.GET)
    public String about(){
        return "about";
    }



    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public String roles(){
        return "/Roles";
    }


}
