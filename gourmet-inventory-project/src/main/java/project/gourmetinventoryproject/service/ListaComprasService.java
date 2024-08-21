package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.*;
import project.gourmetinventoryproject.dto.alerta.ItemListaComprasDto;
import project.gourmetinventoryproject.repository.AlertaRepository;
import project.gourmetinventoryproject.repository.RelatorioRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListaComprasService {
    @Autowired
    private AlertaRepository alertaRepository;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private RelatorioService relatorioService;
    @Autowired
    private RelatorioRepository relatorioRepository;


    public List<ItemListaComprasDto> getListaCompras(Long idEmpresa,LocalDate dataInicio,LocalDate dataFim) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<Alerta> alertas = alertaRepository.findAllByEmpresaAndTipoAlerta(empresa,"Estoque acabando");
        List<Relatorio> relatorios = relatorioRepository.findAllByDataMonthRange(dataInicio.getMonthValue(),dataFim.getMonthValue(),empresa);
        List<ItemListaComprasDto> dtos = new ArrayList<>();
        System.out.println("Iniciando for lista de compras");
        for (int i = 0; i < alertas.size(); i++) {
            ItemListaComprasDto item = createItemListaCompras(alertas.get(i),relatorios);
            dtos.add(item);
            System.out.println("Adicionando itens a lista de compras");
        }
        return dtos;
    }

    public ItemListaComprasDto createItemListaCompras(Alerta alerta, List<Relatorio> relatorios) {
        System.out.println("Criando objeto item lista compra");
        ItemListaComprasDto itemListaComprasDto = new ItemListaComprasDto();
        itemListaComprasDto.setNome(alerta.getEstoqueIngrediente().getNome());
        itemListaComprasDto.setQtdMedia(calcularMedia(alerta.getEstoqueIngrediente().getEmpresa().getIdEmpresa(),alerta,relatorios));
        itemListaComprasDto.setEstoqueIngrediente(alerta.getEstoqueIngrediente());
        System.out.println("objeto criado"+itemListaComprasDto);
        return itemListaComprasDto;
    }
    public Integer calcularMedia(Long idEmpresa,Alerta alerta,List<Relatorio> relatorios){
        System.out.println("calculador media");
        EstoqueIngrediente estoqueIngrediente = alerta.getEstoqueIngrediente();
        Double total = 0.0;
        int qtd = 0;
        for (int i = 0; i < relatorios.size(); i++) {
            for (int j = 0; j < relatorios.get(i).getPratoList().size(); j++) {
                for (int k = 0; k < relatorios.get(i).getPratoList().get(j).getReceitaPrato().size(); k++) {
                    if (estoqueIngrediente.equals(relatorios.get(i).getPratoList().get(j).getReceitaPrato().get(k).getEstoqueIngrediente())) {
                        total+= relatorios.get(i).getPratoList().get(j).getReceitaPrato().get(k).getValorMedida();
                        qtd += 1;
                    }
                }
            }
        }
        Integer media = (int) (Math.floor(total)/qtd);
        System.out.println("Media"+ media);
        return media ;

    }

}
