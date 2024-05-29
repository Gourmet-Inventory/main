package project.gourmetinventoryproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.repository.EmpresaRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    private List<Empresa> empresas;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);

        Empresa empresa1 = new Empresa();
        empresa1.setIdEmpresa(1L);

        Empresa empresa2 = new Empresa();
        empresa2.setIdEmpresa(2L);

        //empresas = Arrays.asList(empresa1, empresa2);
        empresas.add(empresa1);
        empresas.add(empresa2);

        when(empresaRepository.findAll()).thenReturn(empresas);
        when(empresaRepository.findById(1L)).thenReturn(java.util.Optional.of(empresa1));
        when(empresaRepository.findById(2L)).thenReturn(java.util.Optional.of(empresa2));
    }

    @Test
    void getEmpresasValido() {
        List<Empresa> empresas = empresaService.getEmpresas();
        assertEquals(2, empresas.size());
    }
}