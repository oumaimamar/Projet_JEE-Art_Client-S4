//package com.test.Arty.services;
//
//import com.test.Arty.entities.Artisant;
//import com.test.Arty.entities.Services;
//import com.test.Arty.repositories.ArtisanRepository;
//import com.test.Arty.repositories.ServiceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//public interface ArtisanService {
//
//    @Service
//    public class ArtisantService {
//
//        @Autowired
//        private ArtisanRepository artisantRepository;
//
//        @Autowired
//        private ServiceRepository servicesRepository;
//
//        public Artisant getArtisantByEmail(String email) {
//            return artisantRepository.findByEmail(email);
//        }
//
//        public void addServiceToArtisant(Artisant artisant, Services service) {
//            artisant.addService(service);
//            artisantRepository.save(artisant);
//        }
//
//        public Services createService(Services service) {
//            return servicesRepository.save(service);
//        }
//    }
//
//}
//
//
