package festival.Interfaces;

import festival.Domain.Show;

import java.util.List;

/**
 * Created by Sebi on 26-Mar-17.
 */
public interface IShowRepository extends IRepository<Show, Integer> {
    /*
    Gets all shows for a given artist
     */
    List<Show> getShowsForArtist(Integer idArtist);

    /*
    Gets all shows for a given date
     */
    List<Show> getShowsForDate(String date);
}
