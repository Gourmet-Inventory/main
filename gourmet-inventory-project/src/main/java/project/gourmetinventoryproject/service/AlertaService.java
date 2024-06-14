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
    private EmpresaService empresaService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;


    public List<Alerta> getAllAlerta(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<Alerta> alertasAll = alertaRepository.findAll();
//        List<EstoqueIngrediente> estoque = estoqueIngredienteService.getAllEstoqueIngredientes(idEmpresa);
        List<Alerta> alertasEmpresa = new ArrayList<>();
        for (int i = 0; i < alertasAll.size(); i++) {
            Long alertaIdEmpresa = alertasAll.get(i).getEstoqueIngrediente().getEmpresa().getIdEmpresa();
            if (alertaIdEmpresa.equals(idEmpresa)) {
                alertasEmpresa.add(alertasAll.get(i));
            }
        }
        return alertasEmpresa;
    }
    public List<Alerta> getAllAlerta() {
        return alertaRepository.findAll();
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