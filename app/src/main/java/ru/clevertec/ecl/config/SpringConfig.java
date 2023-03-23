package ru.clevertec.ecl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan({"ru.clevertec.ecl" })
@PropertySource("classpath:application.properties")
public class SpringConfig {

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

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
