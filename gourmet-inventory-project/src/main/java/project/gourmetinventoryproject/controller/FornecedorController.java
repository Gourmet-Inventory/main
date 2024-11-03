package project.gourmetinventoryproject.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Fornecedor;
import project.gourmetinventoryproject.dto.fornecedor.FornecedorCriacaoDto;
import project.gourmetinventoryproject.service.FornecedorService;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<Void> postFornecedor(@RequestBody FornecedorCriacaoDto novoFornecedor) {
        try {
            fornecedorService.postFornecedor(novoFornecedor);
            return status(201).build();
        } catch (Exception e) {
            // Aqui você pode adicionar um log da exceção, retornar um status apropriado, etc.
            return status(500).build(); // Status 500 indica erro interno do servidor
        }
    }


    @GetMapping
    public ResponseEntity<List<Fornecedor>> getFornecedor(){
        List<Fornecedor> fornecedores = fornecedorService.getFornecedores();
        return fornecedores.isEmpty() ? status(204).build() : status(200).body(fornecedores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id){
        fornecedorService.deleteFornecedor(id);
        return status(200).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchFornecedor(@PathVariable Long id, @RequestBody @Valid FornecedorCriacaoDto fornecedorAtualizado){
        fornecedorService.patchFornecedor(id, fornecedorAtualizado);
        return status(200).build();
    }
}
