package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteManipuladoConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredientePratosSelectDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueReceitaManipuladoCriacao;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;
import project.gourmetinventoryproject.dto.receita.ReceitaCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;
import project.gourmetinventoryproject.repository.ReceitaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstoqueIngredienteService {

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    @Lazy
    private IngredienteService ingredienteService;

    @Transactional
    public List<Object> getAllEstoqueIngredientes(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteRepository.findAllByEmpresa(empresa);

        return estoqueIngredientes.stream()
                .map(estoqueIngrediente -> {
                    if (estoqueIngrediente.getManipulado() != null && estoqueIngrediente.getManipulado()) {
                        EstoqueIngredienteManipuladoConsultaDto manipuladoDto = modelMapper.map(estoqueIngrediente, EstoqueIngredienteManipuladoConsultaDto.class);

                        Optional<Receita> receita = receitaRepository.findByIdEstoqueIngrediente(estoqueIngrediente.getIdItem());
                        receita.ifPresent(r -> {
                            ReceitaConsultaDto receitaDto = ingredienteService.mapReceitaToDto(r);
                            manipuladoDto.setReceita(receitaDto);
                        });

                        return manipuladoDto;
                    } else {
                        return modelMapper.map(estoqueIngrediente, EstoqueIngredienteConsultaDto.class);
                    }
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public List<EstoqueIngrediente> getAllEstoqueIngredientes() {
        return estoqueIngredienteRepository.findAll();
    }

    @Transactional
    public EstoqueIngrediente getEstoqueIngredienteById(Long id) {
        return estoqueIngredienteRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);
    }

    @Transactional
    public List<EstoqueIngredientePratosSelectDto> getEIngredientesSelect(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        if (empresa == null) {
            throw new IdNotFoundException();
        }
        List<EstoqueIngrediente> lista = estoqueIngredienteRepository.findAllByEmpresa(empresa);
        return lista.stream()
                .map(estoqueIngrediente -> modelMapper.map(estoqueIngrediente, EstoqueIngredientePratosSelectDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public EstoqueIngrediente createEstoqueIngrediente(EstoqueIngrediente estoqueIngrediente, Long idEmpresa) {
        EstoqueIngrediente estoqueIngrediente1 = verificarTipo(estoqueIngrediente);
        estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        return estoqueIngredienteRepository.save(estoqueIngrediente1);
    }

    @Transactional
    public EstoqueIngredienteManipuladoConsultaDto createEstoqueIngredienteManipulado(EstoqueIngrediente estoqueIngrediente, Long idEmpresa, ReceitaCriacaoDto receita) {
        // Estoque
        EstoqueIngrediente estoqueIngrediente1 = verificarTipo(estoqueIngrediente);
        estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        EstoqueIngrediente estoqueIngrediente2 = estoqueIngredienteRepository.save(estoqueIngrediente1);

        // Receita
        Receita receita1 = new Receita();
        receita1.setIdEstoqueIngrediente(estoqueIngrediente2.getIdItem());
        List<Ingrediente> ingredientes = ingredienteService.createIngrediente(receita.getReceita());
        receita1.setIngredientes(ingredientes);
        Receita receitaId = receitaRepository.save(receita1);

        EstoqueIngredienteManipuladoConsultaDto estoqueManipuladoConsulta = mapperRetorno(estoqueIngrediente,receitaId);
        return estoqueManipuladoConsulta;
    }

    @Transactional
    public EstoqueIngrediente updateEstoqueIngrediente(Long id, EstoqueIngrediente estoqueIngrediente) {
        return estoqueIngredienteRepository.findById(id).map(existingEstoqueIngrediente -> {
            verificarTipo(estoqueIngrediente);

            existingEstoqueIngrediente.setLote(estoqueIngrediente.getLote());
            existingEstoqueIngrediente.setNome(estoqueIngrediente.getNome());
            existingEstoqueIngrediente.setCategoria(estoqueIngrediente.getCategoria());
            existingEstoqueIngrediente.setTipoMedida(estoqueIngrediente.getTipoMedida());
            existingEstoqueIngrediente.setValorMedida(estoqueIngrediente.getValorMedida());
            existingEstoqueIngrediente.setUnitario(estoqueIngrediente.getUnitario());
            existingEstoqueIngrediente.setValorTotal(estoqueIngrediente.getValorTotal());
            existingEstoqueIngrediente.setLocalArmazenamento(estoqueIngrediente.getLocalArmazenamento());
            existingEstoqueIngrediente.setDtaCadastro(estoqueIngrediente.getDtaCadastro());
            existingEstoqueIngrediente.setDtaAviso(estoqueIngrediente.getDtaAviso());

            return estoqueIngredienteRepository.save(alertaService.checarAlerta(existingEstoqueIngrediente));
        }).orElseThrow(IdNotFoundException::new);
    }

    @Transactional
    public EstoqueIngredienteManipuladoConsultaDto updateEstoqueIngredienteManipulado(Long id, EstoqueReceitaManipuladoCriacao estoqueIngredienteManipuladoDto) {
        return estoqueIngredienteRepository.findById(id).map(existingManipulado -> {

            EstoqueIngrediente estoqueIngrediente = verificarTipo(modelMapper.map(estoqueIngredienteManipuladoDto.getEstoqueIngredienteCriacaoDto(), EstoqueIngrediente.class));

            existingManipulado.setLote(estoqueIngrediente.getLote());
            existingManipulado.setNome(estoqueIngrediente.getNome());
            existingManipulado.setCategoria(estoqueIngrediente.getCategoria());
            existingManipulado.setTipoMedida(estoqueIngrediente.getTipoMedida());
            existingManipulado.setValorMedida(estoqueIngrediente.getValorMedida());
            existingManipulado.setUnitario(estoqueIngrediente.getUnitario());
            existingManipulado.setValorTotal(estoqueIngrediente.getValorTotal());
            existingManipulado.setLocalArmazenamento(estoqueIngrediente.getLocalArmazenamento());
            existingManipulado.setDtaCadastro(estoqueIngrediente.getDtaCadastro());
            existingManipulado.setDtaAviso(estoqueIngrediente.getDtaAviso());

            ReceitaCriacaoDto receitaDto = estoqueIngredienteManipuladoDto.getReceitaCriacaoDto();
            if (receitaDto != null) {
                Receita receita = receitaRepository.findByIdEstoqueIngrediente(id)
                        .orElse(new Receita());
                receita.setIngredientes(ingredienteService.createIngrediente(receitaDto.getReceita()));
                receita.setIdEstoqueIngrediente(id);
                receitaRepository.save(receita);
            }

            EstoqueIngrediente updatedEstoqueIngrediente = estoqueIngredienteRepository.save(alertaService.checarAlerta(existingManipulado));
            return modelMapper.map(updatedEstoqueIngrediente, EstoqueIngredienteManipuladoConsultaDto.class);
        }).orElseThrow(IdNotFoundException::new);
    }



    @Transactional
    public void deleteEstoqueIngrediente(Long id) {
        if (!estoqueIngredienteRepository.existsById(id)) {
            throw new IdNotFoundException();
        }
        estoqueIngredienteRepository.deleteById(id);
    }

    @Transactional
    public EstoqueIngrediente verificarTipo(EstoqueIngrediente estoqueIngrediente) {
        if (estoqueIngrediente.getUnitario() != null) {
            estoqueIngrediente.setValorTotal(estoqueIngrediente.getValorMedida() * estoqueIngrediente.getUnitario());
        } else {
            estoqueIngrediente.setValorTotal(estoqueIngrediente.getValorMedida());
        }
        return estoqueIngrediente;
    }

    @Transactional
    public void setValorMedidaIfNegativo(Long id) {
        EstoqueIngrediente estoqueIngrediente = estoqueIngredienteRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);

        if (estoqueIngrediente.getValorMedida() <= 0) {
            estoqueIngrediente.setValorMedida(0.0);
            estoqueIngredienteRepository.save(estoqueIngrediente);
        }
    }

    public List<EstoqueIngrediente> getAllEstoqueIngredienteByMonth(Long idEmpresa, int mes) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        return estoqueIngredienteRepository.findAllByDtaAvisoMonth(mes, empresa);
    }

    public EstoqueIngredienteManipuladoConsultaDto mapperRetorno(EstoqueIngrediente estoqueIngrediente, Receita receita ){
        EstoqueIngredienteManipuladoConsultaDto dto = new EstoqueIngredienteManipuladoConsultaDto();
        dto.setIdItem(estoqueIngrediente.getIdItem());
        dto.setManipulado(estoqueIngrediente.getManipulado());
        dto.setNome(estoqueIngrediente.getNome());
        dto.setLote(estoqueIngrediente.getLote());
        dto.setCategoria(estoqueIngrediente.getCategoria());
        dto.setTipoMedida(estoqueIngrediente.getTipoMedida());
        dto.setUnitario(estoqueIngrediente.getUnitario());
        dto.setValorMedida(estoqueIngrediente.getValorMedida());
        dto.setValorTotal(estoqueIngrediente.getValorTotal());
        dto.setLocalArmazenamento(estoqueIngrediente.getLocalArmazenamento());
        dto.setDtaCadastro(estoqueIngrediente.getDtaCadastro());
        dto.setDtaAviso(estoqueIngrediente.getDtaAviso());
        dto.setDescricao(estoqueIngrediente.getDescricao());
        dto.setAlertas(estoqueIngrediente.getAlertas());

        ReceitaConsultaDto receitaDto = ingredienteService.mapReceitaToDto(receita);
        dto.setReceita(receitaDto);

        return dto;
    }
}
