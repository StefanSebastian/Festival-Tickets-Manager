package Repository.Hibernate;

import Domain.Transaction;
import Domain.User;
import Repository.Interfaces.IUserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Sebi on 03-May-17.
 */
public class RepositoryUserHibernate implements IUserRepository {
    private HibernateUtils hibernateUtils;

    public RepositoryUserHibernate(){
        hibernateUtils = new HibernateUtils();
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
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        String queryDef = "FROM User U WHERE U.username = :username AND U.password = :password";
        Query query = session.createQuery(queryDef);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> results = query.list();

        transaction.commit();
        session.close();

        if (results.size() == 1){
            return results.get(0);
        }

        return null;
    }
}
