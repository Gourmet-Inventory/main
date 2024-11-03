package project.gourmetinventoryproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.dto.estoqueIngrediente.EstoqueIngredienteConsultaDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.service.EstoqueIngredienteService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstoqueIngredienteControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private EstoqueIngredienteService estoqueIngredienteService;

    @InjectMocks
    private EstoqueIngredienteController estoqueIngredienteController;

    private List<EstoqueIngrediente> estoqueIngredientesEmpresa1;
    private List<EstoqueIngrediente> estoqueIngredientesEmpresa2;

    private EstoqueIngredienteConsultaDto estoqueDto1;
    private EstoqueIngredienteConsultaDto estoqueDto2;
    private EstoqueIngredienteConsultaDto estoqueDto3;
    private EstoqueIngredienteConsultaDto estoqueDto4;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        Empresa empresa1 = new Empresa();
//        empresa1.setIdEmpresa(1L);
//        empresa1.setNomeFantasia("Empresa 1");
//
//        Empresa empresa2 = new Empresa();
//        empresa2.setIdEmpresa(2L);
//        empresa2.setNomeFantasia("Empresa 2");
//
//        EstoqueIngrediente estoqueIngrediente1 = new EstoqueIngrediente();
//        estoqueIngrediente1.setIdItem(1L);
//        estoqueIngrediente1.setNome("Ingrediente 1");
//        estoqueIngrediente1.setEmpresa(empresa1);
//
//        EstoqueIngrediente estoqueIngrediente2 = new EstoqueIngrediente();
//        estoqueIngrediente2.setIdItem(2L);
//        estoqueIngrediente2.setNome("Ingrediente 2");
//        estoqueIngrediente2.setEmpresa(empresa2);
//
//        EstoqueIngrediente estoqueIngrediente3 = new EstoqueIngrediente();
//        estoqueIngrediente3.setIdItem(3L);
//        estoqueIngrediente3.setNome("Ingrediente 3");
//        estoqueIngrediente3.setEmpresa(empresa1);
//
//        EstoqueIngrediente estoqueIngrediente4 = new EstoqueIngrediente();
//        estoqueIngrediente4.setIdItem(4L);
//        estoqueIngrediente4.setNome("Ingrediente 4");
//        estoqueIngrediente4.setEmpresa(empresa1);
//
//        estoqueIngredientesEmpresa1 = Arrays.asList(estoqueIngrediente1, estoqueIngrediente3, estoqueIngrediente4);
//        estoqueIngredientesEmpresa2 = Arrays.asList(estoqueIngrediente2);
//
//        // Create DTOs
//        estoqueDto1 = new EstoqueIngredienteConsultaDto();
//        estoqueDto1.setIdItem(1L);
//        estoqueDto1.setNome("Ingrediente 1");
//
//        estoqueDto2 = new EstoqueIngredienteConsultaDto();
//        estoqueDto2.setIdItem(2L);
//        estoqueDto2.setNome("Ingrediente 2");
//
//        estoqueDto3 = new EstoqueIngredienteConsultaDto();
//        estoqueDto3.setIdItem(3L);
//        estoqueDto3.setNome("Ingrediente 3");
//
//        estoqueDto4 = new EstoqueIngredienteConsultaDto();
//        estoqueDto4.setIdItem(4L);
//        estoqueDto4.setNome("Ingrediente 4");
//
//        // Configure ModelMapper to return DTOs
//        when(modelMapper.map(estoqueIngrediente1, EstoqueIngredienteConsultaDto.class)).thenReturn(estoqueDto1);
//        when(modelMapper.map(estoqueIngrediente2, EstoqueIngredienteConsultaDto.class)).thenReturn(estoqueDto2);
//        when(modelMapper.map(estoqueIngrediente3, EstoqueIngredienteConsultaDto.class)).thenReturn(estoqueDto3);
//        when(modelMapper.map(estoqueIngrediente4, EstoqueIngredienteConsultaDto.class)).thenReturn(estoqueDto4);
//
//        when(estoqueIngredienteService.getAllEstoqueIngredientes(1L)).thenReturn(estoqueIngredientesEmpresa1);
//        when(estoqueIngredienteService.getAllEstoqueIngredientes(2L)).thenReturn(estoqueIngredientesEmpresa2);
//        when(estoqueIngredienteService.getEstoqueIngredienteById(1L)).thenReturn(estoqueIngrediente1);
//        when(estoqueIngredienteService.getEstoqueIngredienteById(2L)).thenReturn(estoqueIngrediente2);
//        when(estoqueIngredienteService.getEstoqueIngredienteById(3L)).thenReturn(estoqueIngrediente3);
//        when(estoqueIngredienteService.getEstoqueIngredienteById(4L)).thenReturn(estoqueIngrediente4);
//        doNothing().when(estoqueIngredienteService).deleteEstoqueIngrediente(1L);
//        doNothing().when(estoqueIngredienteService).deleteEstoqueIngrediente(6L);
//
//    }

//    @Test
//    @DisplayName("Get all Estoque Ingrediente")
//    void getAllEstoqueIngredientes() {
//        ResponseEntity<List<EstoqueIngredienteConsultaDto>> response = estoqueIngredienteController.getAllEstoqueIngredientes(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(3, response.getBody().size());
//        assertTrue(response.getBody().contains(estoqueDto1));
//        assertTrue(response.getBody().contains(estoqueDto3));
//        assertTrue(response.getBody().contains(estoqueDto4));
//
//        response = estoqueIngredienteController.getAllEstoqueIngredientes(2L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        assertTrue(response.getBody().contains(estoqueDto2));
//    }

    @Test
    @DisplayName("Get Estoque Ingrediente By ID")
    void getEstoqueIngredienteById() {
        ResponseEntity<EstoqueIngredienteConsultaDto> response = estoqueIngredienteController.getEstoqueIngredienteById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estoqueDto1, response.getBody());

        response = estoqueIngredienteController.getEstoqueIngredienteById(3L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estoqueDto3, response.getBody());
    }

    @Test
    @DisplayName("Get Estoque Ingrediente By Non-Existent ID")
    void getEstoqueIngredienteByNonExistentId() {
        ResponseEntity<EstoqueIngredienteConsultaDto> response = estoqueIngredienteController.getEstoqueIngredienteById(6L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Create Estoque Ingrediente")
    void createEstoqueIngrediente() {
        
        assertEquals(0, 1);
    }

    @Test
    @DisplayName("Update Estoque Ingrediente")
    void updateEstoqueIngrediente() {
        
        assertEquals(0, 1);
    }

    @Test
    @DisplayName("Delete Estoque Ingrediente")
    void deleteEstoqueIngrediente() {
        ResponseEntity<?> response = estoqueIngredienteController.deleteEstoqueIngrediente(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete Estoque Ingrediente Not Found")
    void deleteEstoqueIngredienteNotFound() {
        ResponseEntity<?> response = estoqueIngredienteController.deleteEstoqueIngrediente(6L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Get Estoque Select")
    void getEstoqueSelect() {

        assertEquals(0, 1);
    }
}
