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

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<AlertaConsultaDto>> getAllAlertas(@PathVariable Long idEmpresa) {
        List<Alerta> alertas = alertaService.getAllAlerta(idEmpresa);
        return alertas.isEmpty() ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT) : new ResponseEntity<>(alertas.stream()
                .map(alerta -> mapper.map(alerta, AlertaConsultaDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{idEmpresa}")
    //Arrumar alertas duplicados
    public ResponseEntity<List<AlertaConsultaDto>> createAlerta(@PathVariable Long idEmpresa){
        List<AlertaConsultaDto> alertaConsultaDtos = new ArrayList<>();
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes(idEmpresa);
        if (estoqueIngredientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (int i = 0; i < estoqueIngredientes.size(); i++) {
            if (tipoAlerta(estoqueIngredientes.get(i)) != null){
                Alerta alerta = new Alerta();
                alerta.setTipoAlerta(tipoAlerta(estoqueIngredientes.get(i)));
                alerta.setEstoqueIngrediente(estoqueIngredientes.get(i));
                try {
                    alertaConsultaDtos.add(mapper.map(alertaService.createAlerta(alerta), AlertaConsultaDto.class));
                }catch (Exception e){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return alertaConsultaDtos.isEmpty()? new ResponseEntity<>(HttpStatus.NO_CONTENT) :new ResponseEntity<>(alertaConsultaDtos, HttpStatus.CREATED);
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