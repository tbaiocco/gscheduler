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
import com.nuovonet.gscheduler.service.RasterService;

@Component
@DisallowConcurrentExecution
public class RasterJob implements Job {
	
	private static final Logger log = LoggerFactory.getLogger(RasterJob.class);

	@Autowired
	private RasterService rasterService;
	
	@Value("${job.frequency.RasterJob:600000}")
	private long frequency;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSS");

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Executando RasterJob > frequencia {}", frequency);
		rasterService.executaAtualizacao();
		log.debug("RasterJob rodou em: {}", dateFormat.format(new Date()));
	}

	@Bean(name = "rasterJobBean")
    public JobDetailFactoryBean sampleJob() {
        return QuartzConfiguration.createJobDetail(this.getClass(), "Raster job", "RasterJob");
    }

    @Bean(name = "rasterJobBeanTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("rasterJobBean") JobDetail jobDetail) {
    	return QuartzConfiguration.createTrigger(jobDetail,frequency);
    }
	
}
