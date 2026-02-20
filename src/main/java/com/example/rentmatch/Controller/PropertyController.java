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

    // CREATE PROPERTY API
    @PostMapping("/create")
    public ResponseEntity<?> UploadPost(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String location,
            @RequestParam String propertyType,
            @RequestParam MultipartFile[] files,
            java.security.Principal principal
    ) {

        try {

            String email = principal.getName();

            Property property = propertyService.createProperty(
                    title,
                    description,
                    price,
                    location,
                    propertyType,
                    email,
                    files
            );

            return ResponseEntity.ok(property);

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllProperties() {

        List<Property> properties = propertyService.getAllProperties();

        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@PathVariable Long id) {

        Property property = propertyService.getPropertyById(id);

        return ResponseEntity.ok(property);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String location,
            @RequestParam String propertyType,
            @RequestParam(required = false) MultipartFile[] files
    ) throws IOException {

        Property updated = propertyService.updateProperty(
                id, title, description, price, location, propertyType, files
        );

        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {

        String message = propertyService.deleteProperty(id);

        return ResponseEntity.ok(message);
    }
}