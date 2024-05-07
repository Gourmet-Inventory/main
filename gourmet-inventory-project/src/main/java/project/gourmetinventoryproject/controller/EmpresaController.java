package project.gourmetinventoryproject.controller;

import project.gourmetinventoryproject.domain.Empresa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;
import project.gourmetinventoryproject.service.EmpresaService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Void> postEmpresa(@RequestBody @Valid EmpresaCriacaoDto novaEmpresa) {
        empresaService.postEmpresa(novaEmpresa);
        return status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresas() {
        List<Empresa> empresas = empresaService.getEmpresas();
        return empresas.isEmpty() ? status(204).build() : status(200).body(empresas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return status(200).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchEmpresa(@PathVariable Long id, @RequestBody @Valid EmpresaCriacaoDto empresaAtualizada) {
        empresaService.patchEmpresa(id, empresaAtualizada);
        return status(200).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Empresa>> getEmpresasById(@PathVariable Long id) {
        Optional<Empresa> empresas = empresaService.getEmpresasById(id);
        return empresas.isEmpty() ? status(204).build() : status(200).body(empresas);
    }
}
