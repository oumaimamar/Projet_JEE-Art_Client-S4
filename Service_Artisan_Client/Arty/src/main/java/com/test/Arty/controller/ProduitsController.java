package com.test.Arty.controller;

import com.test.Arty.entities.Produit;
import com.test.Arty.entities.ProduitDto;
import com.test.Arty.repositories.ProduitRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/produits")
public class ProduitsController {

    @Autowired ProduitRepository produitRepository;


    @ModelAttribute("allSecteurActivites") // Add all secteurs to the model
    public List<String> getAllSecteurActivites() {
        // Implement logic to retrieve all distinct secteurActivite values
        // (e.g., from a database table or configuration file)
        return Arrays.asList("Mobilier","Décoration","Mode","Art"); // Replace with your logic
    }

    @GetMapping({" "})
    public String showProduitList(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "3") int size,
                                 @RequestParam(name="search", defaultValue = "") String searchName,
                                 @RequestParam(name = "secteurActivite", defaultValue = "") String secteurActivite)
    {


        Page<Produit> produitPage;

        if (secteurActivite.isEmpty()) {
            produitPage=produitRepository.findByNameProdContains(searchName , PageRequest.of(page,size));
        } else {
            produitPage = produitRepository.findAllBySecteurActivite(secteurActivite, PageRequest.of(page, size));
        }
        int[] pages = new int[produitPage.getTotalPages()];
        for (int i = 0; i < pages.length; i++)
            pages[i] = i;

        model.addAttribute("produitPage",produitPage);
        model.addAttribute("tabPages",pages);
        model.addAttribute("size",size);
        model.addAttribute("currentPage", page);
        model.addAttribute("searchName", searchName);
        model.addAttribute("secteurActivite", secteurActivite);


        return "produits/ProduitList";

    }


    @GetMapping("/create")
    public  String showCreatePage(Model model){
        ProduitDto produitDto =new ProduitDto();
        model.addAttribute("produitDto",produitDto);
        return "produits/CreateProduit";
    }

    @PostMapping("/create")
    public String createProduit(
            @Valid @ModelAttribute ProduitDto produitDto,
            BindingResult result ){

        //Image Required
        if (produitDto.getImageFile().isEmpty()){
            result.addError(new FieldError("produitDto","imageFile","The image file is required"));
        }
        if (result.hasErrors()){
            return "produits/CreateProduit";
        }

        //Save the image file
        MultipartFile image= produitDto.getImageFile();
        Date dateProd = new Date();
        String storageFileName = dateProd.getTime()+ ".." + image.getOriginalFilename();

        try{
            String uploadDir = "public/images/";
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


        //Save Produit in Database

        Produit produit = new Produit();
        produit.setNameProd(produitDto.getNameProd());
        produit.setNameOwner(produitDto.getNameOwner());
        produit.setSecteurActivite(produitDto.getSecteurActivite());
        produit.setMontantProd(produitDto.getMontantProd());
        produit.setDescription(produitDto.getDescription());
        produit.setStatus(produitDto.getStatus());
        produit.setDateProd(dateProd);
        produit.setImageFileName(storageFileName);

        produitRepository.save(produit);

        return "redirect:/produits";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id){

        try{
            Produit produit = produitRepository.findById(id).get();
            model.addAttribute("produit",produit);


            ProduitDto produitDto = new ProduitDto();
            produitDto.setNameProd(produit.getNameProd());
            produitDto.setNameOwner(produit.getNameOwner());
            produitDto.setSecteurActivite(produit.getSecteurActivite());
            produitDto.setMontantProd(produit.getMontantProd());
            produitDto.setDescription(produit.getDescription());
            produitDto.setStatus(produit.getStatus());

            model.addAttribute("produitDto",produitDto);

        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/produits";
        }

        return "/produits/EditProduit";
    }


    @PostMapping("/edit")
    public String updateProduit(Model model,
                                @RequestParam int id,
                                @Valid @ModelAttribute ProduitDto produitDto,
                                BindingResult result){

        try{
            Produit produit = produitRepository.findById(id).get();
            model.addAttribute("produit",produit);

            if (result.hasErrors()){
                return  "/produits/EditProduit";
            }

            if (!produitDto.getImageFile().isEmpty()){
                //delete old image
                String uploadDir ="public/images/";
                Path oldImagePath= Paths.get(uploadDir + produit.getImageFileName());

                try{
                    Files.delete(oldImagePath);
                }catch (Exception ex){
                    System.out.println("Exception: " + ex.getMessage());
                }

                //save new image File
                MultipartFile image= produitDto.getImageFile();
                Date dateProd = new Date();
                String storageFileName = dateProd.getTime()+ ".." + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                produit.setImageFileName(storageFileName);
            }

            //Update the other Attribute
            produit.setNameProd(produitDto.getNameProd());
            produit.setNameOwner(produitDto.getNameOwner());
            produit.setSecteurActivite(produitDto.getSecteurActivite());
            produit.setMontantProd(produitDto.getMontantProd());
            produit.setDescription(produitDto.getDescription());
            produit.setStatus(produitDto.getStatus());

            produitRepository.save(produit);
        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/produits";
    }

    @GetMapping("/delete")
    public  String deleteProduit(@RequestParam int id){

        try{
            Produit produit = produitRepository.findById(id).get();

            //delete produit image
            Path imagePath = Paths.get("public/images/" + produit.getImageFileName());

            try{
                Files.delete(imagePath);

            }catch (Exception ex){
                System.out.println("Exception: " + ex.getMessage());}

            //delete the produit
            produitRepository.delete(produit);


        }catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/produits";
    }
}
