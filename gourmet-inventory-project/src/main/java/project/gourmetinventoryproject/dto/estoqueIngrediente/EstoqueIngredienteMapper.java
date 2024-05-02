package project.gourmetinventoryproject.dto.estoqueIngrediente;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EstoqueIngredienteMapper {


    public static EstoqueIngrediente toEntity(EstoqueIngredienteCriacaoDto estoqueIngredienteCriacaoDto){
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setLote(estoqueIngredienteCriacaoDto.getLote());
        estoqueIngrediente.setNome(estoqueIngredienteCriacaoDto.getNome());
        estoqueIngrediente.setCategoria(estoqueIngredienteCriacaoDto.getCategoria());
        estoqueIngrediente.setTipoMedida(estoqueIngredienteCriacaoDto.getTipoMedida());
        estoqueIngrediente.setValorMedida(estoqueIngredienteCriacaoDto.getValorMedida());
        estoqueIngrediente.setLocalArmazenamento(estoqueIngredienteCriacaoDto.getLocalArmazenamento());
        estoqueIngrediente.setDtaCadastro(estoqueIngredienteCriacaoDto.getDtaCadastro());
        estoqueIngrediente.setDtaAviso(estoqueIngredienteCriacaoDto.getDtaAviso());

        return estoqueIngrediente;
    }

    public static EstoqueIngredienteConsultaDto toDto(EstoqueIngrediente estoqueIngrediente){
        EstoqueIngredienteConsultaDto dtoConsulta = new EstoqueIngredienteConsultaDto(estoqueIngrediente.getIdItem(),estoqueIngrediente.getLote(),
                estoqueIngrediente.getNome(),
                estoqueIngrediente.getCategoria(),
                estoqueIngrediente.getTipoMedida(),
                estoqueIngrediente.getValorMedida(),
                estoqueIngrediente.getLocalArmazenamento(),
                estoqueIngrediente.getDtaCadastro(),
                estoqueIngrediente.getDtaAviso()
        );
        return dtoConsulta;
    }

    public static List<EstoqueIngredienteConsultaDto> toDto(List<EstoqueIngrediente> estoqueIngredientes){
        List<EstoqueIngredienteConsultaDto> listaDtoConsulta = new ArrayList<EstoqueIngredienteConsultaDto>();
        for (int i = 0; i < estoqueIngredientes.size(); i++) {
            EstoqueIngredienteConsultaDto dtoConsulta = new EstoqueIngredienteConsultaDto(
                    estoqueIngredientes.get(i).getIdItem(),
                    estoqueIngredientes.get(i).getLote(),
                    estoqueIngredientes.get(i).getNome(),
                    estoqueIngredientes.get(i).getCategoria(),
                    estoqueIngredientes.get(i).getTipoMedida(),
                    estoqueIngredientes.get(i).getValorMedida(),
                    estoqueIngredientes.get(i).getLocalArmazenamento(),
                    estoqueIngredientes.get(i).getDtaCadastro(),
                    estoqueIngredientes.get(i).getDtaAviso()
            );
            listaDtoConsulta.add(dtoConsulta);
        }
        return listaDtoConsulta;
    }

}
