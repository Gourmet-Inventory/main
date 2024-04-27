package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.Exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.Exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.repository.PratoRepository;
import project.gourmetinventoryproject.repository.ReceitaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PratoService {

    @Autowired
    private PratoRepository pratoRepository;
    private ReceitaRepository receitaRepository;

    public List<Prato> getAllPratos() {
        return pratoRepository.findAll();
    }

    public Prato getPratoById(Long id) {
        if (pratoRepository.existsById(id)){
            Optional<Prato> pratoOptional = pratoRepository.findById(id);
            return pratoOptional.orElse(null);
        }
        throw new IdNotFoundException();

    }

    public Prato createPrato(Prato prato) {
        if (pratoRepository.findByNomeIgnoreCase(prato.getNome()).isEmpty()){
            return pratoRepository.save(prato);
        }
        throw new ElementAlreadyExistException();


    }

    public Prato updatePrato(Long id, Prato prato) {
        if (pratoRepository.existsById(id)){
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
        throw new IdNotFoundException();

    }

    public void deletePrato(Long id) {
        if (pratoRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        pratoRepository.deleteById(id);

    }
}