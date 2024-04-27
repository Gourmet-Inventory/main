package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.Exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.util.List;

@Service
public class EstoqueIngredienteService {

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;

    public List<EstoqueIngrediente> getAllEstoqueIngredientes() {
        return estoqueIngredienteRepository.findAll();
    }

    public EstoqueIngrediente getEstoqueIngredienteById(Long id) {
        if (estoqueIngredienteRepository.existsById(id)){
            return estoqueIngredienteRepository.findById(id).orElse(null);
        }
        throw new IdNotFoundException();
    }

    public EstoqueIngrediente createEstoqueIngrediente(EstoqueIngrediente estoqueIngrediente) {
        return estoqueIngredienteRepository.save(estoqueIngrediente);
    }

    public EstoqueIngrediente updateEstoqueIngrediente(Long id, EstoqueIngrediente estoqueIngrediente) {
        if (estoqueIngredienteRepository.existsById(id)){
            estoqueIngrediente.setIdItem(id);
            return estoqueIngredienteRepository.save(estoqueIngrediente);
        }
        throw new IdNotFoundException();
    }

    public void deleteEstoqueIngrediente(Long id) {
        if (estoqueIngredienteRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        estoqueIngredienteRepository.deleteById(id);
    }
}

