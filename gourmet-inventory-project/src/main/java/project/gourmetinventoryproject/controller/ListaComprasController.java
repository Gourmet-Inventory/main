package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.gourmetinventoryproject.domain.ItemListaCompras;
import project.gourmetinventoryproject.service.ListaComprasService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ListaCompras")
public class ListaComprasController {
    @Autowired
    private ListaComprasService listaComprasService;

    @GetMapping("/listaCompras")
    public ResponseEntity<List<ItemListaCompras>> getListaCompras(Long idEmpresa) {
        List<ItemListaCompras> listaDto = listaComprasService.getItemCompras(idEmpresa);
        return ResponseEntity.ok(listaDto);
    }

    @PostMapping("/createItensCompras")
    public void postItensCompras(LocalDate dataInicio, LocalDate dataFim, Long idEmpresa) {
        listaComprasService.postListaCompras(dataInicio,dataFim,idEmpresa);
    }

}
