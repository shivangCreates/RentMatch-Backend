package com.example.rentmatch.Controller;

import com.example.rentmatch.Entity.Property;
import com.example.rentmatch.Service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // CREATE PROPERTY
    @PostMapping("/create")
    public ResponseEntity<?> createProperty(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String location,
            @RequestParam String propertyType,
            @RequestParam MultipartFile[] files,
            java.security.Principal principal
    ) throws IOException {

        String email = principal.getName();

        Property property = propertyService.createProperty(
                title, description, price, location, propertyType, email, files
        );

        return ResponseEntity.ok(property);
    }


    // GET ALL (PUBLIC API)
    @GetMapping
    public ResponseEntity<?> getAllProperties() {

        return ResponseEntity.ok(propertyService.getAllProperties());
    }


    // GET SINGLE (PUBLIC API)
    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@PathVariable Long id) {

        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }


    // UPDATE PROPERTY (SECURED)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String location,
            @RequestParam String propertyType,
            @RequestParam(required = false) MultipartFile[] files,
            java.security.Principal principal
    ) throws IOException {

        String email = principal.getName();

        Property property = propertyService.updateProperty(
                id, title, description, price, location, propertyType, email, files
        );

        return ResponseEntity.ok(property);
    }


    // DELETE PROPERTY (SECURED)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(
            @PathVariable Long id,
            java.security.Principal principal
    ) {

        String email = principal.getName();

        return ResponseEntity.ok(
                propertyService.deleteProperty(id, email)
        );
    }
}