package com.example.my_spring_boot_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.my_spring_boot_app.model.ViaTabloid;
import com.example.my_spring_boot_app.repository.TabloidRepository;

@Service
public class TabloidService {

    @Autowired
    private TabloidRepository repository;

    public List<ViaTabloid> getAllTabloids() {
        return repository.findAll();
    }

    public Optional<ViaTabloid> getTabloidById(Long id) {
        return repository.findById(id);
    }

    public ViaTabloid createTabloid(ViaTabloid tabloid) {
        return repository.save(tabloid);
    }

    public ViaTabloid updateTabloid(ViaTabloid tabloid) {
        return repository.save(tabloid);
    }
    public void deleteTabloid(Long id) {
        repository.deleteById(id);
    }
}
