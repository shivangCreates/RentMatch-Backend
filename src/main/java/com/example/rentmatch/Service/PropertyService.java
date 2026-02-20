        package com.example.rentmatch.Service;

        import com.example.rentmatch.Entity.Property;
        import com.example.rentmatch.Entity.PropertyMedia;
        import com.example.rentmatch.Entity.User;
        import com.example.rentmatch.Repository.PropertyRepository;
        import com.example.rentmatch.Repository.UserRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        @Service
        public class PropertyService {

           @Autowired
            private  PropertyRepository propertyRepository;

           @Autowired
           private UserRepository userRepository;

            private final String uploadDir = "C:/rentmatch/uploads/";

          //To Post All Properties

            public Property createProperty(String title,
                                           String description,
                                           Double price,
                                           String location,
                                           String propertyType,
                                           String email,
                                           MultipartFile[] files) throws IOException {

                // Step 1: Fetch owner from JWT email
                User owner = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                // Step 2: Create Property object
                Property property = new Property();

                property.setTitle(title);
                property.setDescription(description);
                property.setPrice(price);
                property.setLocation(location);
                property.setPropertyType(propertyType);
                property.setOwner(owner);

                // Step 3: Save property first
                Property savedProperty = propertyRepository.save(property);

                // Step 4: Process files
                List<PropertyMedia> mediaList = new java.util.ArrayList<>();

                for (MultipartFile file : files) {

                    if (!file.isEmpty()) { // FIXED CONDITION

                        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        String filepath = uploadDir + filename;

                        // Save file to folder
                        java.io.File dest = new java.io.File(filepath);
                        dest.getParentFile().mkdirs();
                        file.transferTo(dest);

                        // Create media object
                        PropertyMedia media = new PropertyMedia();

                        media.setFilePath(filepath);
                        media.setFileType(file.getContentType());
                        media.setProperty(savedProperty);

                        mediaList.add(media);
                    }
                }

                //  Link media to property
                savedProperty.setMediaList(mediaList);

                //  Save again (cascade saves media)
                return propertyRepository.save(savedProperty);
            }

            public List<Property> getAllProperties() {
                return propertyRepository.findAll();
            }
            public Property getPropertyById(Long id) {

                return propertyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Property not found"));
            }


            public Property updateProperty(Long id,
                                           String title,
                                           String description,
                                           Double price,
                                           String location,
                                           String propertyType,
                                           MultipartFile[] files) throws IOException {

                Property property = propertyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Property not found"));

                // update basic fields
                property.setTitle(title);
                property.setDescription(description);
                property.setPrice(price);
                property.setLocation(location);
                property.setPropertyType(propertyType);

                // update media if new files provided
                if (files != null && files.length > 0) {

                    // clear old media
                    property.getMediaList().clear();

                    List<PropertyMedia> mediaList = new ArrayList<>();

                    for (MultipartFile file : files) {

                        if (!file.isEmpty()) {

                            String filename = System.currentTimeMillis()
                                    + "_" + file.getOriginalFilename();

                            String filePath = uploadDir + filename;

                            File dest = new File(filePath);
                            dest.getParentFile().mkdirs();

                            file.transferTo(dest);

                            PropertyMedia media = new PropertyMedia();

                            media.setFilePath(filePath);
                            media.setFileType(file.getContentType());
                            media.setProperty(property);

                            mediaList.add(media);
                        }
                    }

                    property.setMediaList(mediaList);
                }

                return propertyRepository.save(property);
            }
            public String deleteProperty(Long id) {

                Property property = propertyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Property not found"));

                propertyRepository.delete(property);

                return "Property deleted successfully";
            }

            }






