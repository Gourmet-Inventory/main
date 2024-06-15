package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
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

    private ModelMapper mapper = new ModelMapper();

    public List<Ingrediente> getAllIngredientes() {
        return ingredienteRepository.findAll();
    }

    public IngredienteConsultaDto getIngredienteById(Long id) {
       if (ingredienteRepository.existsById(id)){
           Optional<Ingrediente> ingredienteOptional = ingredienteRepository.findById(id);

           return mapper.map(ingredienteOptional.orElse(null), IngredienteConsultaDto.class);
       }
       throw new IdNotFoundException();
    }

    public List<Ingrediente> createIngrediente(List<IngredienteCriacaoDto> ingredienteDto) {
        if (ingredienteDto.isEmpty()){
            throw new ElementAlreadyExistException();
        }
        List<Ingrediente> lista = new ArrayList<>();
        for (IngredienteCriacaoDto ingrediente : ingredienteDto){
            Ingrediente ingrediente1 =  modelMapper.map(ingrediente,Ingrediente.class);
            ingredienteRepository.save(ingrediente1);
            lista.add(ingrediente1);
        }
        return lista;
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
