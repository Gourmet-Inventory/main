package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.service.ReceitaService;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping
    public ResponseEntity<List<Receita>> getAllReceitas() {
        List<Receita> receitas = receitaService.getAllReceitas();
        return receitas.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(receitas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> getReceitaById(@PathVariable Long id) {
        Receita receita = receitaService.getReceitaById(id);
        return new ResponseEntity<>(receita, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Receita> createReceita(@RequestBody Receita receita) {
        Receita newReceita = receitaService.createReceita(receita);
        return new ResponseEntity<>(newReceita, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receita> updateReceita(@PathVariable Long id, @RequestBody Receita receita) {
        Receita updatedReceita = receitaService.updateReceita(id, receita);
        return new ResponseEntity<>(updatedReceita, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReceita(@PathVariable Long id) {
        receitaService.deleteReceita(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

