//package SEP4Data.air4you.room;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "entityManagerFactory2",
//        basePackages = { "com.room.RoomRepositorySQLServer" }
//)
//public class ConfigFileSqlServerRoomDataSource {
//    @Primary
//    @Bean(name = "second-datasource")
//    @ConfigurationProperties(prefix = "spring.second-datasource")
//    public DataSource dataSource() {
//        return (DataSource) DataSourceBuilder.create().build();
//    }
//
//    @Primary
//    @Bean(name = "entityManagerFactory2")
//    public LocalContainerEntityManagerFactoryBean
//    entityManagerFactory2(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("second-datasource") DataSource dataSource
//    ) {
//        return builder
//                .dataSource(dataSource)
//                .packages("SEP4Data.air4you")
//                .persistenceUnit("foo")
//                .build();
//    }
//
//    @Primary
//    @Bean(name = "transactionManager2")
//    public PlatformTransactionManager transactionManager(
//            @Qualifier("entityManagerFactory") EntityManagerFactory
//                    entityManagerFactory
//    ) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//
//}
