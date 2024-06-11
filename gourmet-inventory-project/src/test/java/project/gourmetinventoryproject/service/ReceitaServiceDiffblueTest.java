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

import java.util.ArrayList;
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
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.ReceitaRepository;

@ContextConfiguration(classes = {ReceitaService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ReceitaServiceDiffblueTest {
    @MockBean
    private ReceitaRepository receitaRepository;

    @Autowired
    private ReceitaService receitaService;

    /**
     * Method under test: {@link ReceitaService#getAllReceitas()}
     */
    @Test
    void testGetAllReceitas() {
        // Arrange
        ArrayList<Receita> receitaList = new ArrayList<>();
        when(receitaRepository.findAll()).thenReturn(receitaList);

        // Act
        List<Receita> actualAllReceitas = receitaService.getAllReceitas();

        // Assert
        verify(receitaRepository).findAll();
        assertTrue(actualAllReceitas.isEmpty());
        assertSame(receitaList, actualAllReceitas);
    }

    /**
     * Method under test: {@link ReceitaService#getAllReceitas()}
     */
    @Test
    void testGetAllReceitas2() {
        // Arrange
        when(receitaRepository.findAll()).thenThrow(new IdNotFoundException());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.getAllReceitas());
        verify(receitaRepository).findAll();
    }

    /**
     * Method under test: {@link ReceitaService#getReceitaById(Long)}
     */
    @Test
    void testGetReceitaById() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);
        Optional<Receita> ofResult = Optional.of(receita);
        when(receitaRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(receitaRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        Receita actualReceitaById = receitaService.getReceitaById(1L);

        // Assert
        verify(receitaRepository).existsById(eq(1L));
        verify(receitaRepository).findById(eq(1L));
        assertSame(receita, actualReceitaById);
    }

    /**
     * Method under test: {@link ReceitaService#getReceitaById(Long)}
     */
    @Test
    void testGetReceitaById2() {
        // Arrange
        when(receitaRepository.findById(Mockito.<Long>any())).thenThrow(new IdNotFoundException());
        when(receitaRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.getReceitaById(1L));
        verify(receitaRepository).existsById(eq(1L));
        verify(receitaRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link ReceitaService#getReceitaById(Long)}
     */
    @Test
    void testGetReceitaById3() {
        // Arrange
        when(receitaRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.getReceitaById(1L));
        verify(receitaRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link ReceitaService#createReceita(Receita)}
     */
    @Test
    void testCreateReceita() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);
        when(receitaRepository.save(Mockito.<Receita>any())).thenReturn(receita);
        when(receitaRepository.findByIdIngredienteAndIdPrato(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());

        Receita receita2 = new Receita();
        receita2.setId(1L);
        receita2.setIdIngrediente(1L);
        receita2.setIdPrato(1L);
        receita2.setManipulavel(true);

        // Act
        Receita actualCreateReceitaResult = receitaService.createReceita(receita2);

        // Assert
        verify(receitaRepository).save(isA(Receita.class));
        verify(receitaRepository).findByIdIngredienteAndIdPrato(eq(1L), eq(1L));
        assertSame(receita, actualCreateReceitaResult);
    }

    /**
     * Method under test: {@link ReceitaService#createReceita(Receita)}
     */
    @Test
    void testCreateReceita2() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);

        ArrayList<Receita> receitaList = new ArrayList<>();
        receitaList.add(receita);
        when(receitaRepository.findByIdIngredienteAndIdPrato(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(receitaList);

        Receita receita2 = new Receita();
        receita2.setId(1L);
        receita2.setIdIngrediente(1L);
        receita2.setIdPrato(1L);
        receita2.setManipulavel(true);

        // Act and Assert
        assertThrows(ElementAlreadyExistException.class, () -> receitaService.createReceita(receita2));
        verify(receitaRepository).findByIdIngredienteAndIdPrato(eq(1L), eq(1L));
    }

    /**
     * Method under test: {@link ReceitaService#createReceita(Receita)}
     */
    @Test
    void testCreateReceita3() {
        // Arrange
        when(receitaRepository.findByIdIngredienteAndIdPrato(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenThrow(new ElementAlreadyExistException());

        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);

        // Act and Assert
        assertThrows(ElementAlreadyExistException.class, () -> receitaService.createReceita(receita));
        verify(receitaRepository).findByIdIngredienteAndIdPrato(eq(1L), eq(1L));
    }

    /**
     * Method under test: {@link ReceitaService#updateReceita(Long, Receita)}
     */
    @Test
    void testUpdateReceita() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);
        when(receitaRepository.save(Mockito.<Receita>any())).thenReturn(receita);
        when(receitaRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Receita receita2 = new Receita();
        receita2.setId(1L);
        receita2.setIdIngrediente(1L);
        receita2.setIdPrato(1L);
        receita2.setManipulavel(true);

        // Act
        Receita actualUpdateReceitaResult = receitaService.updateReceita(1L, receita2);

        // Assert
        verify(receitaRepository).existsById(eq(1L));
        verify(receitaRepository).save(isA(Receita.class));
        assertEquals(1L, receita2.getId().longValue());
        assertSame(receita, actualUpdateReceitaResult);
    }

    /**
     * Method under test: {@link ReceitaService#updateReceita(Long, Receita)}
     */
    @Test
    void testUpdateReceita2() {
        // Arrange
        when(receitaRepository.save(Mockito.<Receita>any())).thenThrow(new IdNotFoundException());
        when(receitaRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.updateReceita(1L, receita));
        verify(receitaRepository).existsById(eq(1L));
        verify(receitaRepository).save(isA(Receita.class));
    }

    /**
     * Method under test: {@link ReceitaService#updateReceita(Long, Receita)}
     */
    @Test
    void testUpdateReceita3() {
        // Arrange
        when(receitaRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.updateReceita(1L, receita));
        verify(receitaRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link ReceitaService#deleteReceita(Long)}
     */
    @Test
    void testDeleteReceita() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);
        Optional<Receita> ofResult = Optional.of(receita);
        doNothing().when(receitaRepository).deleteById(Mockito.<Long>any());
        when(receitaRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        receitaService.deleteReceita(1L);

        // Assert that nothing has changed
        verify(receitaRepository).deleteById(eq(1L));
        verify(receitaRepository).findById(eq(1L));
        assertTrue(receitaService.getAllReceitas().isEmpty());
    }

    /**
     * Method under test: {@link ReceitaService#deleteReceita(Long)}
     */
    @Test
    void testDeleteReceita2() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);
        Optional<Receita> ofResult = Optional.of(receita);
        doThrow(new IdNotFoundException()).when(receitaRepository).deleteById(Mockito.<Long>any());
        when(receitaRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.deleteReceita(1L));
        verify(receitaRepository).deleteById(eq(1L));
        verify(receitaRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link ReceitaService#deleteReceita(Long)}
     */
    @Test
    void testDeleteReceita3() {
        // Arrange
        Optional<Receita> emptyResult = Optional.empty();
        when(receitaRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> receitaService.deleteReceita(1L));
        verify(receitaRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link ReceitaService#findIngredienteIdByTipoMedidaValorMedida(Long, String, String)}
     */
    @Test
    void testFindIngredienteIdByTipoMedidaValorMedida() {
        // Arrange
        when(receitaRepository.findIngredienteIdByTipoMedidaValorMedida(Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(1L);

        // Act
        Long actualFindIngredienteIdByTipoMedidaValorMedidaResult = receitaService
                .findIngredienteIdByTipoMedidaValorMedida(1L, "Tipo Medida", "Valor Medida");

        // Assert
        verify(receitaRepository).findIngredienteIdByTipoMedidaValorMedida(eq(1L), eq("Tipo Medida"), eq("Valor Medida"));
        assertEquals(1L, actualFindIngredienteIdByTipoMedidaValorMedidaResult.longValue());
    }

    /**
     * Method under test:
     * {@link ReceitaService#findIngredienteIdByTipoMedidaValorMedida(Long, String, String)}
     */
    @Test
    void testFindIngredienteIdByTipoMedidaValorMedida2() {
        // Arrange
        when(receitaRepository.findIngredienteIdByTipoMedidaValorMedida(Mockito.<Long>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenThrow(new IdNotFoundException());

        // Act and Assert
        assertThrows(IdNotFoundException.class,
                () -> receitaService.findIngredienteIdByTipoMedidaValorMedida(1L, "Tipo Medida", "Valor Medida"));
        verify(receitaRepository).findIngredienteIdByTipoMedidaValorMedida(eq(1L), eq("Tipo Medida"), eq("Valor Medida"));
    }
}
