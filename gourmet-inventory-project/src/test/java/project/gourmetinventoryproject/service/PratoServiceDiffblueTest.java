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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.domain.Receita;
import project.gourmetinventoryproject.exception.ElementAlreadyExistException;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.PratoRepository;
import project.gourmetinventoryproject.repository.ReceitaRepository;

@ContextConfiguration(classes = {PratoService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PratoServiceDiffblueTest {
    @MockBean
    private ReceitaRepository receitaRepository;

    @MockBean
    private PratoRepository pratoRepository;

    @Autowired
    private PratoService pratoService;

    /**
     * Method under test: {@link PratoService#getAllPratos()}
     */
    @Test
    void testGetAllPratos() {
        // Arrange
        ArrayList<Prato> pratoList = new ArrayList<>();
        when(pratoRepository.findAll()).thenReturn(pratoList);

        // Act
        List<Prato> actualAllPratos = pratoService.getAllPratos();

        // Assert
        verify(pratoRepository).findAll();
        assertTrue(actualAllPratos.isEmpty());
        assertSame(pratoList, actualAllPratos);
    }

    /**
     * Method under test: {@link PratoService#getAllPratos()}
     */
    @Test
    void testGetAllPratos2() {
        // Arrange
        when(pratoRepository.findAll()).thenThrow(new IdNotFoundException());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.getAllPratos());
        verify(pratoRepository).findAll();
    }

    /**
     * Method under test: {@link PratoService#getPratoById(Long)}
     */
    @Test
    void testGetPratoById() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
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
     * Method under test: {@link PratoService#createPrato(Prato)}
     */
    @Test
    void testCreatePrato() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        when(pratoRepository.save(Mockito.<Prato>any())).thenReturn(prato);
        when(pratoRepository.findByNomeIgnoreCase(Mockito.<String>any())).thenReturn(new ArrayList<>());

        Prato prato2 = new Prato();
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setIdPrato(1L);
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);

        // Act
        Prato actualCreatePratoResult = pratoService.createPrato(prato2);

        // Assert
        verify(pratoRepository).save(isA(Prato.class));
        verify(pratoRepository).findByNomeIgnoreCase(eq("Nome"));
        assertSame(prato, actualCreatePratoResult);
    }

    /**
     * Method under test: {@link PratoService#createPrato(Prato)}
     */
    @Test
    void testCreatePrato2() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);

        ArrayList<Prato> pratoList = new ArrayList<>();
        pratoList.add(prato);
        when(pratoRepository.findByNomeIgnoreCase(Mockito.<String>any())).thenReturn(pratoList);

        Prato prato2 = new Prato();
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setIdPrato(1L);
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);

        // Act and Assert
        assertThrows(ElementAlreadyExistException.class, () -> pratoService.createPrato(prato2));
        verify(pratoRepository).findByNomeIgnoreCase(eq("Nome"));
    }

    /**
     * Method under test: {@link PratoService#createPrato(Prato)}
     */
    @Test
    void testCreatePrato3() {
        // Arrange
        when(pratoRepository.findByNomeIgnoreCase(Mockito.<String>any())).thenThrow(new ElementAlreadyExistException());

        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);

        // Act and Assert
        assertThrows(ElementAlreadyExistException.class, () -> pratoService.createPrato(prato));
        verify(pratoRepository).findByNomeIgnoreCase(eq("Nome"));
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, Prato)}
     */
    @Test
    void testUpdatePrato() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        Optional<Prato> ofResult = Optional.of(prato);

        Prato prato2 = new Prato();
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setIdPrato(1L);
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);
        when(pratoRepository.save(Mockito.<Prato>any())).thenReturn(prato2);
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Prato prato3 = new Prato();
        prato3.setCategoria("Categoria");
        prato3.setDescricao("Descricao");
        prato3.setIdPrato(1L);
        prato3.setNome("Nome");
        prato3.setPreco(10.0d);

        // Act
        Prato actualUpdatePratoResult = pratoService.updatePrato(1L, prato3);

        // Assert
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        verify(pratoRepository).save(isA(Prato.class));
        assertSame(prato2, actualUpdatePratoResult);
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, Prato)}
     */
    @Test
    void testUpdatePrato2() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        Optional<Prato> ofResult = Optional.of(prato);
        when(pratoRepository.save(Mockito.<Prato>any())).thenThrow(new IdNotFoundException());
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Prato prato2 = new Prato();
        prato2.setCategoria("Categoria");
        prato2.setDescricao("Descricao");
        prato2.setIdPrato(1L);
        prato2.setNome("Nome");
        prato2.setPreco(10.0d);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePrato(1L, prato2));
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        verify(pratoRepository).save(isA(Prato.class));
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, Prato)}
     */
    @Test
    void testUpdatePrato3() {
        // Arrange
        Optional<Prato> emptyResult = Optional.empty();
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePrato(1L, prato));
        verify(pratoRepository).existsById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#updatePrato(Long, Prato)}
     */
    @Test
    void testUpdatePrato4() {
        // Arrange
        when(pratoRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.updatePrato(1L, prato));
        verify(pratoRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link PratoService#deletePrato(Long)}
     */
    @Test
    void testDeletePrato() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
        Optional<Prato> ofResult = Optional.of(prato);
        doNothing().when(pratoRepository).deleteById(Mockito.<Long>any());
        when(pratoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        pratoService.deletePrato(1L);

        // Assert that nothing has changed
        verify(pratoRepository).deleteById(eq(1L));
        verify(pratoRepository).findById(eq(1L));
        assertTrue(pratoService.getAllPratos().isEmpty());
    }

    /**
     * Method under test: {@link PratoService#deletePrato(Long)}
     */
    @Test
    void testDeletePrato2() {
        // Arrange
        Prato prato = new Prato();
        prato.setCategoria("Categoria");
        prato.setDescricao("Descricao");
        prato.setIdPrato(1L);
        prato.setNome("Nome");
        prato.setPreco(10.0d);
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
     * Method under test: {@link PratoService#calculateIngredientUsage(List)}
     */
    @Test
    void testCalculateIngredientUsage() {
        // Arrange, Act and Assert
        assertTrue(pratoService.calculateIngredientUsage(new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link PratoService#calculateIngredientUsage(List)}
     */
    @Test
    void testCalculateIngredientUsage2() {
        // Arrange
        when(receitaRepository.findByIdPrato(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        ArrayList<Long> servedDishesIds = new ArrayList<>();
        servedDishesIds.add(1L);

        // Act
        Map<Long, Integer> actualCalculateIngredientUsageResult = pratoService.calculateIngredientUsage(servedDishesIds);

        // Assert
        verify(receitaRepository).findByIdPrato(eq(1L));
        assertTrue(actualCalculateIngredientUsageResult.isEmpty());
    }

    /**
     * Method under test: {@link PratoService#calculateIngredientUsage(List)}
     */
    @Test
    void testCalculateIngredientUsage3() {
        // Arrange
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setIdIngrediente(1L);
        receita.setIdPrato(1L);
        receita.setManipulavel(true);
        receita.setQuantidade(1);

        ArrayList<Receita> receitaList = new ArrayList<>();
        receitaList.add(receita);
        when(receitaRepository.findByIdPrato(Mockito.<Long>any())).thenReturn(receitaList);

        ArrayList<Long> servedDishesIds = new ArrayList<>();
        servedDishesIds.add(1L);

        // Act
        Map<Long, Integer> actualCalculateIngredientUsageResult = pratoService.calculateIngredientUsage(servedDishesIds);

        // Assert
        verify(receitaRepository).findByIdPrato(eq(1L));
        assertEquals(1, actualCalculateIngredientUsageResult.size());
        assertEquals(1, actualCalculateIngredientUsageResult.get(1L).intValue());
    }

    /**
     * Method under test: {@link PratoService#calculateIngredientUsage(List)}
     */
    @Test
    void testCalculateIngredientUsage4() {
        // Arrange
        when(receitaRepository.findByIdPrato(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        ArrayList<Long> servedDishesIds = new ArrayList<>();
        servedDishesIds.add(0L);
        servedDishesIds.add(1L);

        // Act
        Map<Long, Integer> actualCalculateIngredientUsageResult = pratoService.calculateIngredientUsage(servedDishesIds);

        // Assert
        verify(receitaRepository, atLeast(1)).findByIdPrato(Mockito.<Long>any());
        assertTrue(actualCalculateIngredientUsageResult.isEmpty());
    }

    /**
     * Method under test: {@link PratoService#calculateIngredientUsage(List)}
     */
    @Test
    void testCalculateIngredientUsage5() {
        // Arrange
        when(receitaRepository.findByIdPrato(Mockito.<Long>any())).thenThrow(new IdNotFoundException());

        ArrayList<Long> servedDishesIds = new ArrayList<>();
        servedDishesIds.add(1L);

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> pratoService.calculateIngredientUsage(servedDishesIds));
        verify(receitaRepository).findByIdPrato(eq(1L));
    }
}
