///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.napalmvin.neuro_log_vui;
//
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.logging.Logger;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.web.context.WebApplicationContext;
//
///**
// *
// * @author LOL
// */
//@Configuration
////@ImportResource("classpath:jpa-config.xml")
//@PropertySource("classpath:/app.properties")
//@ComponentScan//(basePackages = {"org.napalmvin.neuro_log."})
//@EnableTransactionManagement
//public class PersistanceConfig {
//    @Autowired
//    private ApplicationContext ac;
//    @Autowired
//    private WebApplicationContext wac;
//    
//    private Logger log = Logger.getLogger(PersistanceConfig.class.getName());
//
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName("org.postgresql.Driver");
//        ds.setUrl("jdbc:postgresql://localhost:5432/NEURO_DB");
//        ds.setUsername("napalmvin");
//        ds.setPassword("ja256va");
//        return ds;
//    }
//
//    @Bean
//    public HibernateJpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setDatabase(Database.POSTGRESQL);
//        adapter.setShowSql(true);
//        adapter.setGenerateDdl(true);
//        log.info(">>>7");
//        printClassPath();
//       // adapter.setDatabasePlatform(org.hibernate.dialect.ProgressDialect.class.getName());
//        return adapter;
//    }
//
//    private void printClassPath() {
//        TreeMap<String,ClassLoader> tm=new TreeMap<>();
//        tm.put(">>>>SystemClassLoader",ClassLoader.getSystemClassLoader());
//        tm.put(">>>>AppContextLoader",ac.getClassLoader());
//        tm.put(">>>>WebAppContextLoader",wac.getClassLoader());
//        for (Map.Entry<String, ClassLoader> entry : tm.entrySet()) {
//            log.info(entry.getKey());
//            URL[] urls = ((URLClassLoader) entry.getValue()).getURLs();
//         for (URL url : urls) {
//            log.info(url.getFile());
//        }  
//        }
//        
//    }
//
//    @Autowired
//    @Bean
//    public LocalContainerEntityManagerFactoryBean emf(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource);
//        emf.setJpaVendorAdapter(jpaVendorAdapter);
//        emf.setPackagesToScan("org.napalmvin.neuro_log.entities");
//        printClassPath();
//        return emf;
//    }
//
//    @Bean
//    @Autowired
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
////    public static class EclipseLinkJpaVendorAdapterCustomized extends EclipseLinkJpaVendorAdapter {
////
////        @Override
////        public Map<String, Object> getJpaPropertyMap() {
////            Map<String, Object> jpaPropertyMap = super.getJpaPropertyMap();
////            jpaPropertyMap.put("eclipselink.weaving", false);
////            return jpaPropertyMap;
////        }
////
////    }
//}
