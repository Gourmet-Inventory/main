package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gourmetinventoryproject.controller.EmailController;
import project.gourmetinventoryproject.domain.*;
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

    @Autowired
    private EmailController emailController;

    @Autowired
    private UsuarioService usuarioService;

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
    public Alerta createAlerta(EstoqueIngrediente estoqueIngrediente){
        Alerta alerta = new Alerta();
        alerta.setTipoAlerta(tipoAlertaValorTotal(estoqueIngrediente));
        alerta.setEstoqueIngrediente(estoqueIngrediente);
        estoqueIngrediente.getAlertas().add(alerta);
        saveAlerta(alerta);
        return alerta;
    }
    public void saveAlerta(Alerta alerta){
        alertaRepository.save(alerta);
    }



    @Transactional()
    public EstoqueIngrediente checarAlerta(EstoqueIngrediente estoqueIngrediente) {
        if (estoqueIngrediente.getAlertas().isEmpty() && tipoAlertaValorTotal(estoqueIngrediente) != null) {
            System.out.println("Entrando na lista vazia e precisa de alerta");
            Alerta alerta = createAlerta(estoqueIngrediente);
            System.out.println("Alerta criado");
            emailAlerta(estoqueIngrediente, alerta);
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
                        emailAlerta(estoqueIngrediente, alerta);
                        System.out.println("Email mandado estoque vazio");
                    } else {
                        alerta.setTipoAlerta("Estoque acabando");
                        emailAlerta(estoqueIngrediente, alerta);
                        System.out.println("Email mandado estoque acabando");
                    }
                }
            }
            System.out.println("Saindo do for de checagem estoque");
        }
        return estoqueIngrediente;
    }

    public void emailAlerta(EstoqueIngrediente ingrediente, Alerta alerta){
        List<Usuario> usuarios = usuarioService.getUsuariosTeste(ingrediente.getEmpresa().getIdEmpresa());
        System.out.println("Lista de usuario pegada");
        for (int i = 0; i < usuarios.size() ; i++) {
            if (usuarios.get(i).getCargo().equals("administrador")){
                emailController.sendEmail(new Email(usuarios.get(i).getEmail(),"Alerta "+alerta.getTipoAlerta(),
                        """
                        ALERTA 
                      --------------------------------------------------------------------------------
                      O estoque de %s está em estado de alerta devido a %s.
                      
                      Este aviso indica que medidas podem ser necessárias nas próximas 24 horas para garantir que não haja problemas no sistema!
                      
                      Por favor, verifique o estoque imediatamente e tome as ações necessárias para evitar problemas maiores.
                      
                      --------------------------------------------------------------------------------
                      Obrigado pela sua atenção,
                      Equipe Gourmet Inventory
                      """.formatted(ingrediente.getNome(), alerta.getTipoAlerta())));
                System.out.println("email enviador para "+ usuarios.get(i).getEmail());
            }
            else{
                System.out.println("Usuario "+usuarios.get(i).getEmail()+" nao admin");
            }
        }
        System.out.println("saindo for email");
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