package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.repository.IngredienteRepository;

import java.util.List;

import java.util.Optional;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<Ingrediente> getAllIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente getIngredienteById(Long id) {
       if (ingredienteRepository.existsById(id)){
           Optional<Ingrediente> ingredienteOptional = ingredienteRepository.findById(id);
           return ingredienteOptional.orElse(null);
       }
       throw new IdNotFoundException();
    }

    public Ingrediente createIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public Ingrediente updateIngrediente(Long id, Ingrediente ingrediente) {
        if (ingredienteRepository.existsById(id)){
            ingrediente.setIdIngrediente(id);
            return ingredienteRepository.save(ingrediente);
        }
        throw new IdNotFoundException();
    }

    public void deleteIngrediente(Long id) {
        if (ingredienteRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        ingredienteRepository.deleteById(id);
    }
}
