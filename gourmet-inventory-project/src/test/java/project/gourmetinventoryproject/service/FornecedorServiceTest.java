package project.gourmetinventoryproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import project.gourmetinventoryproject.domain.Fornecedor;
import project.gourmetinventoryproject.dto.fornecedor.FornecedorCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.FornecedorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FornecedorServiceTest {
    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FornecedorService fornecedorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save fornecedor successfully")
    void postFornecedorSuccess() {
        FornecedorCriacaoDto fornecedorCriacaoDto = mock(FornecedorCriacaoDto.class);
        Fornecedor fornecedor = new Fornecedor();
        when(modelMapper.map(fornecedorCriacaoDto, Fornecedor.class)).thenReturn(fornecedor);
        fornecedorService.postFornecedor(fornecedorCriacaoDto);
        verify(fornecedorRepository, times(1)).save(fornecedor);
    }

    @Test
    @DisplayName("Should return all fornecedores")
    void getFornecedoresSuccess() {
        when(fornecedorRepository.findAll()).thenReturn(Arrays.asList(new Fornecedor(), new Fornecedor()));
        List<Fornecedor> fornecedores = fornecedorService.getFornecedores();
        assertEquals(2, fornecedores.size());
    }

    @Test
    @DisplayName("Should delete fornecedor when exists")
    void deleteFornecedorWhenExists() {
        when(fornecedorRepository.existsById(anyLong())).thenReturn(true);
        assertDoesNotThrow(() -> fornecedorService.deleteFornecedor(1L));
        verify(fornecedorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw IdNotFoundException when delete fornecedor not exists")
    void deleteFornecedorWhenNotExists() {
        when(fornecedorRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(IdNotFoundException.class, () -> fornecedorService.deleteFornecedor(1L));
    }

    @Test
    @DisplayName("Should update fornecedor when exists")
    void patchFornecedorWhenExists() {
        Fornecedor fornecedor = new Fornecedor();
        FornecedorCriacaoDto fornecedorCriacaoDto = mock(FornecedorCriacaoDto.class);
        when(fornecedorRepository.existsById(anyLong())).thenReturn(true);
        when(fornecedorRepository.findById(anyLong())).thenReturn(Optional.of(fornecedor));
        assertDoesNotThrow(() -> fornecedorService.patchFornecedor(1L, fornecedorCriacaoDto));
        verify(fornecedorRepository, times(1)).save(fornecedor);
    }

    @Test
    @DisplayName("Should throw IdNotFoundException when update fornecedor not exists")
    void patchFornecedorWhenNotExists() {
        FornecedorCriacaoDto fornecedorCriacaoDto = mock(FornecedorCriacaoDto.class);
        when(fornecedorRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(IdNotFoundException.class, () -> fornecedorService.patchFornecedor(1L, fornecedorCriacaoDto));
    }
}