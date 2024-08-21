package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.dto.alerta.ItemListaComprasDto;
import project.gourmetinventoryproject.service.ListaComprasService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ListaCompras")
public class ListaComprasController {
    @Autowired
    private ListaComprasService listaComprasService;

    @GetMapping()
    public ResponseEntity<List<ItemListaComprasDto>> getListaCompras(LocalDate dataInicio, LocalDate dataFim,Long idEmpresa) {
        List<ItemListaComprasDto> listaDto = listaComprasService.getListaCompras(idEmpresa,dataInicio,dataFim);
        return ResponseEntity.ok(listaDto);
    }

}
