package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Fornecedor;
import project.gourmetinventoryproject.dto.fornecedor.FornecedorCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.FornecedorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class FornecedorService {

    @Autowired
    public FornecedorRepository fornecedorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void postFornecedor(FornecedorCriacaoDto fornecedorCriacaoDto){
        Fornecedor novoFornecedor = modelMapper.map(fornecedorCriacaoDto, Fornecedor.class);
        fornecedorRepository.save(novoFornecedor);
    }

    public List<Fornecedor> getFornecedores(){
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores = fornecedorRepository.findAll();
        return fornecedores;
    }


    public void deleteFornecedor(Long id){
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
        } else {
            throw new IdNotFoundException();
        }
    }

    public void patchFornecedor(Long id, FornecedorCriacaoDto fornecedorCriacaoDto){
        if (fornecedorRepository.existsById(id)) {
            Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);

            if (optionalFornecedor.isPresent()) {
                Fornecedor fornecedor = optionalFornecedor.get();
                modelMapper.map(fornecedorCriacaoDto, fornecedor);
                fornecedorRepository.save(fornecedor);

            }
        } else {
            throw new IdNotFoundException();
        }
    }


}
