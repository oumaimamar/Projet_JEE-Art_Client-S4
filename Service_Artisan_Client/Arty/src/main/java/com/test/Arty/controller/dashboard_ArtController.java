package com.test.Arty.controller;


import com.test.Arty.entities.Artisant;
import com.test.Arty.entities.ServiceDto;
import com.test.Arty.entities.Services;
import com.test.Arty.entities.User;
import com.test.Arty.repositories.ArtisanRepository;
import com.test.Arty.repositories.ServiceRepository;
import com.test.Arty.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dashboard_Art")
public class dashboard_ArtController {


    @Autowired ServiceRepository serviceRepository;
    @Autowired UserRepository userRepository;
    @Autowired ArtisanRepository artisanRepository;


    @ModelAttribute("allSecteurActivites") // Add all secteurs to the model
    public List<String> getAllSecteurActivites() {
        // Implement logic to retrieve all distinct secteurActivite values
        return Arrays.asList("Electricien","Plombier","Depannage","Coiffeur" ,"Soudeur" , "Jardinier", "Mecanicien"); // Replace with your logic
    }

    @GetMapping({"/my_Service"})
    public String showServiceList(Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "3") int size,
                                  @RequestParam(name="search", defaultValue = "") String searchName,
                                  @RequestParam(name = "secteurActivite", defaultValue = "") String secteurActivite)
    {


        //********************  Methode 3 Connected Name  *************************************************
        // Get the currently authenticated user email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Username is usually the email

        //  Find the user by email and retrieve full name
        Artisant artisant = artisanRepository.findArtisantByEmail(email);

        if(artisant == null) { System.out.println("l'objet est null"); }
        else {




            Page<Services> servicesPage;

            if (secteurActivite.isEmpty()) {
                servicesPage = serviceRepository.findByNameServiceContainsAndArtisant(searchName, artisant, PageRequest.of(page, size));
            } else {
                servicesPage = serviceRepository.findAllBySecteurActiviteAndArtisant(secteurActivite, artisant, PageRequest.of(page, size));
            }
            int[] pages = new int[servicesPage.getTotalPages()];
            for (int i = 0; i < pages.length; i++)
                pages[i] = i;

            model.addAttribute("servicesPage",servicesPage);
            model.addAttribute("tabPages",pages);
            model.addAttribute("size",size);
            model.addAttribute("currentPage", page);
            model.addAttribute("searchName", searchName);
            model.addAttribute("secteurActivite", secteurActivite);





            String fullName = artisant.getUserName(); // Assuming userName stores the full name
            String artisanService = artisant.getArtisanService();

            model.addAttribute("fullName", fullName);
            model.addAttribute("artisanService", artisanService);
        }
//******************** End Methode 3 Connected Name   *************************************************

        return "/dashboard_Art/my_Service";

    }

// ----------------------------------------------------------------------------------------------
    @GetMapping("/new_Service")
    public  String showCreatePage(Model model){
        ServiceDto serviceDto =new ServiceDto();
        model.addAttribute("serviceDto",serviceDto);

        return "/dashboard_Art/new_Service";
    }

// ----------------------------------------------------------------------------------------------
    @PostMapping("/create")
    public String createService(
            @Valid @ModelAttribute ServiceDto serviceDto,
            BindingResult result ){


        //********************  Methode 3 Connected Name  *************************************************
        // Get the currently authenticated user email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Username is usually the email

        //  Find the user by email and retrieve full name
        Artisant artisant = artisanRepository.findArtisantByEmail(email);

        if(artisant == null) { System.out.println("l'objet est null"); }
        else {
            //Image Required
            if (serviceDto.getImageFile().isEmpty()){
                result.addError(new FieldError("serviceDto","imageFile","The image file is required"));
            }
            if (result.hasErrors()){
                return "/dashboard_Art/new_Service";
            }

            //Save the image file
            MultipartFile image= serviceDto.getImageFile();
            Date dateService = new Date();
            String storageFileName = dateService.getTime()+ ".." + image.getOriginalFilename();

            try{
                String uploadDir = "public/images_Service/";
                Path uploadPath= Paths.get(uploadDir);

                if (!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

            }catch (Exception ex){
                System.out.println("Exception" + ex.getMessage());
            }


            //Save Service in Database
            Services services = new Services();

            services.setNameService(serviceDto.getNameService());
            services.setSecteurActivite(serviceDto.getSecteurActivite());
            services.setMontantService(serviceDto.getMontantService());
            services.setDescriptionMertier(serviceDto.getDescriptionMertier());
            services.setDescriptionService(serviceDto.getDescriptionService());
            services.setStatus(serviceDto.getStatus());
            services.setDateService(dateService);
            services.setImageFileName(storageFileName);
            services.setArtisant(artisant);

            serviceRepository.save(services);

        }
//******************** End Methode 3 Connected Name   *************************************************


        return "redirect:/dashboard_Art/my_Service";
    }


// ***************************************  Edit ************************************************
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id){

        try{
            Services services = serviceRepository.findById(id).get();
            model.addAttribute("services",services);

            ServiceDto serviceDto = new ServiceDto();
            serviceDto.setNameService(services.getNameService());
            serviceDto.setSecteurActivite(services.getSecteurActivite());
            serviceDto.setMontantService(services.getMontantService());
            serviceDto.setDescriptionMertier(services.getDescriptionMertier());
            serviceDto.setDescriptionService(services.getDescriptionService());
            serviceDto.setStatus(services.getStatus());

            model.addAttribute("serviceDto",serviceDto);

        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/dashboard_Art/my_Service";
        }

        return "/dashboard_Art/Edite_Services";
    }

    @PostMapping("/edit")
    public String updateServive(Model model,
                                @RequestParam int id,
                                @Valid @ModelAttribute ServiceDto serviceDto,
                                BindingResult result){

        try{
            Services services = serviceRepository.findById(id).get();
            model.addAttribute("services",services);

            if (result.hasErrors()){
                return  "/dashboard_Art/Edite_Services";
            }

            if (!serviceDto.getImageFile().isEmpty()){
                //delete old image
                String uploadDir ="public/images_Service/";
                Path oldImagePath= Paths.get(uploadDir + services.getImageFileName());

                try{
                    Files.delete(oldImagePath);
                }catch (Exception ex){
                    System.out.println("Exception: " + ex.getMessage());
                }

                //save new image File
                MultipartFile image= serviceDto.getImageFile();
                Date dateService = new Date();
                String storageFileName = dateService.getTime()+ ".." + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                services.setImageFileName(storageFileName);
            }

            //Update the other Attribute
            services.setNameService(serviceDto.getNameService());
            services.setSecteurActivite(serviceDto.getSecteurActivite());
            services.setMontantService(serviceDto.getMontantService());
            services.setDescriptionMertier(serviceDto.getDescriptionMertier());
            services.setDescriptionService(serviceDto.getDescriptionService());
            services.setStatus(serviceDto.getStatus());

            serviceRepository.save(services);
        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }


        return "redirect:/dashboard_Art/my_Service";
    }

// ************************************** END  Edit ********************************************
// ----------------------------------------------------------------------------------------------


// ************************************** DELETE *************************************************
    @GetMapping("/delete")
    public  String deleteservices(@RequestParam int id){

        try{
            Services services = serviceRepository.findById(id).get();

            //delete services image
            Path imagePath = Paths.get("public/images_Service/" + services.getImageFileName());

            try{
                Files.delete(imagePath);

            }catch (Exception ex){
                System.out.println("Exception: " + ex.getMessage());}

            //delete the services
            serviceRepository.delete(services);


        }catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/dashboard_Art/my_Service";
    }
// ************************************** END  DELETE ********************************************
// ----------------------------------------------------------------------------------------------


// ***************************************  Index ************************************************
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model){

//********************  Methode 3 Connected Name  ******************************************
        // Get the currently authenticated user email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Username is usually the email

        //  Find the user by email and retrieve full name
        Artisant artisant = artisanRepository.findArtisantByEmail(email);

        if(artisant == null) { System.out.println("l'objet est null"); }
        else {
            String fullName = artisant.getUserName(); // Assuming userName stores the full name
            String artisanService = artisant.getArtisanService();

            model.addAttribute("fullName", fullName);
            model.addAttribute("artisanService", artisanService);
        }
//******************** End Methode 3 Connected Name   ****************************************

        return "/dashboard_Art/index";
    }
// ***************************************  End Index ************************************************
// ----------------------------------------------------------------------------------------------




//    @RequestMapping(value = "/my_Service",method = RequestMethod.GET)
//    public String my_Service(){
//        return "/dashboard_Art/my_Service";
//    }

//    @RequestMapping(value = "/new_Service",method = RequestMethod.GET)
//    public String new_Service(){
//        return "/dashboard_Art/new_Service";
//    }




// ***************************************  NAV ************************************************
    @RequestMapping(value = "/nav",method = RequestMethod.GET)
    public String nav(Model model){

////******************** Methode 1  *************************************************
//        // Get the currently authenticated user email
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName(); // Username is usually the email
//
//       //  Find the user by email and retrieve full name
//        User user = userRepository.findUserByEmail(email);
//        String fullName = user.getUserName(); // Assuming userName stores the full name
//
//        model.addAttribute("fullName", fullName);
//
////******************** A verifier  *************************************************
//
//       Artisant artisant = artisanRepository.findArtisantByEmail(email);
//        if(artisant == null) {
//            System.out.println("l'objet est null");
//
//        }
//        else {
////            System.out.println(artisant);
////            model.addAttribute("artisant", artisant);
//
//            String artisanService = artisant.getArtisanService();
//            model.addAttribute("artisanService", artisanService);
//        }
////******************** End Methode 1  *************************************************

//********************  Methode 2  *************************************************
//        // Get the currently authenticated user email
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName(); // Username is usually the email
//
//        // Find the user by email and retrieve full name
//        Artisant user = artisanRepository.findArtisantByEmail(email);
//        String fullName = user.getUserName(); // Assuming userName stores the full name
//
//        // If the user is an Artisan, retrieve the artisanService
//        String artisanService = "";
//        if(user == null) {System.out.println("l'objet est null");}
//        else
//            artisanService = user.getArtisanService();

//        model.addAttribute("fullName", fullName);
//        model.addAttribute("artisanService", artisanService);
//******************** End Methode 2  *************************************************

//********************  Methode 3 Connected Name  *************************************************
        // Get the currently authenticated user email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Username is usually the email

        //  Find the user by email and retrieve full name
        Artisant artisant = artisanRepository.findArtisantByEmail(email);

        if(artisant == null) { System.out.println("l'objet est null"); }
        else {
            String fullName = artisant.getUserName(); // Assuming userName stores the full name
            String artisanService = artisant.getArtisanService();

            model.addAttribute("fullName", fullName);
            model.addAttribute("artisanService", artisanService);
        }
//******************** End Methode 3 Connected Name   *************************************************
        return "/dashboard_Art/nav";
    }
// ***************************************  End NAV ************************************************
// ----------------------------------------------------------------------------------------------


    @RequestMapping(value = "/users-profile",method = RequestMethod.GET)
    public String users_profile(){
        return "/dashboard_Art/users-profile";
    }


}
