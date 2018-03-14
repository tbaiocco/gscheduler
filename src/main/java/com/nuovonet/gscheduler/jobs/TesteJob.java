package com.nuovonet.gscheduler.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import com.nuovonet.gscheduler.config.QuartzConfiguration;
import com.nuovonet.gscheduler.service.TesteService;
import com.nuovonet.gscheduler.service.WebColSenderService;

@Component
@DisallowConcurrentExecution
public class TesteJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(TesteJob.class);

	@Autowired
	private TesteService testeService;
	
	@Value("${job.frequency.TesteJob}")
	private long frequency;
	
	@Autowired
	private WebColSenderService webColSenderService;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSS");

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("DESABILITADO TesteJob > frequencia {}", frequency);
		webColSenderService.enviaPosicao();
//		log.info("Executando TesteJob > frequencia {}", frequency);
//		testeService.fazAlgo();
//		log.debug("TesteJob rodou em: {}", dateFormat.format(new Date()));
	}

	@Bean(name = "testeJobBean")
    public JobDetailFactoryBean sampleJob() {
        return QuartzConfiguration.createJobDetail(this.getClass(), "Teste job normalzinho", "TesteJob");
    }

    @Bean(name = "testeJobBeanTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("testeJobBean") JobDetail jobDetail) {
    	return QuartzConfiguration.createTrigger(jobDetail,frequency);
    }
	
}
