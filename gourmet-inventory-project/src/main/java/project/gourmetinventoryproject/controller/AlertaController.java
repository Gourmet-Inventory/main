package project.gourmetinventoryproject.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.dto.alerta.AlertaConsultaDto;
import project.gourmetinventoryproject.service.AlertaService;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alerta")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping()
    public ResponseEntity<List<AlertaConsultaDto>> getAllAlertas() {
        List<Alerta> alertas = alertaService.getAllAlerta();
        return alertas.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(alertas.stream()
                .map(alerta -> mapper.map(alerta, AlertaConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{idEmpresa}")
    public ResponseEntity<List<AlertaConsultaDto>> createAlerta(Long idEmpresa){
        List<AlertaConsultaDto> alertaConsultaDtos = new ArrayList<>();
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes(idEmpresa);
        for (int i = 0; i < estoqueIngredientes.size(); i++) {
            if (tipoAlerta(estoqueIngredientes.get(i)) != null){
                Alerta alerta = new Alerta();
                alerta.setTipoAlerta(tipoAlerta(estoqueIngredientes.get(i)));
                alerta.setEstoqueIngrediente(estoqueIngredientes.get(i));
                alertaService.createAlerta(alerta);
                alertaConsultaDtos.add(mapper.map(alerta, AlertaConsultaDto.class));
            }
        }
        return new ResponseEntity<>(alertaConsultaDtos, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlerta(@PathVariable Long id) {
        alertaService.deleteAlerta(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String tipoAlerta(EstoqueIngrediente estoqueIngrediente){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataTresDiasDepois = dataAtual.plusDays(3);

        if (estoqueIngrediente.getDtaAviso().isEqual(dataAtual)){
            return "Dia de Checagem";
        }
        if (estoqueIngrediente.getDtaAviso().isBefore(dataTresDiasDepois)){
            return "Data Proxima";
        }
        if (estoqueIngrediente.getValorTotal() <= 10){
            return "Estoque vazio";
        }
        if (estoqueIngrediente.getValorTotal() <= 200){
            return "Estoque acabando";
        }
        else {
            return null;
        }
    }
}