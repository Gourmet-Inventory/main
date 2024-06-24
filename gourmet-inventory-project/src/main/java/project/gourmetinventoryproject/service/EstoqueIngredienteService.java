package project.gourmetinventoryproject.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredientePratosSelectDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.util.List;
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

    @Transactional()
    public List<EstoqueIngrediente> getAllEstoqueIngredientes(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        return estoqueIngredienteRepository.findAllByEmpresa(empresa);
    }
    @Transactional()
    public List<EstoqueIngrediente> getAllEstoqueIngredientes() {
        return estoqueIngredienteRepository.findAll();
    }
    @Transactional()
    public EstoqueIngrediente getEstoqueIngredienteById(Long id) {
        if (estoqueIngredienteRepository.existsById(id)){
            return estoqueIngredienteRepository.findById(id).orElse(null);
        }
        throw new IdNotFoundException();
    }
    @Transactional()
    public List<EstoqueIngredientePratosSelectDto> getEIngredientesSelect(Long idEmpresa){
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        if (empresa == null){
            throw new IdNotFoundException();
        }
        List <EstoqueIngrediente> lista= estoqueIngredienteRepository.findAllByEmpresa(empresa);
        return lista.stream().map(estoqueIngrediente -> modelMapper.map(estoqueIngrediente, EstoqueIngredientePratosSelectDto.class)).collect(Collectors.toList());
    }
    @Transactional()
    public EstoqueIngrediente createEstoqueIngrediente(EstoqueIngrediente estoqueIngrediente, Long idEmpresa) {
        EstoqueIngrediente estoqueIngrediente1 = verficarTipo(estoqueIngrediente);
        estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        return estoqueIngredienteRepository.save(estoqueIngrediente1);
    }
    @Transactional()
    public EstoqueIngrediente updateEstoqueIngrediente(Long id, EstoqueIngrediente newEstoqueIngrediente) {
        return estoqueIngredienteRepository.findById(id).map(existingEstoqueIngrediente -> {

            existingEstoqueIngrediente.setLote(newEstoqueIngrediente.getLote());
            existingEstoqueIngrediente.setNome(newEstoqueIngrediente.getNome());
            existingEstoqueIngrediente.setCategoria(newEstoqueIngrediente.getCategoria());
            existingEstoqueIngrediente.setTipoMedida(newEstoqueIngrediente.getTipoMedida());
            existingEstoqueIngrediente.setValorMedida(newEstoqueIngrediente.getValorMedida());
            existingEstoqueIngrediente.setValorTotal(verficarTipo(newEstoqueIngrediente).getValorTotal());
            existingEstoqueIngrediente.setLocalArmazenamento(newEstoqueIngrediente.getLocalArmazenamento());
            existingEstoqueIngrediente.setDtaCadastro(newEstoqueIngrediente.getDtaCadastro());
            existingEstoqueIngrediente.setDtaAviso(newEstoqueIngrediente.getDtaAviso());
            existingEstoqueIngrediente.setIdItem(id);
            return estoqueIngredienteRepository.save(alertaService.checarAlerta(existingEstoqueIngrediente));
        }).orElseThrow(() -> new IdNotFoundException());

    }

    @Transactional()
    public void deleteEstoqueIngrediente(Long id) {
        if (estoqueIngredienteRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        estoqueIngredienteRepository.deleteById(id);
    }


    @Transactional()
    public EstoqueIngrediente verficarTipo(EstoqueIngrediente estoqueIngrediente){
        if(estoqueIngrediente.getUnitario() != null){
            estoqueIngrediente.setValorTotal(estoqueIngrediente.getValorMedida() * estoqueIngrediente.getUnitario());
        }else{
            estoqueIngrediente.setValorTotal(estoqueIngrediente.getValorMedida());
        }
        return estoqueIngrediente;
    }

    @Transactional()
    public void setValorMedidaIfNegativo(Long id) {
        EstoqueIngrediente estoqueIngrediente = estoqueIngredienteRepository.findById(id).orElse(null);
        if (estoqueIngrediente == null){
            throw new IdNotFoundException();
        }
        if (estoqueIngrediente.getValorMedida() <= 0) {
            estoqueIngrediente.setValorMedida(0.0);
            estoqueIngredienteRepository.save(estoqueIngrediente);
        }
    }

}

