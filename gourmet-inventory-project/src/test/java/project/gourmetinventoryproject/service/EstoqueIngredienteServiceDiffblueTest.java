package project.gourmetinventoryproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;

@ContextConfiguration(classes = {EstoqueIngredienteService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class EstoqueIngredienteServiceDiffblueTest {
    @MockBean
    private EstoqueIngredienteRepository estoqueIngredienteRepository;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#getAllEstoqueIngredientes()}
     */
    @Test
    void testGetAllEstoqueIngredientes() {
        // Arrange
        ArrayList<EstoqueIngrediente> estoqueIngredienteList = new ArrayList<>();
        when(estoqueIngredienteRepository.findAll()).thenReturn(estoqueIngredienteList);

        // Act
        List<EstoqueIngrediente> actualAllEstoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes();

        // Assert
        verify(estoqueIngredienteRepository).findAll();
        assertTrue(actualAllEstoqueIngredientes.isEmpty());
        assertSame(estoqueIngredienteList, actualAllEstoqueIngredientes);
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#getAllEstoqueIngredientes()}
     */
    @Test
    void testGetAllEstoqueIngredientes2() {
        // Arrange
        when(estoqueIngredienteRepository.findAll()).thenThrow(new IdNotFoundException());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> estoqueIngredienteService.getAllEstoqueIngredientes());
        verify(estoqueIngredienteRepository).findAll();
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#getEstoqueIngredienteById(Long)}
     */
    @Test
    void testGetEstoqueIngredienteById() {
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
        Optional<EstoqueIngrediente> ofResult = Optional.of(estoqueIngrediente);
        when(estoqueIngredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(estoqueIngredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        EstoqueIngrediente actualEstoqueIngredienteById = estoqueIngredienteService.getEstoqueIngredienteById(1L);

        // Assert
        verify(estoqueIngredienteRepository).existsById(eq(1L));
        verify(estoqueIngredienteRepository).findById(eq(1L));
        assertSame(estoqueIngrediente, actualEstoqueIngredienteById);
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#getEstoqueIngredienteById(Long)}
     */
    @Test
    void testGetEstoqueIngredienteById2() {
        // Arrange
        when(estoqueIngredienteRepository.findById(Mockito.<Long>any())).thenThrow(new IdNotFoundException());
        when(estoqueIngredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> estoqueIngredienteService.getEstoqueIngredienteById(1L));
        verify(estoqueIngredienteRepository).existsById(eq(1L));
        verify(estoqueIngredienteRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#getEstoqueIngredienteById(Long)}
     */
    @Test
    void testGetEstoqueIngredienteById3() {
        // Arrange
        when(estoqueIngredienteRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> estoqueIngredienteService.getEstoqueIngredienteById(1L));
        verify(estoqueIngredienteRepository).existsById(eq(1L));
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#createEstoqueIngrediente(EstoqueIngrediente)}
     */
    @Test
    void testCreateEstoqueIngrediente() {
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
        when(estoqueIngredienteRepository.save(Mockito.<EstoqueIngrediente>any())).thenReturn(estoqueIngrediente);

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

        // Act
        EstoqueIngrediente actualCreateEstoqueIngredienteResult = estoqueIngredienteService
                .createEstoqueIngrediente(estoqueIngrediente2);

        // Assert
        verify(estoqueIngredienteRepository).save(isA(EstoqueIngrediente.class));
        assertSame(estoqueIngrediente, actualCreateEstoqueIngredienteResult);
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#createEstoqueIngrediente(EstoqueIngrediente)}
     */
    @Test
    void testCreateEstoqueIngrediente2() {
        // Arrange
        when(estoqueIngredienteRepository.save(Mockito.<EstoqueIngrediente>any())).thenThrow(new IdNotFoundException());

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

        // Act and Assert
        assertThrows(IdNotFoundException.class,
                () -> estoqueIngredienteService.createEstoqueIngrediente(estoqueIngrediente));
        verify(estoqueIngredienteRepository).save(isA(EstoqueIngrediente.class));
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#updateEstoqueIngrediente(Long, EstoqueIngrediente)}
     */
    @Test
    void testUpdateEstoqueIngrediente() {
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
        when(estoqueIngredienteRepository.save(Mockito.<EstoqueIngrediente>any())).thenReturn(estoqueIngrediente);
        when(estoqueIngredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

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

        // Act
        EstoqueIngrediente actualUpdateEstoqueIngredienteResult = estoqueIngredienteService.updateEstoqueIngrediente(1L,
                estoqueIngrediente2);

        // Assert
        verify(estoqueIngredienteRepository).existsById(eq(1L));
        verify(estoqueIngredienteRepository).save(isA(EstoqueIngrediente.class));
        assertEquals(1L, estoqueIngrediente2.getIdItem().longValue());
        assertSame(estoqueIngrediente, actualUpdateEstoqueIngredienteResult);
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#updateEstoqueIngrediente(Long, EstoqueIngrediente)}
     */
    @Test
    void testUpdateEstoqueIngrediente2() {
        // Arrange
        when(estoqueIngredienteRepository.save(Mockito.<EstoqueIngrediente>any())).thenThrow(new IdNotFoundException());
        when(estoqueIngredienteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

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

        // Act and Assert
        assertThrows(IdNotFoundException.class,
                () -> estoqueIngredienteService.updateEstoqueIngrediente(1L, estoqueIngrediente));
        verify(estoqueIngredienteRepository).existsById(eq(1L));
        verify(estoqueIngredienteRepository).save(isA(EstoqueIngrediente.class));
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#updateEstoqueIngrediente(Long, EstoqueIngrediente)}
     */
    @Test
    void testUpdateEstoqueIngrediente3() {
        // Arrange
        when(estoqueIngredienteRepository.existsById(Mockito.<Long>any())).thenReturn(false);

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

        // Act and Assert
        assertThrows(IdNotFoundException.class,
                () -> estoqueIngredienteService.updateEstoqueIngrediente(1L, estoqueIngrediente));
        verify(estoqueIngredienteRepository).existsById(eq(1L));
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#deleteEstoqueIngrediente(Long)}
     */
    @Test
    void testDeleteEstoqueIngrediente() {
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
        Optional<EstoqueIngrediente> ofResult = Optional.of(estoqueIngrediente);
        doNothing().when(estoqueIngredienteRepository).deleteById(Mockito.<Long>any());
        when(estoqueIngredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        estoqueIngredienteService.deleteEstoqueIngrediente(1L);

        // Assert that nothing has changed
        verify(estoqueIngredienteRepository).deleteById(eq(1L));
        verify(estoqueIngredienteRepository).findById(eq(1L));
        assertTrue(estoqueIngredienteService.getAllEstoqueIngredientes().isEmpty());
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#deleteEstoqueIngrediente(Long)}
     */
    @Test
    void testDeleteEstoqueIngrediente2() {
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
        Optional<EstoqueIngrediente> ofResult = Optional.of(estoqueIngrediente);
        doThrow(new IdNotFoundException()).when(estoqueIngredienteRepository).deleteById(Mockito.<Long>any());
        when(estoqueIngredienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> estoqueIngredienteService.deleteEstoqueIngrediente(1L));
        verify(estoqueIngredienteRepository).deleteById(eq(1L));
        verify(estoqueIngredienteRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link EstoqueIngredienteService#deleteEstoqueIngrediente(Long)}
     */
    @Test
    void testDeleteEstoqueIngrediente3() {
        // Arrange
        Optional<EstoqueIngrediente> emptyResult = Optional.empty();
        when(estoqueIngredienteRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> estoqueIngredienteService.deleteEstoqueIngrediente(1L));
        verify(estoqueIngredienteRepository).findById(eq(1L));
    }
}
