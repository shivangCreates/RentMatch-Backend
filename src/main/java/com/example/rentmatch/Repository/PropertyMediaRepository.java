package com.example.rentmatch.Repository;

import com.example.rentmatch.Entity.PropertyMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyMediaRepository extends JpaRepository<PropertyMedia,Long > {
    List<PropertyMedia> findByPropertyId(Long propertyId);
}
