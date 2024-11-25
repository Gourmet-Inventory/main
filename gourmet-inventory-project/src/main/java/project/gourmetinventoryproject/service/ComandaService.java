package project.gourmetinventoryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Comanda;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.repository.ComandaRepository;
import project.gourmetinventoryproject.repository.PratoRepository;

import java.util.List;
import java.util.Optional;

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

    public Comanda updateComanda(Long id, Comanda updatedComanda) {
        if (!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda not found");
        }
        updatedComanda.setId(id);
        return comandaRepository.save(updatedComanda);
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

    public Comanda removePratoFromComanda(Long comandaId, Long pratoId) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda not found"));
        Prato prato = pratoRepository.findById(pratoId)
                .orElseThrow(() -> new RuntimeException("Prato not found"));

        comanda.getItens().remove(prato);
        comanda.calcularTotal();
        return comandaRepository.save(comanda);
    }

    public Comanda updateStatus(Long comandaId, String newStatus) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda not found"));

        comanda.setStatus(newStatus);
        return comandaRepository.save(comanda);
    }

    public void deleteComanda(Long id) {
        if (!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda not found");
        }
        comandaRepository.deleteById(id);
    }
}
