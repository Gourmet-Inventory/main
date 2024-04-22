package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.repository.ReceitaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public List<Receita> getAllReceitas() {
        return receitaRepository.findAll();
    }

    public Receita getReceitaById(Long id) {
        Optional<Receita> receitaOptional = receitaRepository.findById(id);
        return receitaOptional.orElse(null);
    }

    public Receita createReceita(Receita receita) {
        return receitaRepository.save(receita);
    }

    public Receita updateReceita(Long id, Receita receita) {
        receita.setId(id);
        return receitaRepository.save(receita);
    }

    public void deleteReceita(Long id) {
        receitaRepository.deleteById(id);
    }
}

