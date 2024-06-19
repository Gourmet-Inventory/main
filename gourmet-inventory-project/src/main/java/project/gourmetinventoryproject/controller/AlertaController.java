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
@RequestMapping("/alerta")
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

//    @PostMapping()
//    //Arrumar alertas duplicados
//    public ResponseEntity<List<AlertaConsultaDto>> createAlerta(){
//        List<Alerta> alertas = alertaService.createAlerta();
//        List<AlertaConsultaDto> alertaConsultaDtos = new ArrayList<>();
//        for (Alerta alerta : alertas) {
//            alertaConsultaDtos.add(mapper.map(alerta, AlertaConsultaDto.class));
//        }
//        return alertaConsultaDtos.isEmpty()? new ResponseEntity<>(HttpStatus.NO_CONTENT) :new ResponseEntity<>(alertaConsultaDtos, HttpStatus.CREATED);
//    }

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