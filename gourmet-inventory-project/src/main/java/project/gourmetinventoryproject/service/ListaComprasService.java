package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.*;
import project.gourmetinventoryproject.domain.ItemListaCompras;
import project.gourmetinventoryproject.repository.AlertaRepository;
import project.gourmetinventoryproject.repository.ItemsComprasRepository;
import project.gourmetinventoryproject.repository.RelatorioRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaComprasService {
    @Autowired
    private AlertaRepository alertaRepository;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private ItemsComprasRepository itemsComprasRepository;

    public  List<ItemListaCompras> getItemCompras(Long idEmpresa){
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<ItemListaCompras> itemListaCompras = itemsComprasRepository.findAllByEmpresa(empresa);
        return itemListaCompras;
    }

    public void postListaCompras( Long idEmpresa,Alerta alerta) {
        System.out.println("Iniciando for lista de compras");
        System.out.println("dentro do for");
        ItemListaCompras item = createItemListaCompras(alerta);
        itemsComprasRepository.save(item);
        System.out.println("Adicionando itens a lista de compras");

    }

    public ItemListaCompras createItemListaCompras(Alerta alerta) {
        System.out.println("criar item");
        ItemListaCompras itemListaCompras = new ItemListaCompras();
        itemListaCompras.setNome(alerta.getEstoqueIngrediente().getNome());
        itemListaCompras.setEstoqueIngrediente(alerta.getEstoqueIngrediente());
        System.out.println("objeto criado"+ itemListaCompras);
        return itemListaCompras;
    }
    public void deleteItemsCompras(List<ItemListaCompras> itemListaCompras) {
        for (ItemListaCompras item : itemListaCompras) {
            itemsComprasRepository.delete(item);
        }
    }
    public void deleteItemCompras(ItemListaCompras itemListaCompra) {
        itemsComprasRepository.delete(itemListaCompra);
    }

}
