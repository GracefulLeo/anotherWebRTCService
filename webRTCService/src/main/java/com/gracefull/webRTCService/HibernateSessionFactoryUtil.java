package com.gracefull.webRTCService;

import com.gracefull.webRTCService.models.Credentials;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Credentials.class);

                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                registryBuilder.applySettings(configuration.getProperties());
                StandardServiceRegistry serviceRegistry = registryBuilder.build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
