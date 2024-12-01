package project.gourmetinventoryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Comanda;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.comanda.ComandaResponseDto;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
import project.gourmetinventoryproject.repository.ComandaRepository;
import project.gourmetinventoryproject.repository.PratoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final PratoRepository pratoRepository;


    public Comanda createComanda(Comanda comanda) {
        comanda.setData(LocalDate.now());
        return comandaRepository.save(comanda);
    }

    public List<Comanda> getAllComandas() {
        return comandaRepository.findAll();
    }
    
    public List<Comanda> getAllComandasFiltradasHoje(String status) {
        return comandaRepository.findAllByStatusAndData(status,LocalDate.now());
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
        ComandaResponseDto comandaResponseDto = mapperRetornoComanda(comanda);
        return comandaResponseDto;
    }

    public ComandaResponseDto updateStatus(Long comandaId, String newStatus) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda not found"));

        System.out.println("Comanda encontrada: " + comanda);
        comanda.setStatus(newStatus);
        comandaRepository.save(comanda);
        System.out.println("Comanda atualizada: " + comanda);
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
        dto.setItens(comanda.getItens().stream().map(
                        prato -> {
                            PratoConsultaDto pratoDto = new PratoConsultaDto();
                            pratoDto.setIdPrato(prato.getIdPrato());
                            pratoDto.setNome(prato.getNome());
                            pratoDto.setDescricao(prato.getDescricao());
                            pratoDto.setPreco(prato.getPreco());
                            pratoDto.setAlergicosRestricoes(prato.getAlergicosRestricoes());
                            pratoDto.setCategoria(prato.getCategoria());
//                            pratoDto.setReceitaPrato((ReceitaConsultaDto) prato.getReceitaPrato().getReceita().stream()
//                                    .map(ingrediente -> {
//                                        IngredienteConsultaDto ingredienteDto = new IngredienteConsultaDto();
//                                        if (ingrediente != null) {
//                                            ingredienteDto.setNome(ingrediente.getNome());
//                                            ingredienteDto.setTipoMedida(ingrediente.getTipoMedida());
//                                            ingredienteDto.setValorMedida(ingrediente.getValorMedida());
//                                        }
//                                        return ingredienteDto;
//                                    })
//                                    .collect(Collectors.toList()));
                            pratoDto.setFoto(prato.getFoto());
                            pratoDto.setURLAssinada(prato.getURLAssinada());
                            return pratoDto;
                        })
                .collect(Collectors.toList()
                ));
        return dto;
    }
}
