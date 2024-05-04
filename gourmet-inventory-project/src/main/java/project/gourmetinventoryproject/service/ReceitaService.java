package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.exception.IdNotFoundException;
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
        if (receitaRepository.existsById(id)){
            Optional<Receita> receitaOptional = receitaRepository.findById(id);
            return receitaOptional.orElse(null);
        }
        throw new IdNotFoundException();

    }

    public Receita createReceita(Receita receita) {
        if (receitaRepository.findByIdIngredienteAndIdPrato(receita.getIdIngrediente(),receita.getIdPrato()).isEmpty()){
            return receitaRepository.save(receita);
        }
        throw new ElementAlreadyExistException();
    }
    public Receita updateReceita(Long id, Receita receita) {
        if (receitaRepository.existsById(id)){
            receita.setId(id);
            return receitaRepository.save(receita);
        }
        throw new IdNotFoundException();

    }

    public void deleteReceita(Long id) {
        if (receitaRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        receitaRepository.deleteById(id);
    }
}

