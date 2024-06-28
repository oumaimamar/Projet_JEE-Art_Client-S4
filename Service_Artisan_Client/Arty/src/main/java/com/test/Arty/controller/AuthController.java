package com.test.Arty.controller;

import com.test.Arty.entities.Artisant;
import com.test.Arty.entities.Client;
import com.test.Arty.entities.User;
import com.test.Arty.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class AuthController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

// *************************************  -- logout --  ***********************************************

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        return "redirect:/login";
    }


    // *************************************  --Client login--  ***********************************************

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    // *************************************  --Client Register--  ***********************************************

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(HttpServletRequest request, HttpServletResponse response, Model model){
        Client client = new Client();
        model.addAttribute("client",client);
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String createNewUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("client")Client client){

        try {

            client.setRole("CLIENT");

            User newUser = userService.createUser(client);
            if(newUser == null){ return "redirect:/register?error"; }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(client.getEmail(),client.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,securityContext);

            return "redirect:/";

        } catch (Exception e){ return "redirect:/register?error"; }
    }


    // *************************************  --Artisans login--  ***********************************************

    @RequestMapping(value = "/login_Art",method = RequestMethod.GET)
    public String login_Art(){
        return "login_Art";
    }

    // ********************************************  --Artisans  Register--  ***********************************************

    @RequestMapping(value = "/register_Art",method = RequestMethod.GET)
    public String register_Art(HttpServletRequest request, HttpServletResponse response, Model model){
        Artisant artisant = new Artisant();
        model.addAttribute("artisant",artisant);
        return "/register_Art";
    }

    @RequestMapping(value = "/register_Art",method = RequestMethod.POST)
    public String createNewUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("artisant")Artisant artisant){

        try {

            artisant.setRole("ARTISANT");

            User newUser = userService.createUser(artisant);
            if(newUser == null){
                return "redirect:/register_Art?error";
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(artisant.getEmail(),artisant.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,securityContext);

            return "redirect:/";

        } catch (Exception e){
            return "redirect:/register_Art?error";
        }

    }

    }




