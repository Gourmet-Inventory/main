package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class AlertaJob {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;

    @Scheduled(fixedRate = 10000)
    public void alertarDiarios() {
        LocalDate dataAtual = LocalDate.now();
        List<EstoqueIngrediente> estoqueIngrediente = estoqueIngredienteService.getAllEstoqueIngredientes();

        for (EstoqueIngrediente ingrediente : estoqueIngrediente) {
            System.out.println("Entrando no for principal");

            if (ingrediente.getAlertas().isEmpty() && tipoAlerta(ingrediente) != null) {
                System.out.println("Entrando na lista vazia e precisa de alerta");
                alertaService.createAlerta(ingrediente);
                System.out.println("Alerta criado");
            } else {
                System.out.println("Entrando no else");
                Iterator<Alerta> iterator = ingrediente.getAlertas().iterator();

                while (iterator.hasNext()) {
                    Alerta alerta = iterator.next();

                    if (alerta.getTipoAlerta() != null &&
                            !alerta.getTipoAlerta().equals("Estoque acabando") &&
                            !alerta.getTipoAlerta().equals("Estoque vazio")) {

                        System.out.println("Checagem máxima");

                        if (ingrediente.getDtaAviso().isBefore(dataAtual.plusDays(3))) {
                            System.out.println("Data próxima");
                            alerta.setTipoAlerta("Data proximo");
                        } else if (ingrediente.getDtaAviso().isEqual(dataAtual)) {
                            System.out.println("Dia de checagem");
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
        System.out.println("Alertas criados");
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
}
