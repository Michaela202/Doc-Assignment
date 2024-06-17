package com.example.my_spring_boot_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_spring_boot_app.model.ViaTabloid;
import com.example.my_spring_boot_app.service.TabloidService;

@RestController
@RequestMapping("/api/tabloids")
@CrossOrigin(origins="http://localhost:3000")
public class TabloidController {

    @Autowired
    private TabloidService service;

    @GetMapping
    public List<ViaTabloid> getAllTabloids() {
        return service.getAllTabloids();
    }
  
    @PutMapping("/{id}")
    public ResponseEntity<ViaTabloid> updateTabloid(@PathVariable Long id, @RequestBody ViaTabloid tabloidDetails) {
        Optional<ViaTabloid> optionalTabloid = service.getTabloidById(id);
        if (optionalTabloid.isPresent()) {
            ViaTabloid tabloid = optionalTabloid.get();
            tabloid.setTitle(tabloidDetails.getTitle());
            tabloid.setContent(tabloidDetails.getContent());
            tabloid.setDepartment(tabloidDetails.getDepartment());
            final ViaTabloid updatedTabloid = service.updateTabloid(tabloid);
            return ResponseEntity.ok(updatedTabloid);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ViaTabloid> getTabloidById(@PathVariable Long id) {
        Optional<ViaTabloid> tabloid = service.getTabloidById(id);
        return tabloid.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ViaTabloid createTabloid(@RequestBody ViaTabloid tabloid) {
        return service.createTabloid(tabloid);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTabloid(@PathVariable Long id) {
        service.deleteTabloid(id);
        return ResponseEntity.noContent().build();
    }
}
