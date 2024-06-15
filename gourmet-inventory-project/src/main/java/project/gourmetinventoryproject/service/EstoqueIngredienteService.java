package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.util.List;
import java.util.Optional;

@Service
public class EstoqueIngredienteService {

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;

    @Autowired
    private EmpresaService empresaService;


    public List<EstoqueIngrediente> getAllEstoqueIngredientes(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        return estoqueIngredienteRepository.findAllByEmpresa(empresa);
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

    public void deleteEstoqueIngrediente(Long id) {
        if (estoqueIngredienteRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        estoqueIngredienteRepository.deleteById(id);
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

