//package project.gourmetinventoryproject.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import jakarta.persistence.Id;
//import project.gourmetinventoryproject.domain.*;
//import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;
//import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteCriacaoDto;
//import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
//import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;
//import project.gourmetinventoryproject.dto.prato.PratoCriacaoDto;
//import project.gourmetinventoryproject.exception.IdNotFoundException;
//import project.gourmetinventoryproject.repository.EmpresaRepository;
//import project.gourmetinventoryproject.repository.PratoRepository;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PratoServiceTest {
//
//    @Mock
//    private PratoRepository pratoRepository;
//
//    @InjectMocks
//    private PratoService pratoService;
//
//    @Mock
//    private EmpresaRepository empresaRepository;
//
//    private Empresa empresa;
//    private Prato prato;
//    private Ingrediente ingrediente;
//    private EstoqueIngrediente estoqueIngrediente;
//    private EstoqueIngredienteCriacaoDto estoqueIngredienteCriacaoDto;
//    private IngredienteCriacaoDto ingredienteCriacaoDto;
//    private PratoCriacaoDto pratoCriacaoDto;
//    private EmpresaCriacaoDto empresaCriacaoDto;
//
//    @BeforeEach
//    void setUp() {
//        estoqueIngredienteCriacaoDto = EstoqueIngredienteCriacaoDto.builder()
//                .lote("Lote Teste")
//                .manipulado(false)
//                .nome("Nome Teste")
//                .categoria("Categoria Teste")
//                .tipoMedida(Medidas.LITRO)
//                .unitario(1)
//                .valorMedida(1.0)
//                .localArmazenamento("Local Teste")
//                .dtaCadastro(LocalDate.of(2023, 1, 1))
//                .dtaAviso(LocalDate.of(2023, 12, 10))
//                .build();
//
//        estoqueIngrediente = EstoqueIngrediente.builder()
//                .idItem(1L)
//                .lote("Lote Teste")
//                .manipulado(false)
//                .nome("Nome Teste")
//                .categoria("Categoria Teste")
//                .tipoMedida(Medidas.LITRO)
//                .unitario(1)
//                .valorMedida(1.0)
//                .localArmazenamento("Local Teste")
//                .dtaCadastro(LocalDate.of(2023, 1, 1))
//                .dtaAviso(LocalDate.of(2023, 12, 10))
//                .build();
//
//        ingrediente = Ingrediente.builder()
//                .idIngrediente(1L)
//                .estoqueIngrediente(estoqueIngrediente)
//                .tipoMedida(Medidas.LITRO)
//                .valorMedida(1.0)
//                .build();
//
//        ingredienteCriacaoDto = IngredienteCriacaoDto.builder()
//                .idItem(1L)
//                .tipoMedida(Medidas.LITRO)
//                .valorMedida(1.0)
//                .build();
//
//        empresa = Empresa.builder()
//                .idEmpresa(1L)
//                .nomeFantasia("Empresa Teste")
//                .cnpj("12345678901234")
//                .build();
//
//        empresaCriacaoDto =empresaCriacaoDto.builder()
//                .nomeFantasia("Empresa Teste")
//                .cnpj("62.012.035/0001-06")
//                .telefone("(11) 99999-9999")
//                .build();
//
//        prato = Prato.builder()
//                .idPrato(1L)
//                .nome("Prato Teste")
//                .descricao("Descrição do prato teste")
//                .preco(10.0)
//                .categoria("Categoria Teste")
//                .empresa(empresa)
//                .build();
//
//        pratoCriacaoDto = PratoCriacaoDto.builder()
//                .nome("Prato Teste")
//                .descricao("Descrição do prato teste")
//                .preco(10.0)
//                .categoria("Categoria Teste")
//                .receitaPrato(List.of(ingredienteCriacaoDto))
//                .build();
//    }
//
//    //-------------------------------------------------------------------------------------------------------------
//
//    @Test
//    public void createPratoReturnsPratoConsultaDto() {
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        when(modelMapper.map(pratoCriacaoDto, Prato.class)).thenReturn(prato);
//        when(empresaService.getEmpresasById(empresa.getIdEmpresa())).thenReturn(empresa);
//        when(pratoRepository.save(Mockito.any(Prato.class))).thenReturn(prato);
//        when(modelMapper.map(prato, PratoConsultaDto.class)).thenReturn(new PratoConsultaDto());
//
//        PratoConsultaDto savedPrato = pratoService.createPrato(pratoCriacaoDto, empresa.getIdEmpresa());
//
//        assertThat(savedPrato).isNotNull();
//    }
//
//    @Test
//    public void createPratoReturnsExeption() {
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        // when(empresaRepository.findById(empresa.getIdEmpresa())).thenReturn(null).thenThrow(new IdNotFoundException());
//        when(empresaService.getEmpresasById(empresa.getIdEmpresa())).thenThrow(new IdNotFoundException());
//
//        assertThrows(IdNotFoundException.class, () -> pratoService.createPrato(pratoCriacaoDto, empresa.getIdEmpresa()));
//    }
//
//    @Test
//    public void getAllPratosRetunPratosList() {
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        when(empresaService.getEmpresasById(empresa.getIdEmpresa())).thenReturn(empresa);
//        when(pratoRepository.findAllByEmpresa(empresa)).thenReturn(Arrays.asList(prato));
//
//        Empresa empresaId = Empresa.builder().idEmpresa(1L).build();
//
//        List<Prato> pratoRetun = pratoService.getAllPratos(empresaId.getIdEmpresa());
//
//        assertThat(pratoRetun).isNotNull();
//    }
//
//    @Test
//    public void getPratoByIdReturnPrato() {
//        Long pratoId = 1L;
//
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        when(pratoRepository.existsById(pratoId)).thenReturn(true);
//        when(pratoRepository.findById(pratoId)).thenReturn(Optional.of(prato));
//
//        Prato pratoRetun = pratoService.getPratoById(prato.getIdPrato());
//
//        assertThat(pratoRetun).isNotNull();
//    }
//
//    @Test
//    public void getPratoByIdReturnExeption() {
//        Long pratoId = 1L;
//
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        when(pratoRepository.existsById(pratoId)).thenReturn(false);
//
//        assertThrows(IdNotFoundException.class, () -> pratoService.getPratoById(pratoId));
//    }
//
//    @Test
//    public void updatePratoReturnPrato() {
//        Long pratoId = 1L;
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        when(pratoRepository.existsById(pratoId)).thenReturn(true);
//        when(pratoRepository.findById(pratoId)).thenReturn(Optional.of(prato));
//        when(pratoRepository.save(prato)).thenReturn(prato);
//
//        Prato updateReturn = pratoService.updatePrato(pratoId,  pratoCriacaoDto);
//
//        assertThat(updateReturn).isNotNull();
//    }
//
//    @Test
//    public void deltePratoById() {
//        Long pratoId = 1L;
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//
//        when(pratoRepository.findById(pratoId)).thenReturn(Optional.of(prato));
//        // Primeiro true para antes da deleção, depois false para simular que o prato foi deletado
//        when(pratoRepository.existsById(pratoId)).thenReturn(true, false);
//
//        // Verificando se o prato existe
//        assertTrue(pratoRepository.existsById(pratoId));
//        // Deletando o prato
//        pratoService.deletePrato(pratoId);
//        // Verificando se o prato foi deletado
//        assertFalse(pratoRepository.existsById(pratoId));
//    }
//
//    @Test
//    public void deletePratoThrowsExceptionWhenNotFound() {
//        Long pratoIdInvalido = 2L; // Assumindo que este ID é inválido ou o prato não existe
//        Long pratoId = 1L;
//        EmpresaService empresaService = mock(EmpresaService.class);
//        pratoService.setEmpresaService(empresaService);
//        IngredienteService ingredienteService = mock(IngredienteService.class);
//        pratoService.setIngredienteService(ingredienteService);
//        ModelMapper modelMapper = mock(ModelMapper.class);
//        pratoService.setModelMapper(modelMapper);
//        PratoService pratoServiceM = mock(PratoService.class);
//
//        assertThrows(IdNotFoundException.class, () -> pratoService.deletePrato(pratoId));
//    }
//
//    @Test
//    void updatePratoFotoUpdatesPhotoWhenSuccessful() {
//        byte[] novaFoto = new byte[]{1, 2, 3};
//        Prato existingPrato = new Prato();
//        existingPrato.setIdPrato(1L);
//        existingPrato.setFoto(new byte[]{});
//        when(pratoRepository.existsById(1L)).thenReturn(true);
//        when(pratoRepository.findById(1L)).thenReturn(Optional.of(existingPrato));
//        when(pratoRepository.save(any(Prato.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        Prato result = pratoService.updatePratoFoto(1L, novaFoto);
//
//        assertArrayEquals(novaFoto, result.getFoto());
//    }
//}
