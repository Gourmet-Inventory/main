package project.gourmetinventoryproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.IngredienteRepository;

@ContextConfiguration(classes = {IngredienteService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class IngredienteServiceDiffblueTest {
    @MockBean
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private IngredienteService ingredienteService;

    /**
     * Method under test: {@link IngredienteService#getAllIngredientes()}
     */
    @Test
    void testGetAllIngredientes() {
        // Arrange
        ArrayList<Ingrediente> ingredienteList = new ArrayList<>();
        when(ingredienteRepository.findAll()).thenReturn(ingredienteList);

        // Act
        List<Ingrediente> actualAllIngredientes = ingredienteService.getAllIngredientes();

        // Assert
        verify(ingredienteRepository).findAll();
        assertTrue(actualAllIngredientes.isEmpty());
        assertSame(ingredienteList, actualAllIngredientes);
    }

    /**
     * Method under test: {@link IngredienteService#getAllIngredientes()}
     */
    @Test
    void testGetAllIngredientes2() {
        // Arrange
        when(ingredienteRepository.findAll()).thenThrow(new IdNotFoundException());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.getAllIngredientes());
        verify(ingredienteRepository).findAll();
    }

    /**
     * Method under test: {@link IngredienteService#getIngredienteById(Long)}
     */
    @Test
    void testGetIngredienteById() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        Optional<Ingrediente> ofResult = Optional.of(ingrediente);
        when(ingredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        IngredienteConsultaDto actualIngredienteById = ingredienteService.getIngredienteById(1L);

        // Assert
        verify(ingredienteRepository).existsById(eq(1L));
        verify(ingredienteRepository).findById(eq(1L));
        assertEquals("Categoria", actualIngredienteById.getEstoqueIngrediente().getCategoria());
        assertEquals("Valor Medida", actualIngredienteById.getValorMedida());
        assertEquals(1L, actualIngredienteById.getIdIngrediente().longValue());
        assertEquals(Medidas.COLHER_DE_SOPA, actualIngredienteById.getTipoMedida());
    }

    /**
     * Method under test: {@link IngredienteService#getIngredienteById(Long)}
     */
    @Test
    void testGetIngredienteById2() {
        // Arrange
        when(ingredienteRepository.findById(Mockito.<Long>any())).thenThrow(new IdNotFoundException());
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.getIngredienteById(1L));
        verify(ingredienteRepository).existsById(eq(1L));
        verify(ingredienteRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link IngredienteService#getIngredienteById(Long)}
     */
    @Test
    void testGetIngredienteById3() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        EstoqueIngrediente estoqueIngrediente2 = new EstoqueIngrediente();
        estoqueIngrediente2.setCategoria("Categoria");
        estoqueIngrediente2
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente2.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente2.setIdItem(1L);
        estoqueIngrediente2.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente2.setLote("Lote");
        estoqueIngrediente2.setNome("Nome");
        estoqueIngrediente2.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente2.setValorMedida(10.0d);
        estoqueIngrediente2.setValorTotal(10.0d);
        Ingrediente ingrediente = mock(Ingrediente.class);
        when(ingrediente.getIdIngrediente()).thenReturn(1L);
        when(ingrediente.getValorMedida()).thenReturn("Valor Medida");
        when(ingrediente.getEstoqueIngrediente()).thenReturn(estoqueIngrediente2);
        when(ingrediente.getTipoMedida()).thenReturn(Medidas.COLHER_DE_SOPA);
        doNothing().when(ingrediente).setEstoqueIngrediente(Mockito.<EstoqueIngrediente>any());
        doNothing().when(ingrediente).setIdIngrediente(Mockito.<Long>any());
        doNothing().when(ingrediente).setTipoMedida(Mockito.<Medidas>any());
        doNothing().when(ingrediente).setValorMedida(Mockito.<String>any());
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        Optional<Ingrediente> ofResult = Optional.of(ingrediente);
        when(ingredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        IngredienteConsultaDto actualIngredienteById = ingredienteService.getIngredienteById(1L);

        // Assert
        verify(ingredienteRepository).existsById(eq(1L));
        verify(ingredienteRepository).findById(eq(1L));
        verify(ingrediente).getEstoqueIngrediente();
        verify(ingrediente).getIdIngrediente();
        verify(ingrediente).getTipoMedida();
        verify(ingrediente).getValorMedida();
        verify(ingrediente).setEstoqueIngrediente(isA(EstoqueIngrediente.class));
        verify(ingrediente).setIdIngrediente(eq(1L));
        verify(ingrediente).setTipoMedida(eq(Medidas.COLHER_DE_SOPA));
        verify(ingrediente).setValorMedida(eq("Valor Medida"));
        assertEquals("Categoria", actualIngredienteById.getEstoqueIngrediente().getCategoria());
        assertEquals("Valor Medida", actualIngredienteById.getValorMedida());
        assertEquals(1L, actualIngredienteById.getIdIngrediente().longValue());
        assertEquals(Medidas.COLHER_DE_SOPA, actualIngredienteById.getTipoMedida());
    }

    /**
     * Method under test: {@link IngredienteService#getIngredienteById(Long)}
     */
    @Test
    void testGetIngredienteById4() {
        // Arrange
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);
        Ingrediente ingrediente = mock(Ingrediente.class);
        doNothing().when(ingrediente).setEstoqueIngrediente(Mockito.<EstoqueIngrediente>any());
        doNothing().when(ingrediente).setIdIngrediente(Mockito.<Long>any());
        doNothing().when(ingrediente).setTipoMedida(Mockito.<Medidas>any());
        doNothing().when(ingrediente).setValorMedida(Mockito.<String>any());
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        Optional.of(ingrediente);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.getIngredienteById(1L));
        verify(ingredienteRepository).existsById(eq(1L));
        verify(ingrediente).setEstoqueIngrediente(isA(EstoqueIngrediente.class));
        verify(ingrediente).setIdIngrediente(eq(1L));
        verify(ingrediente).setTipoMedida(eq(Medidas.COLHER_DE_SOPA));
        verify(ingrediente).setValorMedida(eq("Valor Medida"));
    }

    /**
     * Method under test: {@link IngredienteService#createIngrediente(Ingrediente)}
     */
    @Test
    void testCreateIngrediente() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        when(ingredienteRepository.save(Mockito.<Ingrediente>any())).thenReturn(ingrediente);

        EstoqueIngrediente estoqueIngrediente2 = new EstoqueIngrediente();
        estoqueIngrediente2.setCategoria("Categoria");
        estoqueIngrediente2
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente2.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente2.setIdItem(1L);
        estoqueIngrediente2.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente2.setLote("Lote");
        estoqueIngrediente2.setNome("Nome");
        estoqueIngrediente2.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente2.setValorMedida(10.0d);
        estoqueIngrediente2.setValorTotal(10.0d);

        Ingrediente ingredienteDto = new Ingrediente();
        ingredienteDto.setEstoqueIngrediente(estoqueIngrediente2);
        ingredienteDto.setIdIngrediente(1L);
        ingredienteDto.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingredienteDto.setValorMedida("Valor Medida");

        // Act
        Ingrediente actualCreateIngredienteResult = ingredienteService.createIngrediente(ingredienteDto);

        // Assert
        verify(ingredienteRepository).save(isA(Ingrediente.class));
        assertSame(ingrediente, actualCreateIngredienteResult);
    }

    /**
     * Method under test: {@link IngredienteService#createIngrediente(Ingrediente)}
     */
    @Test
    void testCreateIngrediente2() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        when(ingredienteRepository.save(Mockito.<Ingrediente>any())).thenReturn(ingrediente);

        EstoqueIngrediente estoqueIngrediente2 = new EstoqueIngrediente();
        estoqueIngrediente2.setCategoria("Categoria");
        estoqueIngrediente2
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente2.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente2.setIdItem(1L);
        estoqueIngrediente2.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente2.setLote("Lote");
        estoqueIngrediente2.setNome("Nome");
        estoqueIngrediente2.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente2.setValorMedida(10.0d);
        estoqueIngrediente2.setValorTotal(10.0d);
        Ingrediente ingredienteDto = mock(Ingrediente.class);
        when(ingredienteDto.getTipoMedida()).thenReturn(Medidas.COLHER_DE_SOPA);
        doNothing().when(ingredienteDto).setEstoqueIngrediente(Mockito.<EstoqueIngrediente>any());
        doNothing().when(ingredienteDto).setIdIngrediente(Mockito.<Long>any());
        doNothing().when(ingredienteDto).setTipoMedida(Mockito.<Medidas>any());
        doNothing().when(ingredienteDto).setValorMedida(Mockito.<String>any());
        ingredienteDto.setEstoqueIngrediente(estoqueIngrediente2);
        ingredienteDto.setIdIngrediente(1L);
        ingredienteDto.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingredienteDto.setValorMedida("Valor Medida");

        // Act
        Ingrediente actualCreateIngredienteResult = ingredienteService.createIngrediente(ingredienteDto);

        // Assert
        verify(ingredienteRepository).save(isA(Ingrediente.class));
        verify(ingredienteDto).getTipoMedida();
        verify(ingredienteDto).setEstoqueIngrediente(isA(EstoqueIngrediente.class));
        verify(ingredienteDto).setIdIngrediente(eq(1L));
        verify(ingredienteDto).setTipoMedida(eq(Medidas.COLHER_DE_SOPA));
        verify(ingredienteDto).setValorMedida(eq("Valor Medida"));
        assertSame(ingrediente, actualCreateIngredienteResult);
    }

    /**
     * Method under test: {@link IngredienteService#createIngrediente(Ingrediente)}
     */
    @Test
    void testCreateIngrediente3() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        when(ingredienteRepository.save(Mockito.<Ingrediente>any())).thenReturn(ingrediente);

        EstoqueIngrediente estoqueIngrediente2 = new EstoqueIngrediente();
        estoqueIngrediente2.setCategoria("Categoria");
        estoqueIngrediente2
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente2.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente2.setIdItem(1L);
        estoqueIngrediente2.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente2.setLote("Lote");
        estoqueIngrediente2.setNome("Nome");
        estoqueIngrediente2.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente2.setValorMedida(10.0d);
        estoqueIngrediente2.setValorTotal(10.0d);
        Ingrediente ingredienteDto = mock(Ingrediente.class);
        when(ingredienteDto.getTipoMedida()).thenReturn(Medidas.COLHER_DE_CHA);
        doNothing().when(ingredienteDto).setEstoqueIngrediente(Mockito.<EstoqueIngrediente>any());
        doNothing().when(ingredienteDto).setIdIngrediente(Mockito.<Long>any());
        doNothing().when(ingredienteDto).setTipoMedida(Mockito.<Medidas>any());
        doNothing().when(ingredienteDto).setValorMedida(Mockito.<String>any());
        ingredienteDto.setEstoqueIngrediente(estoqueIngrediente2);
        ingredienteDto.setIdIngrediente(1L);
        ingredienteDto.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingredienteDto.setValorMedida("Valor Medida");

        // Act
        Ingrediente actualCreateIngredienteResult = ingredienteService.createIngrediente(ingredienteDto);

        // Assert
        verify(ingredienteRepository).save(isA(Ingrediente.class));
        verify(ingredienteDto, atLeast(1)).getTipoMedida();
        verify(ingredienteDto).setEstoqueIngrediente(isA(EstoqueIngrediente.class));
        verify(ingredienteDto).setIdIngrediente(eq(1L));
        verify(ingredienteDto).setTipoMedida(eq(Medidas.COLHER_DE_SOPA));
        verify(ingredienteDto).setValorMedida(eq("Valor Medida"));
        assertSame(ingrediente, actualCreateIngredienteResult);
    }

    /**
     * Method under test:
     * {@link IngredienteService#updateIngrediente(Long, Ingrediente)}
     */
    @Test
    void testUpdateIngrediente() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        when(ingredienteRepository.save(Mockito.<Ingrediente>any())).thenReturn(ingrediente);
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        EstoqueIngrediente estoqueIngrediente2 = new EstoqueIngrediente();
        estoqueIngrediente2.setCategoria("Categoria");
        estoqueIngrediente2
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente2.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente2.setIdItem(1L);
        estoqueIngrediente2.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente2.setLote("Lote");
        estoqueIngrediente2.setNome("Nome");
        estoqueIngrediente2.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente2.setValorMedida(10.0d);
        estoqueIngrediente2.setValorTotal(10.0d);

        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setEstoqueIngrediente(estoqueIngrediente2);
        ingrediente2.setIdIngrediente(1L);
        ingrediente2.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente2.setValorMedida("Valor Medida");

        // Act
        Ingrediente actualUpdateIngredienteResult = ingredienteService.updateIngrediente(1L, ingrediente2);

        // Assert
        verify(ingredienteRepository).existsById(eq(1L));
        verify(ingredienteRepository).save(isA(Ingrediente.class));
        assertEquals(1L, ingrediente2.getIdIngrediente().longValue());
        assertSame(ingrediente, actualUpdateIngredienteResult);
    }

    /**
     * Method under test:
     * {@link IngredienteService#updateIngrediente(Long, Ingrediente)}
     */
    @Test
    void testUpdateIngrediente2() {
        // Arrange
        when(ingredienteRepository.save(Mockito.<Ingrediente>any())).thenThrow(new IdNotFoundException());
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.updateIngrediente(1L, ingrediente));
        verify(ingredienteRepository).existsById(eq(1L));
        verify(ingredienteRepository).save(isA(Ingrediente.class));
    }

    /**
     * Method under test:
     * {@link IngredienteService#updateIngrediente(Long, Ingrediente)}
     */
    @Test
    void testUpdateIngrediente3() {
        // Arrange
        when(ingredienteRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.updateIngrediente(1L, ingrediente));
        verify(ingredienteRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link IngredienteService#deleteIngrediente(Long)}
     */
    @Test
    void testDeleteIngrediente() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        Optional<Ingrediente> ofResult = Optional.of(ingrediente);
        doNothing().when(ingredienteRepository).deleteById(Mockito.<Long>any());
        when(ingredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ingredienteService.deleteIngrediente(1L);

        // Assert that nothing has changed
        verify(ingredienteRepository).deleteById(eq(1L));
        verify(ingredienteRepository).findById(eq(1L));
        assertTrue(ingredienteService.getAllIngredientes().isEmpty());
    }

    /**
     * Method under test: {@link IngredienteService#deleteIngrediente(Long)}
     */
    @Test
    void testDeleteIngrediente2() {
        // Arrange
        EstoqueIngrediente estoqueIngrediente = new EstoqueIngrediente();
        estoqueIngrediente.setCategoria("Categoria");
        estoqueIngrediente
                .setDtaAviso(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        estoqueIngrediente.setDtaCadastro(LocalDate.of(1970, 1, 1));
        estoqueIngrediente.setIdItem(1L);
        estoqueIngrediente.setLocalArmazenamento("alice.liddell@example.org");
        estoqueIngrediente.setLote("Lote");
        estoqueIngrediente.setNome("Nome");
        estoqueIngrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        estoqueIngrediente.setValorMedida(10.0d);
        estoqueIngrediente.setValorTotal(10.0d);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setEstoqueIngrediente(estoqueIngrediente);
        ingrediente.setIdIngrediente(1L);
        ingrediente.setTipoMedida(Medidas.COLHER_DE_SOPA);
        ingrediente.setValorMedida("Valor Medida");
        Optional<Ingrediente> ofResult = Optional.of(ingrediente);
        doThrow(new IdNotFoundException()).when(ingredienteRepository).deleteById(Mockito.<Long>any());
        when(ingredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.deleteIngrediente(1L));
        verify(ingredienteRepository).deleteById(eq(1L));
        verify(ingredienteRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link IngredienteService#deleteIngrediente(Long)}
     */
    @Test
    void testDeleteIngrediente3() {
        // Arrange
        Optional<Ingrediente> emptyResult = Optional.empty();
        when(ingredienteRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> ingredienteService.deleteIngrediente(1L));
        verify(ingredienteRepository).findById(eq(1L));
    }
}
