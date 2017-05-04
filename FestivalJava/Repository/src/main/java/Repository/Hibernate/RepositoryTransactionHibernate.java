package Repository.Hibernate;

import Domain.Show;
import Domain.Transaction;
import Repository.Interfaces.ITransactionRepository;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Sebi on 04-May-17.
 */
public class RepositoryTransactionHibernate implements ITransactionRepository {
    private HibernateUtils hibernateUtils;

    public RepositoryTransactionHibernate(){
        hibernateUtils = new HibernateUtils();
    }

    @Override
    public void save(Transaction entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Transaction entity) {

    }

    @Override
    public Transaction getById(Integer integer) {
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }

    @Override
    public void saveWithoutId(Transaction transaction) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transactionH = null;
        try {
            transactionH = session.beginTransaction();

            session.save(transaction);

            transactionH.commit();
        } catch (RuntimeException ex){
            if (transactionH != null){
                transactionH.rollback();
            }
        } finally {
            session.close();
        }
    }
}
