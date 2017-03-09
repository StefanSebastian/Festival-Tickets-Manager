import Domain.Artist;
import Repository.RepositoryArtistDB;

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
    }
}
