package com.github.fluent.hibernate.cfg;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;

import com.github.fluent.hibernate.cfg.strategy.StrategyOptions;

/**
 * Fluent API for a Hibernate session factory configuration and build. The simplest way to create a
 * session factory:
 *
 * <code>
 * HibernateSessionFactory.Builder.configureFromDefaultHibernateCfgXml().createSessionFactory();
 * </code>
 *
 * @author V.Ladynev
 */
public class FluentFactoryBuilder {

    private final ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

    private boolean useHibernateCfgXml = true;

    private String hibernateCfgXmlPath;

    /**
     * Specify a path to the xml configuration (like hibernate.cfg.xml). This method should be used
     * only for a non standard path.
     *
     * @param hibernateCfgXmlPath
     *            a path to the xml configuration. For an example, "config/hibernate.cfg.xml".
     */
    public FluentFactoryBuilder hibernateCfgXml(String hibernateCfgXmlPath) {
        this.hibernateCfgXmlPath = hibernateCfgXmlPath;
        return this;
    }

    /**
     * Specify don't use the xml confriguration (like hibernate.cfg.xml).
     *
     */
    public FluentFactoryBuilder dontUseHibernateCfgXml() {
        this.useHibernateCfgXml = false;
        return this;
    }

    public FluentFactoryBuilder database(DatabaseOptions options) {
        configurationBuilder.addDatabaseOptions(options);
        return this;
    }

    public FluentFactoryBuilder annotatedClasses(Class<?>... annotatedClasses) {
        configurationBuilder.addAnnotatedClasses(annotatedClasses);
        return this;
    }

    public FluentFactoryBuilder scanPackages(String... packagesToScan) {
        configurationBuilder.addPackagesToScan(packagesToScan);
        return this;
    }

    public FluentFactoryBuilder hibernatePropertiesFromFile(File propertiesFilePath) {
        configurationBuilder.addPropertiesFromFile(propertiesFilePath);
        return this;
    }

    public FluentFactoryBuilder hibernatePropertiesFromClassPathResource(
            String classPathResourcePath) {
        configurationBuilder.addPropertiesFromClassPath(classPathResourcePath);
        return this;
    }

    /**
     * Use the default Hibernate5NamingStrategy.
     */
    public FluentFactoryBuilder useNamingStrategy() {
        configurationBuilder.useNamingStrategy();
        return this;
    }

    /**
     * Use the default Hibernate5NamingStrategy with options.
     *
     * @param options
     *            options, to specify a strategy behaviour
     */
    public FluentFactoryBuilder useNamingStrategy(StrategyOptions options) {
        configurationBuilder.useNamingStrategy(options);
        return this;
    }

    /**
     * Use an implicit naming strategy.
     *
     * @param strategy
     *            an implicit naming strategy
     */
    public FluentFactoryBuilder useNamingStrategy(ImplicitNamingStrategy strategy) {
        configurationBuilder.useNamingStrategy(strategy);
        return this;
    }

    public static void configureFromExistingSessionFactory(SessionFactory sessionFactory) {
        HibernateSessionFactory.setExistingSessionFactory(sessionFactory);
    }

    /**
     * Build a Hibernate session factory.
     */
    public void build() {
        if (useHibernateCfgXml) {
            configurationBuilder.configure(hibernateCfgXmlPath);
        }

        configureFromExistingSessionFactory(configurationBuilder.buildSessionFactory());
    }

    /**
     * Close a Hibernate session factory.
     */
    public void close() {
        HibernateSessionFactory.closeSessionFactory();
    }

}