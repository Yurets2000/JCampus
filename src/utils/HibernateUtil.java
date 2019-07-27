package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory factory;

    public static void createSessionFactory(){
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            factory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(factory == null) createSessionFactory();
        return factory;
    }

    public static void shutdown() {
        if(factory != null) factory.close();
    }
}
