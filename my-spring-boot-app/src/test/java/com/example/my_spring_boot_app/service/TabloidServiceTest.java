package com.example.my_spring_boot_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.my_spring_boot_app.model.ViaTabloid;
import com.example.my_spring_boot_app.repository.TabloidRepository;

public class TabloidServiceTest {

    @Mock
    private TabloidRepository repository;

    @InjectMocks
    private TabloidService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTabloids() {
        ViaTabloid tabloid1 = new ViaTabloid();
        tabloid1.setId(1L);
        tabloid1.setTitle("Title 1");

        ViaTabloid tabloid2 = new ViaTabloid();
        tabloid2.setId(2L);
        tabloid2.setTitle("Title 2");

        when(repository.findAll()).thenReturn(Arrays.asList(tabloid1, tabloid2));

        List<ViaTabloid> tabloids = service.getAllTabloids();
        assertEquals(2, tabloids.size());
        assertEquals("Title 1", tabloids.get(0).getTitle());
        assertEquals("Title 2", tabloids.get(1).getTitle());
    }

    @Test
    public void testGetTabloidById() {
        ViaTabloid tabloid = new ViaTabloid();
        tabloid.setId(1L);
        tabloid.setTitle("Title 1");

        when(repository.findById(1L)).thenReturn(Optional.of(tabloid));

        Optional<ViaTabloid> foundTabloid = service.getTabloidById(1L);
        assertEquals("Title 1", foundTabloid.get().getTitle());
    }

    @Test
    public void testCreateTabloid() {
        ViaTabloid tabloid = new ViaTabloid();
        tabloid.setTitle("New Tabloid");

        when(repository.save(any(ViaTabloid.class))).thenReturn(tabloid);

        ViaTabloid createdTabloid = service.createTabloid(tabloid);
        assertEquals("New Tabloid", createdTabloid.getTitle());
    }

    @Test
    public void testUpdateTabloid() {
        ViaTabloid tabloid = new ViaTabloid();
        tabloid.setId(1L);
        tabloid.setTitle("Updated Tabloid");

        when(repository.save(any(ViaTabloid.class))).thenReturn(tabloid);

        ViaTabloid updatedTabloid = service.updateTabloid(tabloid);
        assertEquals("Updated Tabloid", updatedTabloid.getTitle());
    }

    @Test
    public void testDeleteTabloid() {
        doNothing().when(repository).deleteById(1L);
        service.deleteTabloid(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
