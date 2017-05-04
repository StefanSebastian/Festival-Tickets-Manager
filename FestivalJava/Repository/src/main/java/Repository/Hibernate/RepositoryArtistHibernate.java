package Repository.Hibernate;

import Domain.Artist;
import Repository.Interfaces.IArtistRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 03-May-17.
 */
public class RepositoryArtistHibernate implements IArtistRepository {
    private HibernateUtils hibernateUtils;

    public RepositoryArtistHibernate(){
        hibernateUtils = new HibernateUtils();
    }

    @Override
    public void save(Artist entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Artist entity) {

    }

    @Override
    public Artist getById(Integer integer) {
        return null;
    }

    @Override
    public List<Artist> getAll() {
        List<Artist> artists = null;

        org.hibernate.Transaction transaction = null;
        try (Session session = hibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String queryH = "FROM Artist";
            Query query = session.createQuery(queryH);
            artists = query.list();

            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return artists;
    }
}
