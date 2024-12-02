package project.gourmetinventoryproject.service;

import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteAtualizacaoDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado.EstoqueIngredienteManipuladoConsultaDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredientePratosSelectDto;
import project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado.EstoqueManipuladoAtualizacao;
import project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado.EstoqueReceitaManipuladoCriacao;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;
import project.gourmetinventoryproject.exception.EmptyListException;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;
import project.gourmetinventoryproject.repository.IngredienteRepository;
import project.gourmetinventoryproject.repository.ReceitaRepository;

import java.util.ArrayList;
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
    private IngredienteService ingredienteService;

    @Autowired
    private IngredienteRepository ingredienteRepository;


    //Lista todos ingrediente por empresa
    @Transactional
    public List<Object> getAllEstoqueIngredientes(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteRepository.findAllByEmpresa(empresa);

        return estoqueIngredientes.stream()
                .map(estoqueIngrediente -> {
                    if (estoqueIngrediente.getManipulado() != null && estoqueIngrediente.getManipulado()) {

                        Receita receita = receitaRepository.findByIdEstoqueIngrediente(estoqueIngrediente.getIdItem()).orElse(null);
                        EstoqueIngredienteManipuladoConsultaDto manipuladoDto = mapperRetorno(estoqueIngrediente,receita);

                        return manipuladoDto;
                    } else {
                        EstoqueIngredienteConsultaDto estoqueIngredienteConsultaDto = modelMapper.map(estoqueIngrediente, EstoqueIngredienteConsultaDto.class);

                        estoqueIngredienteConsultaDto.setCategoria(estoqueIngrediente.getCategoria().getNomeExibicao());
                        estoqueIngredienteConsultaDto.setTipoMedida(estoqueIngrediente.getTipoMedida().getNomeLegivel());
                        return estoqueIngredienteConsultaDto;
                    }
                })
                .collect(Collectors.toList());
    }

    //Traz todos os ingredientes
    @Transactional
    public List<EstoqueIngrediente> getAllEstoqueIngredientes() {
        return estoqueIngredienteRepository.findAll();
    }

    //Traz um estoque ingrediente pelo seu id
    @Transactional
    public EstoqueIngrediente getEstoqueIngredienteById(Long id) {
        return estoqueIngredienteRepository.findById(id)
                .orElseThrow(IdNotFoundException::new);
    }

    //Traz formatado os dados para o select no front
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

    //Cria um Estoque ingrediente Industrializado
    @Transactional
    public EstoqueIngrediente createEstoqueIngrediente(EstoqueIngrediente estoqueIngrediente, Long idEmpresa) {
        EstoqueIngrediente estoqueIngrediente1 = verificarTipo(estoqueIngrediente);
        estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        return estoqueIngredienteRepository.save(estoqueIngrediente1);
    }

    //Cria um Estoque ingrediente Manipulado
    @Transactional
    public EstoqueIngredienteManipuladoConsultaDto createEstoqueIngredienteManipulado(Long idEmpresa, EstoqueReceitaManipuladoCriacao estoqueIngrediente) {
        // Estoque
        EstoqueIngrediente estoqueIngrediente1 = verificarTipo(modelMapper.map(estoqueIngrediente.getEstoqueIngredienteCriacaoDto(),EstoqueIngrediente.class));
        estoqueIngrediente1.setEmpresa(empresaService.getEmpresasById(idEmpresa));
        EstoqueIngrediente estoqueIngrediente2 = estoqueIngredienteRepository.save(estoqueIngrediente1);

        // Receita
        Receita receita1 = new Receita();
        receita1.setIdEstoqueIngrediente(estoqueIngrediente2.getIdItem());
        List<Ingrediente> ingredientes = createIngrediente(estoqueIngrediente.getReceita());
        receita1.setIngredientes(ingredientes);
        Receita receitaId = receitaRepository.save(receita1);

        EstoqueIngredienteManipuladoConsultaDto estoqueManipuladoConsulta = mapperRetorno(estoqueIngrediente2, receita1 );
        return estoqueManipuladoConsulta;
    }


    //Atualizar um Estoque Ingrediente Industrializado
    @Transactional
    public EstoqueIngredienteConsultaDto updateEstoqueIngrediente(Long id, EstoqueIngredienteAtualizacaoDto estoqueIngrediente) {
        EstoqueIngrediente existingEstoqueIngrediente = estoqueIngredienteRepository.findById(id).orElseThrow(IdNotFoundException::new);
        return modelMapper.map(atualizarEstoque(existingEstoqueIngrediente, estoqueIngrediente),EstoqueIngredienteConsultaDto.class);
    }

    //Atualizar um Estoque Ingrediente Manipulado
    @Transactional
    public EstoqueIngredienteManipuladoConsultaDto updateEstoqueIngredienteManipulado(Long id, EstoqueManipuladoAtualizacao estoqueIngredienteManipuladoDto) {
        EstoqueIngrediente existingManipulado = estoqueIngredienteRepository.findById(id).orElseThrow(IdNotFoundException::new);
        List<Ingrediente> listaIngrediente = (estoqueIngredienteManipuladoDto.getReceita() == null) ? null : createIngrediente(estoqueIngredienteManipuladoDto.getReceita());

        Receita receita = receitaRepository.findByIdEstoqueIngrediente(id)
                        .orElse(new Receita());
                receita.setIngredientes(listaIngrediente);
                receita.setIdEstoqueIngrediente(id);
        receitaRepository.save(receita);

        return mapperRetorno(atualizarEstoque(existingManipulado,estoqueIngredienteManipuladoDto.getEstoqueIngredienteAtualizacaoDto()),receita);
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
        dto.setCategoria(estoqueIngrediente.getCategoria().getNomeExibicao());
        dto.setTipoMedida(estoqueIngrediente.getTipoMedida().getNomeLegivel());
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

    public List<Ingrediente> createIngrediente(List<IngredienteCriacaoDto> ingredienteDto) {
        if (ingredienteDto == null) {
            throw new IllegalArgumentException("Lista de ingredientes não pode ser nula");
        }
        if (ingredienteDto.isEmpty()) {
            throw new EmptyListException("Lista de ingredientes vazia");
        }

        List<Ingrediente> lista = new ArrayList<>();
        for (IngredienteCriacaoDto ingrediente : ingredienteDto) {
            EstoqueIngrediente newEstoqueIngrediente = getEstoqueIngredienteById(ingrediente.getIdItem());
            Ingrediente newIngrediente = modelMapper.map(ingrediente, Ingrediente.class);
            newIngrediente.setEstoqueIngrediente(newEstoqueIngrediente);

            ingredienteRepository.save(newIngrediente);
            lista.add(newIngrediente);
        }
        return lista;
    }

    private EstoqueIngrediente atualizarEstoque(EstoqueIngrediente existingEstoqueIngrediente, EstoqueIngredienteAtualizacaoDto estoqueIngrediente ){
            existingEstoqueIngrediente.setNome(estoqueIngrediente.getNome());
            existingEstoqueIngrediente.setLote(estoqueIngrediente.getLote());
            existingEstoqueIngrediente.setMarca(estoqueIngrediente.getMarca());
            existingEstoqueIngrediente.setCategoria(estoqueIngrediente.getCategoria());
            existingEstoqueIngrediente.setTipoMedida(estoqueIngrediente.getTipoMedida());
            existingEstoqueIngrediente.setValorMedida(estoqueIngrediente.getValorMedida());
            existingEstoqueIngrediente.setUnitario(estoqueIngrediente.getUnitario());
            existingEstoqueIngrediente.setDescricao(estoqueIngrediente.getDescricao());
            existingEstoqueIngrediente.setValorTotal(estoqueIngrediente.getValorTotal());
            existingEstoqueIngrediente.setLocalArmazenamento(estoqueIngrediente.getLocalArmazenamento());
            existingEstoqueIngrediente.setDtaCadastro(estoqueIngrediente.getDtaCadastro());
            existingEstoqueIngrediente.setDtaAviso(estoqueIngrediente.getDtaAviso());

            return estoqueIngredienteRepository.save(alertaService.checarAlerta(existingEstoqueIngrediente));
    }
}
