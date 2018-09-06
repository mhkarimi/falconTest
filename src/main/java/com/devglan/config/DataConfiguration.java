package com.devglan.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.devglan.repository")
@EnableTransactionManagement
public class DataConfiguration {


    @Autowired
    DataSourceConfiguration dataSourceConfiguration;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String springJpaHibernateDdlAuto;


//
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceConfiguration.getDriverClassName());
        dataSource.setUrl(dataSourceConfiguration.getUrl());
        dataSource.setUsername(dataSourceConfiguration.getUsername());
        dataSource.setPassword(dataSourceConfiguration.getPassword());
        return dataSource;
    }

    @Bean
    EntityManagerFactory entityManagerFactory(){
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.devglan.*");
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        Properties properties = new Properties();
       // properties.put("hibernate.hbm2ddl.auto",springJpaHibernateDdlAuto);
       // properties.put("hibernate.dialect","org.hibernate.dialect.MySQLInnoDBDialect");
        //properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");

        factoryBean.setJpaProperties(properties);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }


    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        return jpaTransactionManager;
    }



}
