package com.example.rentmatch.Service;

import com.example.rentmatch.Entity.Property;
import com.example.rentmatch.Entity.User;
import com.example.rentmatch.Repository.PropertyRepository;
import com.example.rentmatch.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PropertyService {

   @Autowired
    private  PropertyRepository propertyRepository;
   @Autowired
   private UserRepository userRepository;

  //To Post All Properties

    public Property createProperty(String title ,
                                   String description,
                                   Double price,
                                   String location,
                                   String propertyType,
                                   Long ownerId,
                                   MultipartFile[] files) throws IOException{
        //fetch owner detail
        User owner=userRepository.findById(ownerId).orElseThrow(()->new RuntimeException("owner not Found Exception"));git

    }





}
