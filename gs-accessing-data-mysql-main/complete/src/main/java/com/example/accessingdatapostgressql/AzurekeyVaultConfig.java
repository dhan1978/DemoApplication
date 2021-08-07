package com.example.accessingdatapostgressql;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;

import com.azure.identity.DefaultAzureCredentialBuilder;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.example.accessingdatapostgressql.repository" }, entityManagerFactoryRef = "dbEntityManager", transactionManagerRef = "dbTransactionManager")

public class AzurekeyVaultConfig {

	@Autowired
	private Environment env;
	private static final Logger LOGGER = LoggerFactory.getLogger(AzurekeyVaultConfig.class);
	@Value("${azure.keyvault.uri}")
	private String keyVaultUri;

	Properties config = new Properties();

	@Value("${azure.keyvault.client-id}")
	private String AZURE_CLIENT_ID;

	@Value("${azure.keyvault.tenant-id}")
	private String AZURE_TENANT_ID;
	@Value("${azure.keyvault.client-key}")
	private String AZURE_CLIENT_SECRET;

	// String keyVaultUri ="https://" + keyVaultName + ".vault.azure.net";

	// "https://" + "keyVaultName" + ".vault.azure.net";

	SecretClient secretClient;
	/*
	 * /----------------------- public AzureResourceManager
	 * getazureResourceManager() { AzureResourceManager azureResourceManager =
	 * AzureResourceManager.authenticate( new
	 * DefaultAzureCredentialBuilder().build(), new
	 * AzureProfile(AzureEnvironment.AZURE)) .withDefaultSubscription(); return
	 * azureResourceManager; }
	 */

	@Bean(name = "secretClient")
	public SecretClient getsecretClient() {

		// if(keyVaultName==null||keyVaultName =="") {
		// this.keyVaultUri ="https://dhanankeyvault.vault.azure.net";
		// }

		// System.setProperties(config);
		// config.put(config..PROPERTY_AZURE_TENANT_ID, AZURE_TENANT_ID);
		LOGGER.info("keyVaultUri", "-------------------------------" + keyVaultUri);
		// AzureServiceTokenProvider azureServiceTokenProvider = new
		// AzureServiceTokenProvider();
		// KeyVaultClient keyVaultClient = new KeyVaultClient(new
		// KeyVaultClient.AuthenticationCallback(azureServiceTokenProvider.KeyVaultTokenCallback));
		secretClient = new SecretClientBuilder().vaultUrl(keyVaultUri)
				.credential(new DefaultAzureCredentialBuilder().build()).buildClient();

		return secretClient;
	}

	@Bean(name = "dataSource")

	public DataSource dbDataSource() {
		secretClient = getsecretClient();
		String className = secretClient.getSecret("spring-datasource-driver-class-name").getValue();
		String dbUrl = secretClient.getSecret("spring-datasource-urll").getValue();
		String userName = secretClient.getSecret("spring-datasource-username").getValue();
		String password = secretClient.getSecret("spring-datasource-password").getValue();
		LOGGER.info("++++++++++++++++++++++++++++++++++++ DATA ++++++++++++++++++++++++++++++++++++");
		LOGGER.info("driver class: [{}]", env.getProperty("spring-datasource-driver-class-name"));

		LOGGER.info("url class: [{}]", System.getenv("spring-datasource-urll"));
		LOGGER.info("url class: [{}]", System.getenv("spring-datasource-urll"));

		LOGGER.info("username: [{}]", env.getProperty("spring-datasource-username"));

		LOGGER.info("password: [{}]", env.getProperty("spring-datasource-password"));
		LOGGER.info("url of key vault class: [{}]", secretClient.getVaultUrl());
		LOGGER.info("driver sect class class: [{}]", className);

		LOGGER.info("url sec class url: [{}]", dbUrl);

		LOGGER.info("sec username: [{}]", userName);

		LOGGER.info("sec password: [{}]", password);

		LOGGER.info("------------------------------------ DATA ------------------------------------");
		// secretClient.getSecret("dhanankeyvault");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// dataSource.setDriverClassName(env.getProperty("spring-datasource-driver-class-name"));
		// dataSource.setUrl(env.getProperty("spring-datasource-url"));
		// dataSource.setUsername(env.getProperty("spring-datasource-username"));
		// dataSource.setPassword(env.getProperty("spring-datasource-password"));
		dataSource.setDriverClassName(className);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);

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

		em.setPackagesToScan(new String[] { "com.example.accessingdatapostgressql.entity" });
		em.setPersistenceUnitName("dbEntityManager");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		// HashMap<String, Object> properties = new HashMap<>();
		// properties.put("hibernate.dialect",
		// env.getProperty("spring.jpa.properties.hibernate.dialect"));
		// properties.put("hibernate.show-sql",
		// env.getProperty("jdbc.show-sql"));

		// em.setJpaPropertyMap(properties);

		return em;
	}

}
