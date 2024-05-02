package project.gourmetinventoryproject.dto.prato;

import org.springframework.stereotype.Component;
import project.gourmetinventoryproject.domain.Prato;

import java.util.ArrayList;
import java.util.List;


@Component
public class PratoMapper {
    public static Prato toEntity(PratoCriacaoDto dto) {
        Prato prato = new Prato();
        prato.setNome(dto.getNome());
        prato.setDescricao(dto.getDescricao());
        prato.setPreco(dto.getPreco());
        prato.setCategoria(dto.getCategoria());
        return prato;
    }

    public static PratoConsultaDto toDto(Prato prato) {
        PratoConsultaDto dtoConsulta = new PratoConsultaDto(
                prato.getIdPrato(),
                prato.getNome(),
                prato.getDescricao(),
                prato.getPreco(),
                prato.getCategoria()
        );
        return dtoConsulta;
    }
    public static List<PratoConsultaDto> toDto(List<Prato> pratos) {
        List<PratoConsultaDto> listaDtoConsulta = new ArrayList<>();
        for (int i = 0; i < pratos.size(); i++) {
            PratoConsultaDto dtoConsulta = new PratoConsultaDto(
                    pratos.get(i).getIdPrato(),
                    pratos.get(i).getNome(),
                    pratos.get(i).getDescricao(),
                    pratos.get(i).getPreco(),
                    pratos.get(i).getCategoria()
            );
            listaDtoConsulta.add(dtoConsulta);
        }
        return listaDtoConsulta;
    }
}
