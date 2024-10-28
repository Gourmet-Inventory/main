package project.gourmetinventoryproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    private Empresa empresa1;
    private Empresa empresa2;
    private List<Empresa> empresas;
    private EmpresaCriacaoDto empresaCriacaoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        empresa1 = new Empresa();
        empresa1.setIdEmpresa(1L);

        empresa2 = new Empresa();
        empresa2.setIdEmpresa(2L);

        empresas = Arrays.asList(empresa1, empresa2);

        empresaCriacaoDto = new EmpresaCriacaoDto();
        empresaCriacaoDto.setNomeFantasia("Empresa Teste");
        empresaCriacaoDto.setCnpj("12345678901234");
    }

    @Test
    void getEmpresasReturnsCorrectSize() {
        empresa1 = new Empresa();
        empresa1.setIdEmpresa(1L);
        empresa2 = new Empresa();
        empresa2.setIdEmpresa(2L);

        empresas = Arrays.asList(empresa1, empresa2);
        when(empresaRepository.findAll()).thenReturn(empresas);

        List<Empresa> result = empresaService.getEmpresas();
        assertEquals(2, result.size());
    }

    @Test
    void getEmpresaByIdReturnsCorrectEmpresa() {
        EmpresaService empresaService = mock(EmpresaService.class);
        Empresa empresa = mock(Empresa.class);
        when(empresaRepository.findById(anyLong())).thenReturn(Optional.of(empresa));

        Empresa result = empresaService.getEmpresasById(empresa.getIdEmpresa());
        assertNotNull(result);
        assertEquals(empresa.getIdEmpresa(), result.getIdEmpresa());
    }

    @Test
    void getEmpresaByIdThrowsExceptionForNonExistentId() {
        when(empresaRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(IdNotFoundException.class, () -> empresaService.getEmpresasById(3L));
    }

    @Test
    void postEmpresaCreatesAndReturnsEmpresa() {
        when(modelMapper.map(empresaCriacaoDto, Empresa.class)).thenReturn(empresa1);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa1);

        Empresa result = empresaService.postEmpresa(empresaCriacaoDto);
        assertNotNull(result);
        assertEquals(empresa1, result);
    }

    @Test
    void deleteEmpresaDeletesEmpresa() {
        empresaService.deleteEmpresa(1L);
        verify(empresaRepository, times(1)).deleteById(1L);
    }

    @Test
    void patchEmpresaUpdatesExistingEmpresa() {
        when(empresaRepository.existsById(1L)).thenReturn(true);
        when(modelMapper.map(empresaCriacaoDto, Empresa.class)).thenReturn(empresa1);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa1);

        empresaService.patchEmpresa(1L, empresaCriacaoDto);
        verify(empresaRepository, times(1)).save(empresa1);
    }

    @Test
    void putEmpresaUpdatesExistingEmpresa() {
        when(empresaRepository.existsById(1L)).thenReturn(true);
        when(modelMapper.map(empresaCriacaoDto, Empresa.class)).thenReturn(empresa1);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa1);

        Empresa result = empresaService.putEmpresa(1L, empresaCriacaoDto);
        assertNotNull(result);
        assertEquals(empresa1, result);
    }

    @Test
    void putEmpresaThrowsExceptionForNonExistentId() {
        when(empresaRepository.existsById(3L)).thenReturn(false);
        assertThrows(IdNotFoundException.class, () -> empresaService.putEmpresa(3L, empresaCriacaoDto));
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 7ed6d3c46e25a50256593c25cbc326836945d078
