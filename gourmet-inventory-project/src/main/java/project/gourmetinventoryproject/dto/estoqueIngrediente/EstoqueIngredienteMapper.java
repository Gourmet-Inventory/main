package project.gourmetinventoryproject.dto.estoqueIngrediente;

import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.util.ArrayList;
import java.util.List;

public class EstoqueIngredienteMapper {


    public static EstoqueIngrediente toEntity(EstoqueIngredienteCriacaoDto dto){
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setLote(dto.getLote());
        estoqueIngrediente.setNome(dto.getNome());
        estoqueIngrediente.setCategoria(dto.getCategoria());
        estoqueIngrediente.setTipoMedida(dto.getTipoMedida());
        estoqueIngrediente.setValorMedida(dto.getValorMedida());
        estoqueIngrediente.setLocalArmazenamento(dto.getLocalArmazenamento());
        estoqueIngrediente.setDtaCadastro(dto.getDtaCadastro());
        estoqueIngrediente.setDtaAviso(dto.getDtaAviso());

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
