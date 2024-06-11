package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Criar nova empresa", method = "POST")
    @PostMapping
    public ResponseEntity<Void> postEmpresa(@RequestBody @Valid EmpresaCriacaoDto novaEmpresa) {
        empresaService.postEmpresa(novaEmpresa);
        return status(201).build();
    }

    @Operation(summary = "Obter uma lista de empresa", method = "GET")
    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresas() {
        List<Empresa> empresas = empresaService.getEmpresas();
        return empresas.isEmpty() ? status(204).build() : status(200).body(empresas);
    }

    @Operation(summary = "Deletar empresa por id", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        empresaService.deleteEmpresa(id);
        return status(200).build();
    }

    @Operation(summary = "Atualizar empresa por id", method = "Patch")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchEmpresa(@PathVariable Long id, @RequestBody @Valid EmpresaCriacaoDto empresaAtualizada) {
        empresaService.patchEmpresa(id, empresaAtualizada);
        return status(200).build();
    }
    @Operation(summary = "Atualizar empresa por id", method = "Patch")
    @PutMapping("/{id}")
    public ResponseEntity<Void> putEmpresa(@PathVariable Long id, @RequestBody @Valid EmpresaCriacaoDto empresaAtualizada) {
        empresaService.putEmpresa(id, empresaAtualizada);
        return status(200).build();
    }

    @Operation(summary = "Obter empresa por id", method = "GET")
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresasById(@PathVariable Long id) {
        Empresa empresa = empresaService.getEmpresasById(id);
        return ResponseEntity.status(200).body(empresa);
    }
}
