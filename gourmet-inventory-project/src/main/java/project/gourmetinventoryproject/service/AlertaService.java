package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.AlertaRepository;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;
    @Autowired
    private EmpresaService empresaService;


    public List<Alerta> getAllAlerta(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<EstoqueIngrediente> estoque = estoqueIngredienteRepository.findAllByEmpresa(empresa);
        List<Alerta> alertas = new ArrayList<>();
        for (int i = 0; i < estoque.size(); i++) {
            Alerta alerta = alertaRepository.findByEstoqueIngrediente(estoque.get(i));
            alertas.add(alerta);
        }
        return alertas;

    }
    public Alerta createAlerta(Alerta alerta) {

        return alertaRepository.save(alerta);
    }
    public void deleteAlerta(Long id) {
        if (alertaRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        alertaRepository.deleteById(id);
    }
}