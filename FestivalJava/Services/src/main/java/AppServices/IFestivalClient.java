package AppServices;

import Domain.Show;
import Validation.Exceptions.ServiceException;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IFestivalClient {
    /*
    Show updated after transaction
     */
    void showUpdated(Show show) throws ServiceException;
}
