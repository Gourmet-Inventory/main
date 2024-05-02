package project.gourmetinventoryproject.dto.ingrediente;


import org.springframework.stereotype.Component;
import project.gourmetinventoryproject.domain.Ingrediente;

import java.util.ArrayList;
import java.util.List;



@Component
public class IngredienteMapper {
    public static Ingrediente toEntity(IngredienteCriacaoDto dto) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(dto.getEstoqueIngrediente());
        ingrediente.setTipoMedida(dto.getTipoMedida());
        ingrediente.setValorMedida(dto.getValorMedida());
        return ingrediente;
    }

    public static IngredienteConsultaDto toDto(Ingrediente ingrediente) {
        IngredienteConsultaDto dtoConsulta = new IngredienteConsultaDto(
                ingrediente.getIdIngrediente(),
                ingrediente.getEstoqueIngrediente(),
                ingrediente.getTipoMedida(),
                ingrediente.getValorMedida()
                );
        return dtoConsulta;
    }
    public static List<IngredienteConsultaDto> toDto(List<Ingrediente> ingredientes) {
        List<IngredienteConsultaDto> listaDtoConsulta = new ArrayList<>();
        for (int i = 0; i < ingredientes.size(); i++) {
            IngredienteConsultaDto dtoConsulta = new IngredienteConsultaDto(
                    ingredientes.get(i).getIdIngrediente(),
                    ingredientes.get(i).getEstoqueIngrediente(),
                    ingredientes.get(i).getTipoMedida(),
                    ingredientes.get(i).getValorMedida());
            listaDtoConsulta.add(dtoConsulta);
        }
        return listaDtoConsulta;
    }
}
