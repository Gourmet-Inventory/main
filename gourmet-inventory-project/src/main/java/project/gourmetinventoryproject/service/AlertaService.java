package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.dto.alerta.TiposAlertasDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.AlertaRepository;
import project.gourmetinventoryproject.repository.EstoqueIngredienteRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private EmpresaService empresaService;

    @Transactional()
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
    @Transactional()
    public List<Alerta> getAllAlerta() {
        return alertaRepository.findAll();
    }
    @Transactional()
    public TiposAlertasDto getTipoAlertas(Long idEmpresa){
        List<Alerta> alertasAll = getAllAlerta(idEmpresa);
        TiposAlertasDto tiposAlertasDto = new TiposAlertasDto();
        int estoqueAcabandoQtd = 0;
        int estoqueVazioQtd = 0;
        int diaChecagemQtd = 0;
        int dataProximaQtd = 0;
        for (int i = 0; i < alertasAll.size(); i++) {
            if (alertasAll.get(i).getTipoAlerta().equals("Estoque vazio")) {
                estoqueVazioQtd++;
            }
            if (alertasAll.get(i).getTipoAlerta().equals("Estoque acabando")) {
                estoqueAcabandoQtd++;
            }
            if (alertasAll.get(i).getTipoAlerta().equals("Dia de Checagem")) {
                diaChecagemQtd++;
            }
            if (alertasAll.get(i).getTipoAlerta().equals("Data Proxima")) {
                dataProximaQtd++;
            }
        }
        System.out.println(dataProximaQtd );
        System.out.println(diaChecagemQtd );
        System.out.println(estoqueAcabandoQtd );
        System.out.println(estoqueVazioQtd );
        int somaTotalAlertas = estoqueAcabandoQtd + estoqueVazioQtd +diaChecagemQtd + dataProximaQtd;
        if (somaTotalAlertas > 0){
            tiposAlertasDto.setEstoqueAcabandoQtd(estoqueAcabandoQtd);
            tiposAlertasDto.setEstoqueVazioQtd(estoqueVazioQtd);
            tiposAlertasDto.setDiaChecagemQtd(diaChecagemQtd);
            tiposAlertasDto.setDataProximaQtd(dataProximaQtd);
            tiposAlertasDto.setSomaTotalAlertar(somaTotalAlertas);
        }
        return tiposAlertasDto;
    }

    @Transactional()
    public void deleteAlerta(Long id) {
        if (alertaRepository.findById(id).orElse(null) == null){
            throw new IdNotFoundException();
        }
        alertaRepository.deleteById(id);
    }
    @Transactional()
    public void createAlerta(EstoqueIngrediente estoqueIngrediente){
        Alerta alerta = new Alerta();
        alerta.setTipoAlerta(tipoAlertaValorTotal(estoqueIngrediente));
        alerta.setEstoqueIngrediente(estoqueIngrediente);
        estoqueIngrediente.getAlertas().add(alerta);
        saveAlerta(alerta);
    }
    public void saveAlerta(Alerta alerta){
        alertaRepository.save(alerta);
    }



    @Transactional()
    public EstoqueIngrediente checarAlerta(EstoqueIngrediente estoqueIngrediente) {
        if (estoqueIngrediente.getAlertas().isEmpty() && tipoAlertaValorTotal(estoqueIngrediente) != null) {
            System.out.println("Entrando na lista vazia e precisa de alerta");
            createAlerta(estoqueIngrediente);
            System.out.println("Alerta criado");
            return estoqueIngrediente;
        } else {
            System.out.println("Entrando no else");
            Iterator<Alerta> iterator = estoqueIngrediente.getAlertas().iterator();

            while (iterator.hasNext()) {
                Alerta alerta = iterator.next();
                if (!alerta.getTipoAlerta().equals("Data Proxima") && !alerta.getTipoAlerta().equals("Dia Checagem")) {
                    System.out.println("checagem maxima");
                    if (estoqueIngrediente.getValorTotal() > 60) {
                        iterator.remove();
                        deleteAlerta(alerta.getIdAlerta());
                    } else if (estoqueIngrediente.getValorTotal() <= 0) {
                        alerta.setTipoAlerta("Estoque vazio");
                    } else {
                        alerta.setTipoAlerta("Estoque acabando");
                    }
                }
            }
            System.out.println("Saindo do for de checagem estoque");
        }
        return estoqueIngrediente;
    }
    @Transactional()
    public String tipoAlertaValorTotal(EstoqueIngrediente estoqueIngrediente){
        if(estoqueIngrediente.getUnitario() == null){
            if (estoqueIngrediente.getValorTotal() <= 0){
                return "Estoque vazio";
            }
            if (estoqueIngrediente.getValorTotal() <= 60){
                return "Estoque acabando";
            }
            return null;
        }
        else{
            if (estoqueIngrediente.getValorTotal() <= 0){
                return "Estoque vazio";
            }
            if (estoqueIngrediente.getValorTotal() <= 2){
                return "Estoque acabando";
            }
            return null;
        }

    }
}