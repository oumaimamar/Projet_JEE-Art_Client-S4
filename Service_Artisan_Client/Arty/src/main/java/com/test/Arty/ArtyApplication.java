package com.test.Arty;

import com.test.Arty.entities.*;
import com.test.Arty.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ArtyApplication implements CommandLineRunner {


	@Autowired UserRepository userRepository;
	@Autowired ArtisanRepository artisanRepository;
	@Autowired  ProduitRepository produitRepository;
	@Autowired ServiceRepository serviceRepository;


	public static void main(String[] args) {
		SpringApplication.run(ArtyApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

// ************************************ Verifier Select Product By Category *********************
//		List<Produit> produitMobilier = produitRepository.findUserBySecteurActivite("Mobilier");
//		produitMobilier.forEach(System.out::println);
// ********************************	     End      ***********************************************


//produitRepository.save(new Produit(null,"Sumy","Mar","Plombier",200,"xxxxxxx aaaaaaa zzzzzzzzz","Limiter",new Date(),"A1.jpg"));



//				List<User> user_Art = userRepository.findUserByRole("ARTISANT");
//		        user_Art.forEach(System.out::println)	;


// ************************************ Verifier Select All Artisan User *********************
//		List<User> users = userRepository.findAll();
//		List<Artisant> artisants = artisanRepository.findAll();
//		artisants.forEach(System.out::println);
// ********************************	     End      **********************************************



// ************************************ Verifier Get Data From Artisan User *********************
//		Artisant artisant = artisanRepository.findArtisantByEmail("Mery@gmail.com");
//		if(artisant == null) { System.out.println("l'objet est null"); }
//		else System.out.println(artisant.getUserName());
// ********************************	     End      ***********************************************


// ************************************ Verifier Select Product By Category *********************
// ********************************	     End      ***********************************************

	}
}
