package com.nuovonet.gscheduler.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.nuovonet.gscheduler.repo" }, entityManagerFactoryRef = "entityManagerFactory")
public class DatabaseConfiguration implements EnvironmentAware {

	private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
//	private RelaxedPropertyResolver jpaProps;
	private RelaxedPropertyResolver dsProps;

	@Bean
	public DataSource dataSource() {
//		return DataSourceBuilder.create().username("exec1").password("1234exrs!").url("jdbc:sqlserver://10.1.1.211:49203;databaseName=TESTEACESSO").driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver").build();
		return DataSourceBuilder.create()
				.username(dsProps.getProperty("username"))
				.password(dsProps.getProperty("password"))
				.url(dsProps.getProperty("url"))
				.driverClassName(dsProps.getProperty("driverClassName"))
			.build();
	}

	public void setEnvironment(Environment environment) {
//		this.jpaProps = new RelaxedPropertyResolver(environment, "spring.jpa.");
		this.dsProps = new RelaxedPropertyResolver(environment, "spring.datasource.");
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			DataSource dataSource) {
		log.info("Configuring EntityManagerFactory for Tenants");
		LocalContainerEntityManagerFactoryBean factory = builder.dataSource(dataSource).persistenceUnit("default")
				.jta(true).packages("com.nuovonet.gscheduler").build();
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setJpaDialect(new HibernateJpaDialect());

		return factory;
	}
	
//	@Bean(name = "dataSourceRouter")
//	public DataSource dataSourceRouter() {
//		return baseDataSource();
//	}
}
