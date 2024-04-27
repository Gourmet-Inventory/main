package project.gourmetinventoryproject.controller;

import project.gourmetinventoryproject.service.EstoqueIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;


import java.util.List;

@RestController
@RequestMapping("/estoque-ingrediente")
public class EstoqueIngredienteController {

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @GetMapping
    public ResponseEntity<List<EstoqueIngrediente>> getAllEstoqueIngredientes() {
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes();
        return estoqueIngredientes.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(estoqueIngredientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueIngrediente> getEstoqueIngredienteById(@PathVariable Long id) {
        EstoqueIngrediente estoqueIngrediente = estoqueIngredienteService.getEstoqueIngredienteById(id);
        return new ResponseEntity<>(estoqueIngrediente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EstoqueIngrediente> createEstoqueIngrediente(@RequestBody EstoqueIngrediente estoqueIngrediente) {
        EstoqueIngrediente newEstoqueIngrediente = estoqueIngredienteService.createEstoqueIngrediente(estoqueIngrediente);
        return new ResponseEntity<>(newEstoqueIngrediente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueIngrediente> updateEstoqueIngrediente(@PathVariable Long id, @RequestBody EstoqueIngrediente estoqueIngrediente) {
        EstoqueIngrediente updatedEstoqueIngrediente = estoqueIngredienteService.updateEstoqueIngrediente(id, estoqueIngrediente);
        return new ResponseEntity<>(updatedEstoqueIngrediente, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstoqueIngrediente(@PathVariable Long id) {
        estoqueIngredienteService.deleteEstoqueIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

