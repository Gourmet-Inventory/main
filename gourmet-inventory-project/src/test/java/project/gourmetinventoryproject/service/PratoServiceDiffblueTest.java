package project.gourmetinventoryproject.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;
import project.gourmetinventoryproject.dto.prato.PratoCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.PratoRepository;

@ContextConfiguration(classes = {PratoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PratoServiceDiffblueTest {
    @MockBean
    private EmpresaService empresaService;

    @MockBean
    private IngredienteService ingredienteService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PratoRepository pratoRepository;

    @Autowired
    private PratoService pratoService;

    /**
     * Method under test: {@link PratoService#getAllPratos(Long)}
     */
    @Test
    void testGetAllPratos() {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");
        when(empresaService.getEmpresasById(Mockito.<Long>any())).thenReturn(empresa);
        ArrayList<Prato> pratoList = new ArrayList<>();
        when(pratoRepository.findAllByEmpresa(Mockito.<Empresa>any())).thenReturn(pratoList);

        // Act
        List<Prato> actualAllPratos = pratoService.getAllPratos(1L);

        // Assert
        verify(pratoRepository).findAllByEmpresa(isA(Empresa.class));
        verify(empresaService).getEmpresasById(eq(1L));
        assertTrue(actualAllPratos.isEmpty());
        assertSame(pratoList, actualAllPratos);
    }

    /**
     * Method under test: {@link PratoService#getAllPratos(Long)}
     */
    @Test
    void testGetAllPratos2() {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");
        when(empresaService.getEmpresasById(Mockito.<Long>any())).thenReturn(empresa);
        when(pratoRepository.findAllByEmpresa(Mockito.<Empresa>any())).thenThrow(new IdNotFoundException());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.getAllPratos(1L));
        verify(pratoRepository).findAllByEmpresa(isA(Empresa.class));
        verify(empresaService).getEmpresasById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#getPratoById(Long)}
     */
    @Test
    void testGetPratoById() throws UnsupportedEncodingException {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        Prato actualPratoById = pratoService.getPratoById(1L);

        // Assert
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        assertSame(prato, actualPratoById);
    }

    /**
     * Method under test: {@link PratoService#getPratoById(Long)}
     */
    @Test
    void testGetPratoById2() {
        // Arrange
        when(pratoRepository.findById(Mockito.<Long>any())).thenThrow(new IdNotFoundException());
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.getPratoById(1L));
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#getPratoById(Long)}
     */
    @Test
    void testGetPratoById3() {
        // Arrange
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.getPratoById(1L));
        verify(pratoRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#createPrato(PratoCriacaoDto, Long)}
     */
    @Test
    void testCreatePrato() throws UnsupportedEncodingException {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");
        when(empresaService.getEmpresasById(Mockito.<Long>any())).thenReturn(empresa);
        when(ingredienteService.createIngrediente(Mockito.<List<IngredienteCriacaoDto>>any()))
                .thenReturn(new ArrayList<>());

        Empresa empresa2 = new Empresa();
        empresa2.setCnpj("Cnpj");
        empresa2.setIdEmpresa(1L);
        empresa2.setNomeFantasia("Nome Fantasia");
        empresa2.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa2);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Prato>>any())).thenReturn(prato);
        when(pratoRepository.save(Mockito.<Prato>any())).thenThrow(new IdNotFoundException());

        PratoCriacaoDto prato2 = new PratoCriacaoDto();
        prato2.setAlergicosRestricoes(new ArrayList<>());
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);
        prato2.setReceitaPrato(new ArrayList<>());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.createPrato(prato2, 1L));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(pratoRepository).save(isA(Prato.class));
        verify(empresaService).getEmpresasById(eq(1L));
        verify(ingredienteService).createIngrediente(isA(List.class));
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, PratoCriacaoDto)}
     */
    @Test
    void testUpdatePrato() throws UnsupportedEncodingException {
        // Arrange
        when(ingredienteService.createIngrediente(Mockito.<List<IngredienteCriacaoDto>>any()))
                .thenReturn(new ArrayList<>());

        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);

        Empresa empresa2 = new Empresa();
        empresa2.setCnpj("Cnpj");
        empresa2.setIdEmpresa(1L);
        empresa2.setNomeFantasia("Nome Fantasia");
        empresa2.setTelefone("Telefone");

        Prato prato2 = new Prato();
        prato2.setAlergicosRestricoes(new ArrayList<>());
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setEmpresa(empresa2);
        prato2.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato2.setIdPrato(1L);
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);
        prato2.setReceitaPrato(new ArrayList<>());
        when(pratoRepository.save(Mockito.<Prato>any())).thenReturn(prato2);
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        PratoCriacaoDto prato3 = new PratoCriacaoDto();
        prato3.setAlergicosRestricoes(new ArrayList<>());
        prato3.setCategoria("Categoria");
        prato3.setDescricao("Descricao");
        prato3.setNome("Nome");
        prato3.setPreco(10.0d);
        prato3.setReceitaPrato(new ArrayList<>());

        // Act
        Prato actualUpdatePratoResult = pratoService.updatePrato(1L, prato3);

        // Assert
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        verify(pratoRepository).save(isA(Prato.class));
        verify(ingredienteService).createIngrediente(isA(List.class));
        assertSame(prato2, actualUpdatePratoResult);
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, PratoCriacaoDto)}
     */
    @Test
    void testUpdatePrato2() throws UnsupportedEncodingException {
        // Arrange
        when(ingredienteService.createIngrediente(Mockito.<List<IngredienteCriacaoDto>>any()))
                .thenReturn(new ArrayList<>());

        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);
        when(pratoRepository.save(Mockito.<Prato>any())).thenThrow(new IdNotFoundException());
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        PratoCriacaoDto prato2 = new PratoCriacaoDto();
        prato2.setAlergicosRestricoes(new ArrayList<>());
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);
        prato2.setReceitaPrato(new ArrayList<>());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePrato(1L, prato2));
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        verify(pratoRepository).save(isA(Prato.class));
        verify(ingredienteService).createIngrediente(isA(List.class));
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, PratoCriacaoDto)}
     */
    @Test
    void testUpdatePrato3() {
        // Arrange
        Optional<Prato> emptyResult = Optional.empty();
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        PratoCriacaoDto prato = new PratoCriacaoDto();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePrato(1L, prato));
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#deletePrato(Long)}
     */
    @Test
    void testDeletePrato() throws UnsupportedEncodingException {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);
        doNothing().when(pratoRepository).deleteById(Mockito.<Long>any());
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        pratoService.deletePrato(1L);

        // Assert that nothing has changed
        verify(pratoRepository).deleteById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#deletePrato(Long)}
     */
    @Test
    void testDeletePrato2() throws UnsupportedEncodingException {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);
        doThrow(new IdNotFoundException()).when(pratoRepository).deleteById(Mockito.<Long>any());
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.deletePrato(1L));
        verify(pratoRepository).deleteById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#deletePrato(Long)}
     */
    @Test
    void testDeletePrato3() {
        // Arrange
        Optional<Prato> emptyResult = Optional.empty();
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.deletePrato(1L));
        verify(pratoRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link PratoService#generateExcelReport(List, int, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateExcelReport() throws IOException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access files (file '\directory\foo.txt', permission 'write').
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // Arrange and Act
        pratoService.generateExcelReport(new ArrayList<>(), 10, "/directory/foo.txt");
    }

    /**
     * Method under test: {@link PratoService#updatePratoFoto(Long, byte[])}
     */
    @Test
    void testUpdatePratoFoto() throws UnsupportedEncodingException {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);

        Empresa empresa2 = new Empresa();
        empresa2.setCnpj("Cnpj");
        empresa2.setIdEmpresa(1L);
        empresa2.setNomeFantasia("Nome Fantasia");
        empresa2.setTelefone("Telefone");

        Prato prato2 = new Prato();
        prato2.setAlergicosRestricoes(new ArrayList<>());
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setEmpresa(empresa2);
        prato2.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato2.setIdPrato(1L);
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);
        prato2.setReceitaPrato(new ArrayList<>());
        when(pratoRepository.save(Mockito.<Prato>any())).thenReturn(prato2);
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        Prato actualUpdatePratoFotoResult = pratoService.updatePratoFoto(1L, "AXAXAXAX".getBytes("UTF-8"));

        // Assert
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        verify(pratoRepository).save(isA(Prato.class));
        assertSame(prato, actualUpdatePratoFotoResult);
        byte[] expectedFoto = "AXAXAXAX".getBytes("UTF-8");
        assertArrayEquals(expectedFoto, actualUpdatePratoFotoResult.getFoto());
    }

    /**
     * Method under test: {@link PratoService#updatePratoFoto(Long, byte[])}
     */
    @Test
    void testUpdatePratoFoto2() throws UnsupportedEncodingException {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setCnpj("Cnpj");
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Nome Fantasia");
        empresa.setTelefone("Telefone");

        Prato prato = new Prato();
        prato.setAlergicosRestricoes(new ArrayList<>());
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setEmpresa(empresa);
        prato.setFoto("AXAXAXAX".getBytes("UTF-8"));
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        prato.setReceitaPrato(new ArrayList<>());
        Optional<Prato> ofResult = Optional.of(prato);
        when(pratoRepository.save(Mockito.<Prato>any())).thenThrow(new IdNotFoundException());
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePratoFoto(1L, "AXAXAXAX".getBytes("UTF-8")));
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        verify(pratoRepository).save(isA(Prato.class));
    }

    /**
     * Method under test: {@link PratoService#updatePratoFoto(Long, byte[])}
     */
    @Test
    void testUpdatePratoFoto3() throws UnsupportedEncodingException {
        // Arrange
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePratoFoto(1L, "AXAXAXAX".getBytes("UTF-8")));
        verify(pratoRepository).existsById(eq(1L));
    }
}
