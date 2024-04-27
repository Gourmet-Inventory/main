package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.service.IngredienteService;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @Operation(description = "Obter lista de ingredientes")
    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAllIngredientes() {
        List<Ingrediente> ingredientes = ingredienteService.getAllIngredientes();
        return ingredientes.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(ingredientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> getIngredienteById(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteService.getIngredienteById(id);
        return new ResponseEntity<>(ingrediente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ingrediente> createIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente newIngrediente = ingredienteService.createIngrediente(ingrediente);
        return new ResponseEntity<>(newIngrediente, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> updateIngrediente(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        Ingrediente updatedIngrediente = ingredienteService.updateIngrediente(id, ingrediente);
        return new ResponseEntity<>(updatedIngrediente, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngrediente(@PathVariable Long id) {
        ingredienteService.deleteIngrediente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}