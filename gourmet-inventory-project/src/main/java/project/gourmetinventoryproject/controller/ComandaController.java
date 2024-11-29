package project.gourmetinventoryproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Literal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Comanda;
import project.gourmetinventoryproject.dto.comanda.ComandaResponseDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.service.ComandaService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comandas")
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

    @GetMapping("/last")
    public ResponseEntity<Comanda> getLastComanda() {
        log.info("Buscando ultima comanda");
//        return new ResponseEntity<>(comandaService.getAllComandas(), HttpStatus.OK);
        return new ResponseEntity<>(comandaService.getLastComanda(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ComandaResponseDto>> getAllComandas() {
        log.info("Buscando todas as comandas");
//        List<ComandaResponseDto> comandas = comandaService.getAllComandas()
//                .stream()
//                .map(comanda -> new ComandaResponseDto(
//                        comanda.getId(),
//                        comanda.getIdGarcom(),
//                        comanda.getTitulo(),
//                        comanda.getMesa(),
//                        comanda.getItens().stream()
//                                .map(prato -> new PratoConsultaDto(
//                                        prato.getIdPrato(),
//                                        prato.getNome(),
//                                        prato.getDescricao(),
//                                        prato.getPreco(),
//                                        prato.getAlergicosRestricoes(),
//                                        prato.getCategoria(),
//                                        prato.getReceitaPrato().stream()
//                                                .map(ingrediente -> new IngredienteConsultaDto(
////                                                        ingrediente.getEstoqueIngrediente().getNome(),
////                                                        ingrediente.getTipoMedida(),
////                                                        ingrediente.getValorMedida()
//                                                ))
//                                                .collect(Collectors.toList()),
//                                        prato.getFoto(),
//                                        prato.getURLAssinada()
//                                ))
//                                .collect(Collectors.toList()),
//                        comanda.getStatus(),
//                        comanda.getTotal()))
//                .collect(Collectors.toList());
        List<Comanda> comandas = comandaService.getAllComandas();
        List<ComandaResponseDto> comandaResponseDto = comandas.stream()
                .map(comandaService::mapperRetornoComanda)
                .collect(Collectors.toList());
        return new ResponseEntity<>(comandaResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comanda> getComandaById(@PathVariable Long id) {
        log.info("Buscando comanda com id: {}", id);
        return comandaService.getComandaById(id)
                .map(comanda -> new ResponseEntity<>(comanda, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComandaResponseDto> updateComanda(@PathVariable Long id, @RequestBody Comanda updatedComanda) {
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
    public ResponseEntity<ComandaResponseDto> removePratoFromComanda(@PathVariable Long comandaId, @PathVariable Long pratoId) {
        return new ResponseEntity<>(comandaService.removePratoFromComanda(comandaId, pratoId), HttpStatus.OK);
    }

    @PatchMapping("/{comandaId}/status")
    public ResponseEntity<ComandaResponseDto> updateComandaStatus(@PathVariable Long comandaId, @RequestParam String status) {
        ComandaResponseDto updatedComanda = comandaService.updateStatus(comandaId, status);
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