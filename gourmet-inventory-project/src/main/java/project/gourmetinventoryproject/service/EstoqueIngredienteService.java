package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return estoqueIngredienteRepository.findById(id).orElse(null);
    }

    public EstoqueIngrediente createEstoqueIngrediente(EstoqueIngrediente estoqueIngrediente) {
        return estoqueIngredienteRepository.save(estoqueIngrediente);
    }

    public EstoqueIngrediente updateEstoqueIngrediente(Long id, EstoqueIngrediente estoqueIngrediente) {
        estoqueIngrediente.setIdItem(id);
        return estoqueIngredienteRepository.save(estoqueIngrediente);
    }

    public void deleteEstoqueIngrediente(Long id) {
        estoqueIngredienteRepository.deleteById(id);
    }
}

