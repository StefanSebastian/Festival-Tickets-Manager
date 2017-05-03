package protobuf;

import Domain.Artist;
import Domain.Show;
import Domain.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class ProtoUtils {
    // ---------------- Authentication ---------------------
    public static FestivalProtobufs.FestivalRequest createLoginRequest(User user){
        FestivalProtobufs.User userDto =
                FestivalProtobufs.User.newBuilder()
                        .setId(user.getUsername())
                        .setPassword(user.getPassword())
                        .build();
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest.newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.Login)
                        .setUser(userDto)
                        .build();
        return request;
    }

    public static FestivalProtobufs.FestivalRequest createLogoutRequest(User user){
        FestivalProtobufs.User userDto =
                FestivalProtobufs.User.newBuilder()
                        .setId(user.getUsername())
                        .setPassword(user.getPassword())
                        .build();
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest.newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.Logout)
                        .setUser(userDto)
                        .build();
        return request;
    }

    // ------------------ Users -----------------------
    public static User getUser(FestivalProtobufs.FestivalRequest request){
        return new User(request.getUser().getId(), request.getUser().getPassword());
    }

    // ------------------- Generic responses
    public static FestivalProtobufs.FestivalResponse createOKResponse(){
        FestivalProtobufs.FestivalResponse response =
                FestivalProtobufs.FestivalResponse.newBuilder()
                        .setType(FestivalProtobufs.FestivalResponse.Type.Ok)
                        .build();
        return response;
    }

    public static FestivalProtobufs.FestivalResponse createErrorResponse(String msg){
        FestivalProtobufs.FestivalResponse response =
                FestivalProtobufs.FestivalResponse.newBuilder()
                .setType(FestivalProtobufs.FestivalResponse.Type.Error)
                .setError(msg)
                .build();
        return response;
    }

    // --------------- Artists ----------------------------

    public static FestivalProtobufs.FestivalRequest createGetArtistsRequest(){
        FestivalProtobufs.FestivalRequest request = FestivalProtobufs.FestivalRequest
                .newBuilder()
                .setType(FestivalProtobufs.FestivalRequest.Type.GetArtists)
                .build();
        return request;
    }

    public static Artist getArtist(FestivalProtobufs.Artist artist){
        return new Artist(artist.getId(), artist.getName());
    }

    public static List<Artist> getArtists(FestivalProtobufs.FestivalResponse response){
        List<Artist> artists = new ArrayList<>();
        for (int i = 0; i < response.getArtistsCount(); i++){
            artists.add(getArtist(response.getArtists(i)));
        }
        return artists;
    }

    public static FestivalProtobufs.FestivalResponse createGetArtistsResponse(List<Artist> artists){
        FestivalProtobufs.FestivalResponse.Builder response = FestivalProtobufs.FestivalResponse.newBuilder().setType(FestivalProtobufs.FestivalResponse.Type.Ok);

        for (Artist artist : artists){
            FestivalProtobufs.Artist artistDto = FestivalProtobufs.Artist.newBuilder().setId(artist.getIdArtist()).setName(artist.getName()).build();
            response.addArtists(artistDto);
        }

        return response.build();
    }

    public static FestivalProtobufs.Artist getProtoFromArtist(Artist artist){
        return FestivalProtobufs.Artist.newBuilder().setId(artist.getIdArtist()).setName(artist.getName()).build();
    }

    // -------------------- Shows ------------------
    public static FestivalProtobufs.FestivalRequest createGetShowsForArtistRequest(int idArtist){
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest
                        .newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.GetShowsForArtist)
                        .setIdArtist(idArtist)
                        .build();
        return request;
    }

    public static FestivalProtobufs.FestivalResponse createGetShowsForArtistResponse(List<Show> shows) {
        FestivalProtobufs.FestivalResponse.Builder response =
                FestivalProtobufs.FestivalResponse.newBuilder().setType(FestivalProtobufs.FestivalResponse.Type.Ok);

        for (Show show : shows){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FestivalProtobufs.Show showDto = FestivalProtobufs.Show.newBuilder()
                    .setId(show.getIdShow())
                    .setArtist(getProtoFromArtist(show.getArtist()))
                    .setDate(df.format(show.getDate()))
                    .setLocation(show.getLocation())
                    .setTicketsAvailable(show.getTicketsAvailable())
                    .setTicketsSold(show.getTicketsSold())
                    .build();
            response.addShows(showDto);
        }

        return response.build();
    }

    public static FestivalProtobufs.FestivalRequest createGetShowsForDateRequest(String date){
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest
                        .newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.GetShowsForDate)
                        .setDate(date)
                        .build();
        return request;
    }

    public static FestivalProtobufs.FestivalResponse createGetShowsForDateResponse(List<Show> shows) {
        FestivalProtobufs.FestivalResponse.Builder response =
                FestivalProtobufs.FestivalResponse.newBuilder().setType(FestivalProtobufs.FestivalResponse.Type.Ok);

        for (Show show : shows){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FestivalProtobufs.Show showDto = FestivalProtobufs.Show.newBuilder()
                    .setId(show.getIdShow())
                    .setArtist(getProtoFromArtist(show.getArtist()))
                    .setDate(df.format(show.getDate()))
                    .setLocation(show.getLocation())
                    .setTicketsAvailable(show.getTicketsAvailable())
                    .setTicketsSold(show.getTicketsSold())
                    .build();
            response.addShows(showDto);
        }

        return response.build();
    }

    public static  Show getShow(FestivalProtobufs.Show show){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date showDate = null;
        try {
            showDate = df.parse(show.getDate());
        } catch (ParseException e){
            e.printStackTrace();
        }
        return new Show(show.getId(),
                show.getLocation(),
                showDate,
                show.getTicketsAvailable(),
                show.getTicketsSold(),
                getArtist(show.getArtist()));
    }

    public static List<Show> getShows(FestivalProtobufs.FestivalResponse response){
        List<Show> showList = new ArrayList<>();
        for (int i = 0; i < response.getShowsCount(); i++){
            showList.add(getShow(response.getShows(i)));
        }
        return showList;
    }

    public static FestivalProtobufs.Show getProtoFromShow(Show show){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        FestivalProtobufs.Show showDto = FestivalProtobufs.Show
                .newBuilder()
                .setId(show.getIdShow())
                .setDate(df.format(show.getDate()))
                .setLocation(show.getLocation())
                .setTicketsAvailable(show.getTicketsAvailable())
                .setTicketsSold(show.getTicketsSold())
                .setArtist(getProtoFromArtist(show.getArtist()))
                .build();
        return showDto;
    }

    // ------------------ Tickets ------------------
    public static FestivalProtobufs.FestivalRequest createBuyTicketsRequest(int idShow, String clientName, int nrTickets, String username){
        FestivalProtobufs.FestivalRequest request =
                FestivalProtobufs.FestivalRequest
                        .newBuilder()
                        .setType(FestivalProtobufs.FestivalRequest.Type.BuyTickets)
                        .setIdShow(idShow)
                        .setClientName(clientName)
                        .setNrOfTickets(nrTickets)
                        .setUsername(username)
                        .build();
        return request;
    }
}
