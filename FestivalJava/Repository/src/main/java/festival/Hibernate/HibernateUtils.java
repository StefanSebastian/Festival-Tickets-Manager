package festival.Hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Sebi on 03-May-17.
 */
@Component
public class HibernateUtils {
    private static SessionFactory sessionFactory = null;

    public SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            initialize();
        }
        return sessionFactory;
    }

    private void initialize(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e){
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
