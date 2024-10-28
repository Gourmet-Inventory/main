package project.gourmetinventoryproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Comanda;
import project.gourmetinventoryproject.repository.ComandaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComandaService {

    private final ComandaRepository comandaRepository;

    public Comanda createComanda(Comanda comanda) {
        return comandaRepository.save(comanda);
    }

    public List<Comanda> getAllComandas() {
        return comandaRepository.findAll();
    }

    public Optional<Comanda> getComandaById(Long id) {
        return comandaRepository.findById(id);
    }

    public Comanda updateComanda(Long id, Comanda updatedComanda) {
        if (!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda not found");
        }
        updatedComanda.setId(id);
        return comandaRepository.save(updatedComanda);
    }

    public void deleteComanda(Long id) {
        if (!comandaRepository.existsById(id)) {
            throw new RuntimeException("Comanda not found");
        }
        comandaRepository.deleteById(id);
    }
}
