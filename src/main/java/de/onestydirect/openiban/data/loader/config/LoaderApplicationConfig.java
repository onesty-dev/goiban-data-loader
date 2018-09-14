package de.onestydirect.openiban.data.loader.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EntityScan("de.onestydirect.openiban.data.loader")
@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
public class LoaderApplicationConfig {

	@Value("${spring.datasource.driverClassName}")
	String driverClassName;

	@Value("${spring.datasource.url}")
	String url;

	@Value("${spring.datasource.username}")
	String username;

	@Value("${spring.datasource.password}")
	String password;

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		return DataSourceBuilder
				.create()
				.username(username)
				.password(password)
				.url(url)
				.driverClassName(driverClassName)
				.build();
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(final DataSource dataSource) {
		final LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.scanPackages("de.onestydirect.openiban.data.loader");
		return sessionBuilder.buildSessionFactory();
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			final SessionFactory sessionFactory) {
		return new HibernateTransactionManager(
				sessionFactory);
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		return initializer;
	}
}
