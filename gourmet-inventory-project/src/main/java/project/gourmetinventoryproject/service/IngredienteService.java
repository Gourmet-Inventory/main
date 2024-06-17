package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.exception.EmptyListException;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.repository.IngredienteRepository;

import java.util.*;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    private ModelMapper mapper = new ModelMapper();

    public List<Ingrediente> getAllIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente getIngredienteById(Long id) {
       if (ingredienteRepository.existsById(id)){
           Ingrediente ingredienteOptional = ingredienteRepository.findById(id).orElse(null);
           return ingredienteOptional;
       }
       throw new IdNotFoundException();
    }

    public List<Ingrediente> createIngrediente(List<IngredienteCriacaoDto> ingredienteDto) {
        if (ingredienteDto == null) {
            throw new IllegalArgumentException("Lista de ingredientes não pode ser nula");
        }
        if (ingredienteDto.isEmpty()) {
            throw new EmptyListException("Lista de ingredientes vazia");
        }

        List<Ingrediente> lista = new ArrayList<>();
        for (IngredienteCriacaoDto ingrediente : ingredienteDto) {
            EstoqueIngrediente newEstoqueIngrediente = estoqueIngredienteService.getEstoqueIngredienteById(ingrediente.getIdItem());
            Ingrediente newIngrediente = modelMapper.map(ingrediente, Ingrediente.class);
            newIngrediente.setEstoqueIngrediente(newEstoqueIngrediente);

            ingredienteRepository.save(newIngrediente);
            lista.add(newIngrediente);
        }
        return lista;
    }

    public void deleteIngrediente(Long id) {
        if (ingredienteRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        ingredienteRepository.deleteById(id);
    }
}
