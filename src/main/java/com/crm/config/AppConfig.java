package com.crm.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
 * @author sachin
 * This class configures the App web capabilities by  setting the 
 * viewResolvers for redirecting the JSP pages
 * setting up the DataSources,Connection pool for the crm and security persistence
 * setting up the Hibernate properties, Transaction Manager
 *
 */

@Configuration
@EnableWebMvc
@EnableTransactionManagement // returns the jdbc configured datasource
@ComponentScan("com.crm")
@PropertySource({ "classpath:crm-persistence.properties","classpath:security-persistence.properties" })
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;

	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver view = new InternalResourceViewResolver();

		view.setPrefix("/WEB-INF/view/");
		view.setSuffix(".jsp");
		return view;
	}

	@Bean
	public DataSource getCrmDataSource() {
		// create connection pool
		ComboPooledDataSource crmDataSource = new ComboPooledDataSource();
		// set the jdbc driver class
		try {
			crmDataSource.setDriverClass(env.getProperty("jdbc.driver"));

		} catch (Exception exc) {

		}
		crmDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		crmDataSource.setUser(env.getProperty("jdbc.user"));
		crmDataSource.setPassword(env.getProperty("jdbc.password"));
		crmDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		crmDataSource.setMinPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		crmDataSource.setMaxPoolSize(getIntProperty("connection.pool.minPoolSize"));

		crmDataSource.setMaxIdleTime(getIntProperty("connection.pool.initialPoolSize"));

		return crmDataSource;
	}

	@Bean
	public DataSource securityDataSource() {

		// create connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

		// set the jdbc driver class

		try {
			securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
		} catch (PropertyVetoException exc) {
			throw new RuntimeException(exc);
		}

		// set database connection props

		securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
		securityDataSource.setUser(env.getProperty("security.jdbc.user"));
		securityDataSource.setPassword(env.getProperty("security.jdbc.password"));

		// set connection pool props

		securityDataSource.setInitialPoolSize(getIntProperty("security.connection.pool.initialPoolSize"));

		securityDataSource.setMinPoolSize(getIntProperty("security.connection.pool.minPoolSize"));

		securityDataSource.setMaxPoolSize(getIntProperty("security.connection.pool.maxPoolSize"));

		securityDataSource.setMaxIdleTime(getIntProperty("security.connection.pool.maxIdleTime"));

		return securityDataSource;
	}

	// set hibernate properties
	public Properties setHibernateProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate_dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return props;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// create a new session factory object
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		// set the jdbc connection and the connection pooling from the data source
		sessionFactory.setDataSource(getCrmDataSource());
		// set the entity package to be scanned by the hibernate
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));

		// set the hibernate props like dialect, console.
		sessionFactory.setHibernateProperties(setHibernateProperties());

		return sessionFactory;
	}

	// set up the tranction manager
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;

	}

	// overridden method of WebMvcConfigurer
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	// need a helper method
	// read environment property and convert to int

	private int getIntProperty(String propName) {

		String propVal = env.getProperty(propName);

		// now convert to int
		int intPropVal = Integer.parseInt(propVal);

		return intPropVal;
	}

}
