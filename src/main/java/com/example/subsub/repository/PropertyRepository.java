package com.example.subsub.repository;

import com.example.subsub.domain.Property;
import com.example.subsub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findTop5ByOrderByExpiredAtAsc();
}