package com.db.db.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "secondEntityManagerFactoryBean", basePackages = {"com.db.db.product.repositories"}, transactionManagerRef = "secondTransactionManager")
@EnableTransactionManagement
public class ProductConfig {
    @Autowired
    private Environment environment;
    @Primary
    @Bean(name = "secondDataSource")
    public DataSource dataSource() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("spring.db1.datasource.url"));
        driverManagerDataSource.setUsername(environment.getProperty("spring.db1.datasource.username"));
        driverManagerDataSource.setPassword(environment.getProperty("spring.db1.datasource.password"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("spring.db1.datasource.driver-class-name"));
        return  driverManagerDataSource;
    }

    @Primary
    @Bean(name = "secondEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter adapter= new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(properties);

        localContainerEntityManagerFactoryBean.setPackagesToScan("com.db.db.product.entity");
        return localContainerEntityManagerFactoryBean;
    }

    @Primary
    @Bean("secondTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;

    }
}
