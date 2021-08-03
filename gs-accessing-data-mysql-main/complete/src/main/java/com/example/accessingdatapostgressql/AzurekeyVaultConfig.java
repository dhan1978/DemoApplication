package com.example.accessingdatapostgressql;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.DeletedSecret;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
@Configuration
@EnableJpaRepositories(basePackages = {
		"com.example.accessingdatapostgressql" }, entityManagerFactoryRef = "dbEntityManager", transactionManagerRef = "dbTransactionManager")

public class AzurekeyVaultConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(AzurekeyVaultConfig.class);
	@Value("${KEY_VAULT_NAME}")
	 String keyVaultName ;
	 String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";
	 
	 SecretClient secretClient;
	 
	@Bean(name = "secretClient")
	public  SecretClient getsecretClient() {
		LOGGER.info("keyVaultUri", "-------------------------------"+keyVaultUri);
		
	 secretClient = new SecretClientBuilder()
		    .vaultUrl(keyVaultUri)
		    .credential(new DefaultAzureCredentialBuilder().build())
		    .buildClient(); 
	return secretClient;
	}
	@Bean(name = "dbDataSource")
	
	public DataSource dbDataSource() {
		secretClient=getsecretClient();
		LOGGER.info("++++++++++++++++++++++++++++++++++++ FDATA ++++++++++++++++++++++++++++++++++++");
		LOGGER.info("driver class: [{}]", secretClient.getSecret("spring-datasource-driver-class-name").getValue());
		
		LOGGER.info("url class: [{}]", secretClient.getSecret("spring-datasource-url").getValue());
		
		LOGGER.info("username: [{}]", secretClient.getSecret("spring-datasource-username").getValue());
		
		LOGGER.info("password: [{}]", secretClient.getSecret("spring-datasource-password").getValue());
		
		LOGGER.info("------------------------------------ DATA ------------------------------------");
		secretClient.getSecret("dhanankeyvault");
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName(secretClient.getSecret("spring-datasource-driver-class-name").getValue());
	dataSource.setUrl(secretClient.getSecret("spring-datasource-url").getValue());
	dataSource.setUsername(secretClient.getSecret("spring-datasource-username").getValue());
	dataSource.setPassword(secretClient.getSecret("spring-datasource-password").getValue());
	return dataSource;
}
	
	@Bean(name = "dbTransactionManager")
	public PlatformTransactionManager dbTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(dbEntityManager().getObject());
		return transactionManager;
	}
	@Bean(name = "dbEntityManager")
	public LocalContainerEntityManagerFactoryBean dbEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dbDataSource());
		em.setPackagesToScan(new String[] { "com.example.accessingdatapostgressql.Users" });
		em.setPersistenceUnitName("dbEntityManager");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		// HashMap<String, Object> properties = new HashMap<>();
		// properties.put("hibernate.dialect",
		// env.getProperty("hibernate.dialect"));
		// properties.put("hibernate.show-sql",
		// env.getProperty("jdbc.show-sql"));
		// em.setJpaPropertyMap(properties);
		return em;
	}
		   
}
