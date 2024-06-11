package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.AlertaRepository;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;


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