package ru.clevertec.ecl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@Profile("prod")
public class SpringDBConfig {

    @Value("${postgresql_driver}")
    private String driverClassName;
    @Value("${postgresql_url}")
    private String url;
    @Value("${postgresql_username}")
    private String userName;
    @Value("${postgresql_password}")
    private String password;

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(driverClassName);
        driver.setUrl(url);
        driver.setUsername(userName);
        driver.setPassword(password);
        return driver;
    }

    private Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProp.put("hibernate.format sql", true);
        hibernateProp.put("hibernate.use sql comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        return hibernateProp;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setHibernateProperties(
                hibernateProperties());
        sessionFactoryBean.setPackagesToScan(
                "ru.clevertec.ecl.model");
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new HibernateTransactionManager(sessionFactory());
    }
}
