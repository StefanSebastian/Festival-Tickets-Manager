package festival.Hibernate;

import festival.Domain.Show;
import festival.Interfaces.IShowRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 04-May-17.
 */
public class RepositoryShowHibernate implements IShowRepository {
    private HibernateUtils hibernateUtils;

    public RepositoryShowHibernate(HibernateUtils hibernateUtils){
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public void save(Show show) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            session.save(show);

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
    public void delete(Integer idShow) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Show show = session.get(Show.class, idShow);
            session.delete(show);

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
    public void update(Integer idShow, Show newShow) {
        Session session = hibernateUtils.getSessionFactory().openSession();
        org.hibernate.Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Show show = (Show)session.load(Show.class, idShow);
            show = newShow;

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
    public Show getById(Integer idShow) {
        Show show = null;

        org.hibernate.Transaction transaction = null;
        try (Session session = hibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            show = session.get(Show.class, idShow);
            Hibernate.initialize(show.getArtist());
            transaction.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return show;
    }

    @Override
    public List<Show> getAll() {
        List<Show> shows = null;

        org.hibernate.Transaction transaction = null;
        try (Session session = hibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String queryH = "SELECT show FROM Show show " +
                    "left join fetch show.artist artist";
            Query query = session.createQuery(queryH);
            shows = query.list();

            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return shows;
    }

    @Override
    public List<Show> getShowsForArtist(Integer idArtist) {
        List<Show> shows = null;

        org.hibernate.Transaction transaction = null;
        try (Session session = hibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String queryH = "SELECT show FROM Show show " +
                    "left join fetch show.artist artist WHERE artist.idArtist = :idArtist";
            Query query = session.createQuery(queryH);
            query.setParameter("idArtist", idArtist);
            shows = query.list();

            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return shows;
    }

    @Override
    public List<Show> getShowsForDate(String date) {
        List<Show> shows = new ArrayList<>();

        org.hibernate.Transaction transaction = null;
        try (Session session = hibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            String queryH = "SELECT show FROM Show show left join fetch show.artist WHERE show.date = :date";
            Query query = session.createQuery(queryH);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                query.setParameter("date", format.parse(date));
                shows = query.list();
            } catch (java.text.ParseException ex) {
                System.out.println("Invalid date");
            }

            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return shows;
    }
}
