package project.gourmetinventoryproject.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.dto.alerta.AlertaConsultaDto;
import project.gourmetinventoryproject.dto.alerta.TiposAlertasDto;
import project.gourmetinventoryproject.service.AlertaService;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alerta")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<AlertaConsultaDto>> getAllAlertas(@PathVariable Long idEmpresa) {
        List<Alerta> alertas = alertaService.getAllAlerta(idEmpresa);
        return alertas.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(alertas.stream()
                .map(alerta -> mapper.map(alerta, AlertaConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/quantidade-tipos-alerta/{idEmpresa}")
    public ResponseEntity<TiposAlertasDto> getAlertasByTipo(@PathVariable Long idEmpresa) {
        TiposAlertasDto tiposAlertasDto = alertaService.getTipoAlertas(idEmpresa);
        return new ResponseEntity<>(tiposAlertasDto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlerta(@PathVariable Long id) {
        alertaService.deleteAlerta(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}