package dev.shade.config;


import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import jakarta.transaction.UserTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AtomikosConfig {

    @Bean
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.jta.atomikos.datasource.min-pool-size}") int minPoolSize,
            @Value("${spring.jta.atomikos.datasource.max-pool-size}") int maxPoolSize,
            @Value("${spring.jta.atomikos.datasource.unique-resource-name}") String uniqueResourceName
    ) {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        dataSource.setUniqueResourceName(uniqueResourceName);
        dataSource.setMinPoolSize(minPoolSize);
        dataSource.setMaxPoolSize(maxPoolSize);

        Properties xaProperties = new Properties();
        xaProperties.setProperty("url", url);
        xaProperties.setProperty("user", username);
        xaProperties.setProperty("password", password);

        dataSource.setXaProperties(xaProperties);

        return dataSource;
    }

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(300);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            UserTransaction userTransaction,
            UserTransactionManager atomikosTransactionManager
    ) {
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }

}
