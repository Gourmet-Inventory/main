package project.gourmetinventoryproject.dto.receita;

import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;

import java.util.ArrayList;
import java.util.List;

public class ReceitaMapper {
//    private Long id;
////    private Long idIngrediente;
////    private Long idPrato;
////    private Boolean manipulavel;
//public static Prato toEntity(PratoConsultaDto dtoConsulta) {

    public Receita toEntity(ReceitaCriacaoDto Dto) {
        Receita receita = new Receita();
        receita.setIdIngrediente(Dto.getIdIngrediente());
        receita.setIdPrato(Dto.getIdPrato());
        receita.setManipulavel(Dto.getManipulavel());
        return receita;
    }
    public ReceitaConsultaDto toDto(Receita receita){
        ReceitaConsultaDto dtoConsulta = new ReceitaConsultaDto(
                receita.getId(),
                receita.getIdIngrediente(),
                receita.getIdPrato(),
                receita.getManipulavel()
        );
        return dtoConsulta;

    }
    public List<ReceitaConsultaDto> toDto(List<Receita> receitas){
        List<ReceitaConsultaDto> listaDtoConsulta = new ArrayList<>();
        for (int i = 0; i < receitas.size(); i++) {
            ReceitaConsultaDto dtoConsulta = new ReceitaConsultaDto(
                    receitas.get(i).getId(),
                    receitas.get(i).getIdIngrediente(),
                    receitas.get(i).getIdPrato(),
                    receitas.get(i).getManipulavel()
            );
            listaDtoConsulta.add(dtoConsulta);
        }
        return listaDtoConsulta;

    }
}
