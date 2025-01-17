package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.ItemListaCompras;
import project.gourmetinventoryproject.service.ListaComprasService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ListaCompras")
public class ListaComprasController {
    @Autowired
    private ListaComprasService listaComprasService;

    @GetMapping("/listaCompras/{idEmpresa}")
    public ResponseEntity<List<ItemListaCompras>> getListaCompras(@PathVariable Long idEmpresa) {
        List<ItemListaCompras> listaDto = listaComprasService.getItemCompras(idEmpresa);
        return ResponseEntity.ok(listaDto);
    }
    @DeleteMapping("/lista")
    public void deleteItemsCompras(@RequestBody List<ItemListaCompras> itemListaCompras){
        listaComprasService.deleteItemsCompras(itemListaCompras);
    }
    @DeleteMapping("/{idItem}")
    public void deleteItemCompras(@PathVariable Long idItem){
        listaComprasService.deleteItemCompras(idItem);
    }


}
