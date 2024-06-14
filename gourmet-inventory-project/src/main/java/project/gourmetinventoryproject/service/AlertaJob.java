package project.gourmetinventoryproject.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.time.LocalDate;
import java.util.List;

@Component
public class AlertaJob implements Job{
    @Autowired
    private AlertaService alertaService;


    @Autowired
    private EstoqueIngredienteService estoqueIngredienteService;


    @Async
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<Alerta> alertas = alertaService.getAllAlerta();
        List<EstoqueIngrediente> estoqueIngredientes = estoqueIngredienteService.getAllEstoqueIngredientes();
        if (estoqueIngredientes.isEmpty()) {
            System.out.println("Sem alertas para criar");
        }
        else {
            Boolean aletaExiste = false;
            for (int i = 0; i < estoqueIngredientes.size(); i++) {
                aletaExiste = false;
                if (!alertas.isEmpty()){
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
                    if (tipoAlerta(estoqueIngredientes.get(i)) != null && !aletaExiste) {
                        Alerta alerta = new Alerta();
                        alerta.setTipoAlerta(tipoAlerta(estoqueIngredientes.get(i)));
                        alerta.setEstoqueIngrediente(estoqueIngredientes.get(i));
                        alertaService.createAlerta(alerta);
                    }
                }
                else {
                    if (tipoAlerta(estoqueIngredientes.get(i)) != null && !aletaExiste) {
                        Alerta alerta = new Alerta();
                        alerta.setTipoAlerta(tipoAlerta(estoqueIngredientes.get(i)));
                        alerta.setEstoqueIngrediente(estoqueIngredientes.get(i));
                        alertaService.createAlerta(alerta);
                    }
                }
            }
            System.out.println("alertas criados");
        }

    }
    public String tipoAlerta(EstoqueIngrediente estoqueIngrediente){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataTresDiasDepois = dataAtual.plusDays(3);
        if (estoqueIngrediente.getDtaAviso().isEqual(dataAtual)){
            return "Dia de Checagem";
        }
        if (estoqueIngrediente.getDtaAviso().isBefore(dataTresDiasDepois)){
            return "Data Proxima";
        }
        if (estoqueIngrediente.getValorTotal() <= 10){
            return "Estoque vazio";
        }
        if (estoqueIngrediente.getValorTotal() <= 200){
            return "Estoque acabando";
        }
        else {
            return null;
        }
    }
}
