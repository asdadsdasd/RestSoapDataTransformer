package ru.kozarez.restapp.configurations;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
        basePackages = {"ru.kozarez.restapp"}
)
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
public class HibernateConf {
    @Autowired
    private Environment environment;

    public HibernateConf() {
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(this.dataSource());
        sessionFactory.setPackagesToScan(new String[]{"ru.kozarez.restapp.entities"});
        sessionFactory.setHibernateProperties(this.hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(this.environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(this.environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(this.environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(this.sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", this.environment.getRequiredProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", this.environment.getRequiredProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", this.environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return hibernateProperties;
    }
}
