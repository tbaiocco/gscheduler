package com.nuovonet.gscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.client.RestTemplate;

import com.nuovonet.gscheduler.util.HibernateAwareObjectMapper;

@SpringBootApplication
@ComponentScan(basePackages="com.nuovonet.gscheduler")
public class Application {
	
    public static void main(String[] args) { 
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
    
    @SuppressWarnings("rawtypes")
	@Bean
    public org.springframework.web.client.RestTemplate restTemplate() {
    	
    	HttpMessageConverters msgConverters = new HttpMessageConverters(
    			new org.springframework.http.converter.ByteArrayHttpMessageConverter(),
    			new org.springframework.http.converter.StringHttpMessageConverter(),
    			new org.springframework.http.converter.ResourceHttpMessageConverter(),
    			new org.springframework.http.converter.xml.SourceHttpMessageConverter(),
    			new org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter(),
    			new org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter(),
    			new MappingJackson2HttpMessageConverter(
    					new HibernateAwareObjectMapper()
    					)
    			);
    	
    	RestTemplate rt = new org.springframework.web.client.RestTemplate();
    	rt.setMessageConverters(msgConverters.getConverters());
    	return rt;
    }
}
