//package com.test.Arty.services;
//
//import com.test.Arty.entities.Artisant;
//import com.test.Arty.entities.Services;
//import com.test.Arty.repositories.ArtisanRepository;
//import com.test.Arty.repositories.ServiceRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//public class ArtisanServiceImpl implements ArtisanService {
//    @Autowired
//    ArtisanRepository artisanRepository;
//    ServiceRepository serviceRepository;
//
//    @Override
//    public Artisant addNewArtisan(Artisant artisant) {
//        return null;
//    }
//
//    @Override
//    public Services addNewService(Services service) {
//        return serviceRepository.save(service);
//    }
//
//    @Override
//    public Artisant findArtisanByUsername(String username) {
//        return null;
//    }
//
//    @Override
//    public Services findServiceByName(String serviceName) {
//        return serviceRepository.findServiceByName(serviceName);
//    }
//
//    @Override
//    public void addServiceToArtisan(String username, String serviceName) {
//
//        Artisant artisant = findArtisanByUsername(username);
//        Services services = findServiceByName(serviceName);
//        if(artisant != null) {
//
//            artisant.getServices().add(services);
//          //  artisanRepository.save(artisant);
//
//}
//
//    }
//}
