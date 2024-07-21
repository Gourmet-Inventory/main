package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.gourmetinventoryproject.controller.EmailController;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Email;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Usuario;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class AlertaJob {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Autowired
    private EmailController emailController;

    @Autowired
    private UsuarioService usuarioService;

    @Scheduled(fixedRate = 86400000)
    public void alertarDiarios() {
        LocalDate dataAtual = LocalDate.now();
        List<EstoqueIngrediente> estoqueIngrediente = estoqueIngredienteService.getAllEstoqueIngredientes();

        for (EstoqueIngrediente ingrediente : estoqueIngrediente) {
            System.out.println("Entrando no for principal");

            if (ingrediente.getAlertas().isEmpty() && tipoAlerta(ingrediente) != null) {
                System.out.println("Entrando na lista vazia e precisa de alerta");
                Alerta alerta = createAlerta(ingrediente);
                System.out.println("Alerta criado");
                emailAlerta(ingrediente,alerta);
            }else {
                System.out.println("Entrando no else");
                Iterator<Alerta> iterator = ingrediente.getAlertas().iterator();

                while (iterator.hasNext()) {
                    Alerta alerta = iterator.next();

                    if (alerta.getTipoAlerta() != null && !alerta.getTipoAlerta().equals("Estoque acabando") && !alerta.getTipoAlerta().equals("Estoque vazio")) {
                        System.out.println("Checagem máxima");
                        if (ingrediente.getDtaAviso().isBefore(dataAtual.plusDays(3))) {
                            System.out.println("Data próxima");
                            emailAlerta(ingrediente,alerta);
                            alerta.setTipoAlerta("Data proximo");
                        } else if (ingrediente.getDtaAviso().isEqual(dataAtual)) {
                            System.out.println("Dia de checagem");
                            emailAlerta(ingrediente,alerta);
                            alerta.setTipoAlerta("Dia checagem");
                        } else {
                            System.out.println("Deletar alerta");
                            iterator.remove();
                            alertaService.deleteAlerta(alerta.getIdAlerta());
                        }
                    }
                }
                System.out.println("Saindo do for de checagem de estoque");
            }
        }
    }
    public void emailAlerta(EstoqueIngrediente ingrediente, Alerta alerta){
        List<Usuario> usuarios = usuarioService.getUsuariosTeste(ingrediente.getEmpresa().getIdEmpresa());
        System.out.println("Lista de usuario pegada");
        for (int i = 0; i < usuarios.size() ; i++) {
            System.out.println("dentro do for");
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
    }

    public String tipoAlerta(EstoqueIngrediente estoqueIngrediente){
        LocalDate dataAtual = LocalDate.now();
        if (estoqueIngrediente.getDtaAviso().isEqual(dataAtual)){
            return "Dia de Checagem";
        }
        if (estoqueIngrediente.getDtaAviso().isBefore(dataAtual.plusDays(3))){
            return "Data Proxima";
        }
        else {
            return null;
        }
    }
    public Alerta createAlerta(EstoqueIngrediente estoqueIngrediente){
        Alerta alerta = new Alerta();
        alerta.setEstoqueIngrediente(estoqueIngrediente);
        alerta.setTipoAlerta(tipoAlerta(estoqueIngrediente));
        estoqueIngrediente.getAlertas().add(alerta);
        alertaService.saveAlerta(alerta);
        return alerta;
    }
}
