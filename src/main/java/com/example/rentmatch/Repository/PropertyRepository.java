package com.example.rentmatch.Repository;

import com.example.rentmatch.Entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property,Long> {
    List <Property> findByOwnerEmail(String ownerId);
}
