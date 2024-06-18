package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.AlertaRepository;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EstoqueIngredienteRepository estoqueIngredienteRepository;

    public List<Alerta> getAllAlerta(Long idEmpresa) {
        Empresa empresa = empresaService.getEmpresasById(idEmpresa);
        List<Alerta> alertasAll = alertaRepository.findAll();
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

    public List<Alerta> createAlerta() {
        List<Alerta> alertas = alertaRepository.findAll();
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteRepository.findAll();
        List<Alerta> newAlertas = new ArrayList<>();
        if (estoqueIngredientes.isEmpty()) {
            //estoque vazio
            System.out.println("Sem alertas para criar");
        }
        else {
            Boolean aletaExiste = false;
            for (int i = 0; i < estoqueIngredientes.size(); i++) {
                aletaExiste = false;
                if (!alertas.isEmpty()){
                    //validar existencia de alerta para aquele estoque
                    for (int j = 0; j < alertas.size(); j++) {
                        if (estoqueIngredientes.get(i).getIdItem().equals(alertas.get(j).getEstoqueIngrediente().getIdItem())) {
                            aletaExiste = true;
                            alertas.remove(j);
                            break;
                        } else {
                            aletaExiste = false;
                        }
                    }
                    //Criar alerta
                    if (tipoAlertaValorTotal(estoqueIngredientes.get(i)) != null && !aletaExiste) {
                        Alerta alerta = new Alerta();
                        alerta.setTipoAlerta(tipoAlertaValorTotal(estoqueIngredientes.get(i)));
                        estoqueIngredientes.get(i).addAlerta(alerta);
                        alerta.setEstoqueIngrediente(estoqueIngredientes.get(i));
                        newAlertas.add(alerta);
                        saveAlerta(alerta);
                    }
                }
                else {
                    //Criar alerta
                    if (tipoAlertaValorTotal(estoqueIngredientes.get(i)) != null && !aletaExiste) {
                        Alerta alerta = new Alerta();
                        alerta.setTipoAlerta(tipoAlertaValorTotal(estoqueIngredientes.get(i)));
                        alerta.setEstoqueIngrediente(estoqueIngredientes.get(i));
                        estoqueIngredientes.get(i).addAlerta(alerta);
                        newAlertas.add(alerta);
                        saveAlerta(alerta);
                    }
                }
            }
            System.out.println("alertas criados");
        }
        return newAlertas;

    }

    public Alerta saveAlerta(Alerta alerta){
        return alertaRepository.save(alerta);
    }

    public void deleteAlerta(Long id) {
        if (alertaRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        alertaRepository.deleteById(id);
    }
    public EstoqueIngrediente checarAlerta(EstoqueIngrediente estoqueIngrediente){
        if (estoqueIngrediente.getAlertas().isEmpty() && tipoAlertaValorTotal(estoqueIngrediente) != null){
            createAlerta();
            return estoqueIngrediente;
        }
        if (estoqueIngrediente.getAlertas().size() == 2){
            if(estoqueIngrediente.getUnitario() != null){
                if (estoqueIngrediente.getValorTotal() >= 0 && estoqueIngrediente.getValorTotal() < 0){
                    for (int i = 0; i < estoqueIngrediente.getAlertas().size(); i++) {
                        if (estoqueIngrediente.getAlertas().get(i).getTipoAlerta() == "Estoque vazio"){
                            deleteAlerta(estoqueIngrediente.getAlertas().get(i).getIdAlerta());
                            return estoqueIngrediente;
                        }
                    }
                    Alerta alerta = new Alerta();
                    alerta.setTipoAlerta("Estoque acabando");
                    alerta.setEstoqueIngrediente(estoqueIngrediente);
                    estoqueIngrediente.getAlertas().add(alerta);
                    saveAlerta(alerta);
                    return estoqueIngrediente;
                }
                if (estoqueIngrediente.getValorTotal() >= 60){
                    for (int i = 0; i < estoqueIngrediente.getAlertas().size(); i++) {
                        if (estoqueIngrediente.getAlertas().get(i).getTipoAlerta() == "Estoque acabando" || estoqueIngrediente.getAlertas().get(i).getTipoAlerta() == "Estoque vazio"){
                            deleteAlerta(estoqueIngrediente.getAlertas().get(i).getIdAlerta());
                            return estoqueIngrediente;
                        }
                    }
                }
            }
            if(estoqueIngrediente.getUnitario() == null){
                if (estoqueIngrediente.getValorTotal() >= 0 && estoqueIngrediente.getValorTotal() < 2){
                    for (int i = 0; i < estoqueIngrediente.getAlertas().size(); i++) {
                        if (estoqueIngrediente.getAlertas().get(i).getTipoAlerta() == "Estoque vazio"){
                            deleteAlerta(estoqueIngrediente.getAlertas().get(i).getIdAlerta());
                        }
                    }
                    Alerta alerta = new Alerta();
                    alerta.setTipoAlerta("Estoque acabando");
                    alerta.setEstoqueIngrediente(estoqueIngrediente);
                    estoqueIngrediente.getAlertas().add(alerta);
                    saveAlerta(alerta);
                    return estoqueIngrediente;
                }
                if (estoqueIngrediente.getValorTotal() >= 2){
                    for (int i = 0; i < estoqueIngrediente.getAlertas().size(); i++) {
                        if (estoqueIngrediente.getAlertas().get(i).getTipoAlerta() == "Estoque acabando" || estoqueIngrediente.getAlertas().get(i).getTipoAlerta() == "Estoque vazio"){
                            deleteAlerta(estoqueIngrediente.getAlertas().get(i).getIdAlerta());
                        }
                    }
                }
            }
        }
        else {

        }
        return estoqueIngrediente;
    }

    public String tipoAlertaValorTotal(EstoqueIngrediente estoqueIngrediente){
        if(estoqueIngrediente.getUnitario() != null){
            if (estoqueIngrediente.getValorTotal() <= 0){
                return "Estoque vazio";
            }
            if (estoqueIngrediente.getValorTotal() <= 60){
                return "Estoque acabando";
            }
        }
        if(estoqueIngrediente.getUnitario() == null){
            if (estoqueIngrediente.getValorTotal() <= 0){
                return "Estoque vazio";
            }
            if (estoqueIngrediente.getValorTotal() <= 2){
                return "Estoque acabando";
            }
        }
        return null;

    }
}