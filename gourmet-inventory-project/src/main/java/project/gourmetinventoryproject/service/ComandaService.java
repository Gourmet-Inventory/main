package project.gourmetinventoryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Comanda;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.comanda.ComandaResponseDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.repository.ComandaRepository;
import project.gourmetinventoryproject.repository.PratoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final PratoRepository pratoRepository;

    public Comanda createComanda(Comanda comanda) {
        return comandaRepository.save(comanda);
    }

    public List<Comanda> getAllComandas() {
        return comandaRepository.findAll();
    }

    public Optional<Comanda> getComandaById(Long id) {
        return comandaRepository.findById(id);
    }

    public Comanda getLastComanda() {
        return comandaRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("Comanda not found"));
    }

    public ComandaResponseDto updateComanda(Long id, Comanda updatedComanda) {
        if (!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda not found");
        }
        updatedComanda.setId(id);
        ComandaResponseDto comandaResponseDto = mapperRetornoComanda(updatedComanda);
        return comandaResponseDto;
    }

    public Comanda addPratoToComanda(Long comandaId, Long pratoId) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda not found"));
        Prato prato = pratoRepository.findById(pratoId)
                .orElseThrow(() -> new RuntimeException("Prato not found"));

        comanda.getItens().add(prato);
        comanda.calcularTotal();
        return comandaRepository.save(comanda);
    }

    public ComandaResponseDto removePratoFromComanda(Long comandaId, Long pratoId) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda not found"));
        Prato prato = pratoRepository.findById(pratoId)
                .orElseThrow(() -> new RuntimeException("Prato not found"));

        comanda.getItens().remove(prato);
        comanda.calcularTotal();
        ComandaResponseDto comandaResponseDto = mapperRetornoComanda(comanda); ;
        return comandaResponseDto;
    }

    public ComandaResponseDto updateStatus(Long comandaId, String newStatus) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda not found"));

        comanda.setStatus(newStatus);
        ComandaResponseDto comandaResponseDto = mapperRetornoComanda(comanda);
        return comandaResponseDto;
    }

    public void deleteComanda(Long id) {
        if (!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda not found");
        }
        comandaRepository.deleteById(id);
    }

    public ComandaResponseDto mapperRetornoComanda(Comanda comanda) {
        ComandaResponseDto dto = new ComandaResponseDto();
        dto.setId(comanda.getId());
        dto.setIdGarcom(comanda.getIdGarcom());
        dto.setTitulo(comanda.getTitulo());
        dto.setMesa(comanda.getMesa());
        dto.setStatus(comanda.getStatus());
        dto.setTotal(comanda.getTotal());

        List<PratoConsultaDto> itensDto = comanda.getItens().stream()
                .map(prato -> {
                    PratoConsultaDto pratoDto = new PratoConsultaDto();
                    pratoDto.setIdPrato(prato.getIdPrato());
                    pratoDto.setNome(prato.getNome());
                    pratoDto.setPreco(prato.getPreco());
                    pratoDto.setDescricao(prato.getDescricao());
                    pratoDto.setCategoria(prato.getCategoria());
                    pratoDto.setAlergicosRestricoes(prato.getAlergicosRestricoes());
                    pratoDto.setReceitaPrato(prato.getReceitaPrato().stream()
                            .map(ingrediente -> {
                                IngredienteConsultaDto ingredienteDto = new IngredienteConsultaDto();
                                ingredienteDto.setNome(ingrediente.getEstoqueIngrediente().getNome());
                                ingredienteDto.setTipoMedida(ingrediente.getTipoMedida());
                                ingredienteDto.setValorMedida(ingrediente.getValorMedida());
                                return ingredienteDto;
                            })
                            .collect(Collectors.toList()));
                    pratoDto.setFoto(prato.getFoto());
                    pratoDto.setURLAssinada(prato.getURLAssinada());
                    return pratoDto;
                })
                .collect(Collectors.toList());
        dto.setItens(itensDto);

        return dto;
    }
}
