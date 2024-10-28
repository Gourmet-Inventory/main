package project.gourmetinventoryproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Comanda;
import project.gourmetinventoryproject.service.ComandaService;

import java.util.List;

@RestController
@RequestMapping("/comandas")
@RequiredArgsConstructor
@Slf4j
public class ComandaController {

    private final ComandaService comandaService;

    @PostMapping
    public ResponseEntity<Comanda> createComanda(@RequestBody Comanda comanda) {
        log.info("Criando nova comanda");
        Comanda createdComanda = comandaService.createComanda(comanda);
        return new ResponseEntity<>(createdComanda, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Comanda>> getAllComandas() {
        log.info("Buscando todas as comandas");
        return new ResponseEntity<>(comandaService.getAllComandas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comanda> getComandaById(@PathVariable Long id) {
        log.info("Buscando comanda com id: {}", id);
        return comandaService.getComandaById(id)
                .map(comanda -> new ResponseEntity<>(comanda, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comanda> updateComanda(@PathVariable Long id, @RequestBody Comanda updatedComanda) {
        log.info("Atualizando comanda com id: {}", id);
        try {
            return new ResponseEntity<>(comandaService.updateComanda(id, updatedComanda), HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar comanda: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{comandaId}/addPrato/{pratoId}")
    public ResponseEntity<Comanda> addPratoToComanda(@PathVariable Long comandaId, @PathVariable Long pratoId) {
        return new ResponseEntity<>(comandaService.addPratoToComanda(comandaId, pratoId), HttpStatus.OK);
    }

    @DeleteMapping("/{comandaId}/removePrato/{pratoId}")
    public ResponseEntity<Comanda> removePratoFromComanda(@PathVariable Long comandaId, @PathVariable Long pratoId) {
        return new ResponseEntity<>(comandaService.removePratoFromComanda(comandaId, pratoId), HttpStatus.OK);
    }

    @PatchMapping("/{comandaId}/status")
    public ResponseEntity<Comanda> updateComandaStatus(@PathVariable Long comandaId, @RequestParam String status) {
        Comanda updatedComanda = comandaService.updateStatus(comandaId, status);
        return new ResponseEntity<>(updatedComanda, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComanda(@PathVariable Long id) {
        log.info("Deletando comanda com id: {}", id);
        try {
            comandaService.deleteComanda(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar comanda: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
