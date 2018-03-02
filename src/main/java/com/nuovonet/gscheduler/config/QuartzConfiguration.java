package com.nuovonet.gscheduler.config;

import java.util.List;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.nuovonet.gscheduler.quartz.QuartzJobFactory;
import com.nuovonet.gscheduler.util.AppUtil;

@Configuration
public class QuartzConfiguration {

	@Value("${org.quartz.scheduler.instanceName}")
    private String instanceName;
 
    @Value("${org.quartz.scheduler.instanceId}")
    private String instanceId;
 
    @Value("${org.quartz.threadPool.threadCount}")
    private String threadCount;
 
    @Value("${job.startDelay}")
    private Long startDelay;
 
    @Value("${job.repeatInterval}")
    private Long repeatInterval;
 
    @Value("${job.description}")
    private String description;
 
    @Value("${job.key}")
    private String key;
 
//    @Autowired
//    private DataSource dataSource;
    
    @Autowired
	List<Trigger> listOfTrigger;
 
    @Bean
    public org.quartz.spi.JobFactory jobFactory(ApplicationContext applicationContext) {
 
        QuartzJobFactory sampleJobFactory = new QuartzJobFactory();
        sampleJobFactory.setApplicationContext(applicationContext);
        return sampleJobFactory;
    }
 
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
 
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
 
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory(applicationContext));
 
//        factory.setDataSource(dataSource);
 
        factory.setQuartzProperties(quartzProperties());
//        factory.setTriggers(testeJobTrigger().getObject());
        
        
        if (!AppUtil.isObjectEmpty(listOfTrigger)) {
			factory.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));
		}
        
        return factory;
    }
    
    @Bean
	public Properties quartzProperties()/* throws IOException*/ {
    	Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.instanceName",instanceName);
        quartzProperties.setProperty("org.quartz.scheduler.instanceId",instanceId);
        quartzProperties.setProperty("org.quartz.threadPool.threadCount",threadCount);
        return quartzProperties;
        
//		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//		propertiesFactoryBean.afterPropertiesSet();
//		return propertiesFactoryBean.getObject();
	}
 
//    @Bean(name = "testeJobTrigger")
//    public SimpleTriggerFactoryBean testeJobTrigger() {
// 
// 
//        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
//        factoryBean.setJobDetail(testeJobDetails().getObject());
//        factoryBean.setStartDelay(startDelay);
//        factoryBean.setRepeatInterval(repeatInterval);
//        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
//        return factoryBean;
//    }
 
//    @Bean(name = "testeJobDetails")
//    public JobDetailFactoryBean testeJobDetails() {
// 
//        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
//        jobDetailFactoryBean.setJobClass(TesteJob.class);
//        jobDetailFactoryBean.setDescription(description);
//        jobDetailFactoryBean.setDurability(true);
//        jobDetailFactoryBean.setName(key);
// 
//        return jobDetailFactoryBean;
//    }
    
    public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(0L);
		factoryBean.setRepeatInterval(pollFrequencyMs);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		// in case of misfire, ignore all missed triggers and continue :
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
		return factoryBean;
	}

	// Use this method for creating cron triggers instead of simple triggers:
	public static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		return factoryBean;
	}

	@SuppressWarnings("rawtypes")
	public static JobDetailFactoryBean createJobDetail(Class jobClass, String desc, String key) {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(jobClass);
		// job has to be durable to be stored in DB:
		factoryBean.setDescription(desc);
		factoryBean.setDurability(true);
		factoryBean.setName(key);
		return factoryBean;
	}
    
}
