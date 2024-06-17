package com.example.my_spring_boot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.my_spring_boot_app.model.ViaTabloid;

@Repository
public interface TabloidRepository extends JpaRepository<ViaTabloid, Long> {
}
