package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredientePratosDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstoqueIngredienteService {

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private  AlertaService alertaService;
    @Autowired
    private ModelMapper modelMapper;


    public List<EstoqueIngrediente> getAllEstoqueIngredientes(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        return estoqueIngredienteRepository.findAllByEmpresa(empresa);
    }
    public List<EstoqueIngrediente> getAllEstoqueIngredientes() {
        return estoqueIngredienteRepository.findAll();
    }

    public EstoqueIngrediente getEstoqueIngredienteById(Long id) {
        if (estoqueIngredienteRepository.existsById(id)){
            return estoqueIngredienteRepository.findById(id).orElse(null);
        }
        throw new IdNotFoundException();
    }

    public EstoqueIngrediente createEstoqueIngrediente(EstoqueIngrediente estoqueIngrediente, Long idEmpresa) {
        EstoqueIngrediente estoqueIngrediente1 = verficarTipo(estoqueIngrediente);
        estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        return estoqueIngredienteRepository.save(estoqueIngrediente1);
    }

    public EstoqueIngrediente updateEstoqueIngrediente(Long id, EstoqueIngrediente newEstoqueIngrediente) {
        return estoqueIngredienteRepository.findById(id).map(existingEstoqueIngrediente -> {

            existingEstoqueIngrediente.setLote(newEstoqueIngrediente.getLote());
            existingEstoqueIngrediente.setNome(newEstoqueIngrediente.getNome());
            existingEstoqueIngrediente.setCategoria(newEstoqueIngrediente.getCategoria());
            existingEstoqueIngrediente.setTipoMedida(newEstoqueIngrediente.getTipoMedida());
            existingEstoqueIngrediente.setValorMedida(newEstoqueIngrediente.getValorMedida());
            existingEstoqueIngrediente.setValorTotal(newEstoqueIngrediente.getValorTotal());
            existingEstoqueIngrediente.setLocalArmazenamento(newEstoqueIngrediente.getLocalArmazenamento());
            existingEstoqueIngrediente.setDtaCadastro(newEstoqueIngrediente.getDtaCadastro());
            existingEstoqueIngrediente.setDtaAviso(newEstoqueIngrediente.getDtaAviso());

            existingEstoqueIngrediente.setIdItem(id);

            System.out.println(("Atualizando entidade: {}" + existingEstoqueIngrediente));
            return estoqueIngredienteRepository.save(existingEstoqueIngrediente);
        }).orElseThrow(() -> new IdNotFoundException());

    }

    public EstoqueIngrediente updateEstoqueIngrediente(Long id, EstoqueIngrediente estoqueIngrediente,Long idEmpresa) {
        if (estoqueIngredienteRepository.existsById(id)) {
            EstoqueIngrediente estoqueIngrediente1 = verficarTipo(estoqueIngrediente);
            estoqueIngrediente1.setIdItem(id);
            estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
            alertaService.checarAlerta(estoqueIngrediente1);
            return estoqueIngredienteRepository.save(estoqueIngrediente1);
        }
        throw new IdNotFoundException();
    }

    public void deleteEstoqueIngrediente(Long id) {
        if (estoqueIngredienteRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        estoqueIngredienteRepository.deleteById(id);
    }

    public List<EstoqueIngredientePratosDto> getEIngredientesSelect(Long idEmpresa){
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        if (empresa == null){
            throw new IdNotFoundException();
        }
        List <EstoqueIngrediente> lista= estoqueIngredienteRepository.findAllByEmpresa(empresa);
        return  lista.stream().map(estoqueIngrediente -> modelMapper.map(estoqueIngrediente, EstoqueIngredientePratosDto.class)).collect(Collectors.toList());
    }

    public EstoqueIngrediente verficarTipo(EstoqueIngrediente estoqueIngrediente){
        if(estoqueIngrediente.getUnitario() != null){
            estoqueIngrediente.setValorTotal(estoqueIngrediente.getValorMedida() * estoqueIngrediente.getUnitario());
        }else{
            estoqueIngrediente.setValorTotal(estoqueIngrediente.getValorMedida());
        }
        return estoqueIngrediente;
    }
}

