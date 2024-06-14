package project.gourmetinventoryproject.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.gourmetinventoryproject.service.AlertaJob;

@Configuration  // Indica que esta classe contém definições de beans Spring
public class QuartzJobConfig {

    @Bean  // Define um bean que será gerenciado pelo Spring
    public JobDetail jobDetail() {
        return JobBuilder.newJob(AlertaJob.class)  // Cria um novo job do tipo MyQuartzJob
                .withIdentity("AlertaJob")  // Define a identidade (nome) do job
                .storeDurably()  // Indica que o job será armazenado mesmo que não haja triggers associadas a ele
                .build();  // Constrói o JobDetail e o retorna como um bean Spring
    }

    @Bean  // Define um bean que será gerenciado pelo Spring
    public Trigger trigger(JobDetail jobDetail) {  // Recebe o JobDetail como parâmetro
        return TriggerBuilder.newTrigger()  // Cria um novo trigger
                .forJob(jobDetail)  // Associa este trigger ao job especificado
                .withIdentity("AlertaTrigger")  // Define a identidade (nome) do trigger
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()  // Define uma programação simples para o trigger
                        .withIntervalInMinutes(1)  // Define o intervalo de execução para 10 minutos
                        .repeatForever())  // Define que o job será repetido indefinidamente
                .build();  // Constrói o Trigger e o retorna como um bean Spring
    }
}
