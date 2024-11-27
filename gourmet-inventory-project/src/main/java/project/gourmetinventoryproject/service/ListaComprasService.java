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
    @Autowired
    private RelatorioRepository relatorioRepository;

    public  List<ItemListaCompras> getItemCompras(Long idEmpresa){
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<ItemListaCompras> itemListaCompras = itemsComprasRepository.findAllByEmpresa(empresa);
        return itemListaCompras;
    }

//    public void postListaCompras( LocalDate dataInicio, LocalDate dataFim,Long idEmpresa) {
//        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
//        List<Alerta> alertas = alertaRepository.findAllByEmpresaAndTipoAlerta(empresa);
//        List<Relatorio> relatorios = relatorioRepository.findAllByDataMonthRange(dataInicio.getMonthValue(),dataFim.getMonthValue(),empresa);
//        List<ItemListaCompras> itens = itemsComprasRepository.findAllByEmpresa(empresa);
//        System.out.println("Iniciando for lista de compras");
//        System.out.println(alertas.size());
//        if (!alertas.isEmpty()){
//            for (int i = 0; i < alertas.size(); i++) {
//                System.out.println("dentro do for");
//                ItemListaCompras item = createItemListaCompras(alertas.get(i), relatorios, itens);
//                itemsComprasRepository.save(item);
//                System.out.println("Adicionando itens a lista de compras");
//            }
//        }
//    }

//    public ItemListaCompras createItemListaCompras(Alerta alerta, List<Relatorio> relatorios,List<ItemListaCompras> itens) {
//        System.out.println("criar item");
//        if (!itens.isEmpty()) {
//            for (int i = 0; i < itens.size(); i++) {
//                if (alerta.getEstoqueIngrediente().equals(itens.get(i).getEstoqueIngrediente())) {
//                    System.out.println("Atualizando item");
//                    ItemListaCompras itemListaCompras = new ItemListaCompras();
//                    itemListaCompras.setIdItemLista(itens.get(i).getIdItemLista());
//                    itemListaCompras.setNome(itens.get(i).getNome());
//                    itemListaCompras.setQtdMedia(calcularMedia(itens.get(i).getEstoqueIngrediente().getEmpresa().getIdEmpresa(),alerta,relatorios));
//                    itemListaCompras.setEstoqueIngrediente(itens.get(i).getEstoqueIngrediente());
//                    System.out.println("objeto criado"+ itemListaCompras);
//                    return itemListaCompras;
//                }
//            }
//        }
//        System.out.println("Criando objeto item lista compra");
//        ItemListaCompras itemListaCompras = new ItemListaCompras();
//        itemListaCompras.setNome(alerta.getEstoqueIngrediente().getNome());
//        itemListaCompras.setQtdMedia(calcularMedia(alerta.getEstoqueIngrediente().getEmpresa().getIdEmpresa(), alerta, relatorios));
//        itemListaCompras.setEstoqueIngrediente(alerta.getEstoqueIngrediente());
//        System.out.println("objeto criado" + itemListaCompras);
//        return itemListaCompras;
//
//
//    }
//    public Integer calcularMedia(Long idEmpresa,Alerta alerta,List<Relatorio> relatorios){
//        System.out.println("calculador media");
//        EstoqueIngrediente estoqueIngrediente = alerta.getEstoqueIngrediente();
//        Double total = 0.0;
//        int qtd = 0;
//        for (int i = 0; i < relatorios.size(); i++) {
//            for (int j = 0; j < relatorios.get(i).getPratoList().size(); j++) {
//                for (int k = 0; k < relatorios.get(i).getPratoList().get(j).getReceitaPrato().size(); k++) {
//                    if (estoqueIngrediente.equals(relatorios.get(i).getPratoList().get(j).getReceitaPrato().get(k).getEstoqueIngrediente())) {
//                        total+= relatorios.get(i).getPratoList().get(j).getReceitaPrato().get(k).getValorMedida();
//                        qtd += 1;
//                    }
//                }
//            }
//        }
//        Integer media = (int) (Math.floor(total)/qtd);
//        System.out.println("Media"+ media);
//        return media ;
//
//    }
    public void deleteItemCompras(List<ItemListaCompras> itemListaCompras) {
        for (ItemListaCompras item : itemListaCompras) {
            itemsComprasRepository.delete(item);
        }

    }

}
