    package com.example.rentmatch.Entity;

    import jakarta.persistence.*;

    import java.util.List;

    @Entity
    @Table(name = "properties")
    public class Property {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        private String description;

        private Double price;

        private String location;

        private String propertyType;

        // Many properties can belong to one owner
        @ManyToOne
        @JoinColumn(name = "owner_id")
        private User owner;

        // One property can have many images/videos
        @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
        private List<PropertyMedia> mediaList;

        // Constructors
        public Property() {
        }

        // Getters and Setters

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPropertyType() {
            return propertyType;
        }

        public void setPropertyType(String propertyType) {
            this.propertyType = propertyType;
        }

        public User getOwner() {
            return owner;
        }

        public void setOwner(User owner) {
            this.owner = owner;
        }

        public List<PropertyMedia> getMediaList() {
            return mediaList;
        }

        public void setMediaList(List<PropertyMedia> mediaList) {
            this.mediaList = mediaList;
        }
    }

