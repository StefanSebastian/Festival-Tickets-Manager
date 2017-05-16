package festival.Hibernate;

import festival.Domain.User;
import festival.Interfaces.IUserRepository;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by Sebi on 03-May-17.
 */
public class RepositoryUserHibernate implements IUserRepository {
    private HibernateUtils hibernateUtils;

    public RepositoryUserHibernate(HibernateUtils hibernateUtils){
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(String s, User entity) {

    }

    @Override
    public User getById(String s) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getUserForLogin(String username, String password) {
        User user = null;

        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String queryDef = "FROM User U WHERE U.username = :username AND U.password = :password";
            Query query = session.createQuery(queryDef);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<User> results = query.list();

            if (results.size() == 1){
                user = results.get(0);
            }

            transaction.commit();
        } catch (RuntimeException ex){
            if (transaction != null){
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return user;
    }
}
