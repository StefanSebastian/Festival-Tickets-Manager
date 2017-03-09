import Domain.Artist;
import Domain.Show;
import Domain.Transaction;
import Repository.RepositoryArtistDB;
import Repository.RepositoryShowDB;
import Repository.RepositoryTransactionDB;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Main {

    public static void main(String[] args){

        Properties serverProperties = new Properties();
        try {
            serverProperties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Can not find bd.config");
        }

        RepositoryTransactionDB repositoryTransactionDB = new RepositoryTransactionDB(serverProperties);

        for (Transaction t : repositoryTransactionDB.getAll()){
            System.out.println(t);
        }
    }
}
