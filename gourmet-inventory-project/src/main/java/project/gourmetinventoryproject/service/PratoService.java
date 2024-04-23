package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.repository.PratoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;

    public List<Prato> getAllPratos() {
        return pratoRepository.findAll();
    }

    public Prato getPratoById(Long id) {
        Optional<Prato> pratoOptional = pratoRepository.findById(id);
        return pratoOptional.orElse(null);
    }

    public Prato createPrato(Prato prato) {
        return pratoRepository.save(prato);
    }

    public Prato updatePrato(Long id, Prato prato) {
        Optional<Prato> existingPratoOptional = pratoRepository.findById(id);
        if (existingPratoOptional.isPresent()) {
            Prato existingPrato = existingPratoOptional.get();
            existingPrato.setNome(prato.getNome());
            existingPrato.setDescricao(prato.getDescricao());
            existingPrato.setPreco(prato.getPreco());
            existingPrato.setCategoria(prato.getCategoria());
            return pratoRepository.save(existingPrato);
        } else {
            return null;
        }
    }

    public void deletePrato(Long id) {
        pratoRepository.deleteById(id);
    }
}