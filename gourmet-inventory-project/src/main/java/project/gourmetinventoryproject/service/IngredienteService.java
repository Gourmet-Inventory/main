package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.exception.EmptyListException;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.repository.IngredienteRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;


    public List<Ingrediente> getAllIngredientes() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente getIngredienteById(Long id) {
        if (ingredienteRepository.existsById(id)) {
            Ingrediente ingredienteOptional = ingredienteRepository.findById(id).orElse(null);
            return ingredienteOptional;
        }
        throw new IdNotFoundException();
    }



    public void deleteIngrediente(Long id) {
        if (ingredienteRepository.findById(id).orElse(null) == null) {
            throw new IdNotFoundException();
        }
        ingredienteRepository.deleteById(id);
    }

    public ReceitaConsultaDto mapReceitaToDto(Receita receita) {
        ReceitaConsultaDto receitaDto = new ReceitaConsultaDto();
        receitaDto.setIdReceita(receita.getIdReceita());

        List<IngredienteConsultaDto> ingredientes = receita.getIngredientes().stream()
                .map(ingrediente -> {
                    IngredienteConsultaDto ingredienteDto = new IngredienteConsultaDto();
                    ingredienteDto.setNome(ingrediente.getEstoqueIngrediente().getNome());
                    ingredienteDto.setValorMedida(ingrediente.getValorMedida());
                    ingredienteDto.setTipoMedida(ingrediente.getTipoMedida());
                    return ingredienteDto;
                })
                .collect(Collectors.toList());

        receitaDto.setReceita(ingredientes);
        return receitaDto;
    }
}
