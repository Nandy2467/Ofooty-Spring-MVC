package com.nandeep.ofooty.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class ConfigHibernate {

	@Autowired
	private Environment environment;
	
	/***
	 * Create Hibernate Session Factory
	 * @return sessionFactory
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] {"com.nandeep.ofooty.modal"});
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	/***
	 * Create mySql data source
	 * @return dataSource
	 */
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
		return dataSource;
	}
	
	/***
	 * Create Hibernate properties definition
	 * @return hibernateProperties
	 */
	private Properties hibernateProperties(){
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
		hibernateProperties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.show-sql"));
		hibernateProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
		return hibernateProperties;
	}
	
	
	/***
	 * Create Hibernate Transaction Manager
	 * @param sf
	 * @return htxManager
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sf){
		HibernateTransactionManager htxManager = new HibernateTransactionManager();
	    htxManager.setSessionFactory(sf);
	    return htxManager;
	}
	
}
