package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.service.PratoService;

import java.util.List;

@RestController
@RequestMapping("/pratos")
public class PratoController {

    @Autowired
    private PratoService pratoService;

    @GetMapping
    public ResponseEntity<List<Prato>> getAllPratos() {
        List<Prato> pratos = pratoService.getAllPratos();
        return new ResponseEntity<>(pratos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prato> getPratoById(@PathVariable Long id) {
        Prato prato = pratoService.getPratoById(id);
        return new ResponseEntity<>(prato, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Prato> createPrato(@RequestBody Prato prato) {
        Prato newPrato = pratoService.createPrato(prato);
        return new ResponseEntity<>(newPrato, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prato> updatePrato(@PathVariable Long id, @RequestBody Prato prato) {
        Prato updatedPrato = pratoService.updatePrato(id, prato);
        if (updatedPrato != null) {
            return new ResponseEntity<>(updatedPrato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrato(@PathVariable Long id) {
        pratoService.deletePrato(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}