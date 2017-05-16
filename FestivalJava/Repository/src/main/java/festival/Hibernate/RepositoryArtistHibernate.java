package festival.Hibernate;

import festival.Domain.Artist;
import festival.Interfaces.IArtistRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Sebi on 03-May-17.
 */
@Component
public class RepositoryArtistHibernate implements IArtistRepository {
    @Autowired
    private HibernateUtils hibernateUtils;

    public RepositoryArtistHibernate(HibernateUtils hibernateUtils){
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void save(Artist artist) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.save(artist);

            transaction.commit();
        } catch (RuntimeException ex){
            if (transaction != null){
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Integer id) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Artist artist = session.get(Artist.class, id);
            session.delete(artist);

            transaction.commit();
        } catch (RuntimeException ex){
            if (transaction != null){
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Integer id, Artist newArtist) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.update(newArtist);

            transaction.commit();
        } catch (RuntimeException ex){
            if (transaction != null){
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public Artist getById(Integer id) {
        Artist artist = null;

        org.hibernate.Transaction transaction = null;
        try (Session session = hibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            artist = session.get(Artist.class, id);
            transaction.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return artist;
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
