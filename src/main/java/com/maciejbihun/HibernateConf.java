package com.maciejbihun;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author BHN
 */
@Configuration
@EnableTransactionManagement
public class HibernateConf {

    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        String[] activeProfiles = environment.getActiveProfiles();
        String testProfile = "test";
        if (activeProfiles.length == 0){
            em.setDataSource(dataSource());
            em.setJpaProperties(hibernateProperties());
        } else if (activeProfiles[0].equals(testProfile)){
            em.setDataSource(testDataSource());
            em.setJpaProperties(testHibernateProperties());
        }
        em.setPackagesToScan(new String[] { "com.maciejbihun.models" });
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=you-obligate-db;integratedSecurity=true;");
        dataSource.setUsername("Maciek1234");
        dataSource.setPassword("Maciek1234");
        return dataSource;
    }

    /**
     * DataSource only for tests.
     */
    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }


    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
        hibernateProperties.setProperty("hibernate.show_sql", Boolean.TRUE.toString());
        /*hibernateProperties.setProperty(
                "hibernate.cache.use_query_cache", Boolean.TRUE.toString());
        hibernateProperties.setProperty(
                "hibernate.cache.use_second_level_cache", Boolean.TRUE.toString());
        hibernateProperties.setProperty(
                "hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");*/
        return hibernateProperties;
    }

    private Properties testHibernateProperties() {
        Properties hibernateProperties = new Properties();
        // hibernateProperties.setProperty("hibernate.show_sql", Boolean.TRUE.toString());
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty(
                "spring.jpa.hibernate.ddl-auto", "false");
        return hibernateProperties;
    }
}
