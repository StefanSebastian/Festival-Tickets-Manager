package AppServices;

import Domain.Show;
import Validation.Exceptions.ServiceException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IFestivalClient extends Remote {
    /*
    Show updated after transaction
     */
    void showUpdated(Show show) throws ServiceException, RemoteException;
}
