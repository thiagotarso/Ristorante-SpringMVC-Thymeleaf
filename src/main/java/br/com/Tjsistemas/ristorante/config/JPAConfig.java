package br.com.Tjsistemas.ristorante.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.repository.Clientes;


@Configuration
@ComponentScan(basePackageClasses = Clientes.class)
@EnableJpaRepositories(basePackageClasses = Clientes.class, enableDefaultTransactions= false)
@EnableTransactionManagement
public class JPAConfig {

	@Bean
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		return dataSourceLookup.getDataSource("jdbc/Ristorante");
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");

		return hibernateJpaVendorAdapter;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean Factory = new LocalContainerEntityManagerFactoryBean();
		Factory.setDataSource(dataSource);
		Factory.setJpaVendorAdapter(jpaVendorAdapter);
        Factory.setPackagesToScan(Cliente.class.getPackage().getName());
//        Factory.setMappingResources("sql/consultas-nativas.xml");
		Factory.afterPropertiesSet();
		
		return Factory.getObject();
	}
	
	@Bean 
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
	    JpaTransactionManager transactionManager = new JpaTransactionManager();	
	    transactionManager.setEntityManagerFactory(entityManagerFactory);
	    
	    return transactionManager;
	}
}