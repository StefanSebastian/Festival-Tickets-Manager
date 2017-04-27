using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace Networking.protobuff
{
    public class ProtoUtils
    {
        public static Protobuf.FestivalRequest createLoginRequest(User user)
        {
            Protobuf.User userDto = new Protobuf.User();
            userDto.Id = user.Username;
            userDto.Password = user.Password;

            Protobuf.FestivalRequest request = new Protobuf.FestivalRequest();
            request.Type = Protobuf.FestivalRequest.Types.Type.Login;
            request.User = userDto;
            return request;
        }

        public static Protobuf.FestivalRequest createLogoutRequest(User user)
        {
            Protobuf.User userDto = new Protobuf.User();
            userDto.Id = user.Username;
            userDto.Password = user.Password;

            Protobuf.FestivalRequest request = new Protobuf.FestivalRequest();
            request.Type = Protobuf.FestivalRequest.Types.Type.Logout;
            request.User = userDto;
            return request;
        }

        public static Protobuf.FestivalResponse createOkResponse()
        {
            Protobuf.FestivalResponse response = new Protobuf.FestivalResponse();
            response.Type = Protobuf.FestivalResponse.Types.Type.Ok;
            return response;
        }

        public static Protobuf.FestivalResponse createErrorResponse(string error)
        {
            Protobuf.FestivalResponse response = new Protobuf.FestivalResponse();
            response.Type = Protobuf.FestivalResponse.Types.Type.Error;
            response.Error = error;
            return response;
        }

        public static Protobuf.FestivalRequest createGetArtistsRequest()
        {
            Protobuf.FestivalRequest request = new Protobuf.FestivalRequest();
            request.Type = Protobuf.FestivalRequest.Types.Type.GetArtists;
            return request;
        }

        public static Artist getArtistFromProto(Protobuf.Artist artistP)
        {
            return new Artist(artistP.Id, artistP.Name);
        }

        public static List<Artist> getArtists(Protobuf.FestivalResponse response)
        {
            List<Artist> artists = new List<Artist>();
            for (int i = 0; i < response.Artists.Count; i++)
            {
                artists.Add(getArtistFromProto(response.Artists.ElementAt(i)));
            }
            return artists;
        }

        public static Protobuf.FestivalRequest createGetShowsForArtistRequest(int idArtist)
        {
            Protobuf.FestivalRequest request = new Protobuf.FestivalRequest();
            request.Type = Protobuf.FestivalRequest.Types.Type.GetShowsForArtist;
            request.IdArtist = idArtist;
            return request;
        }

        public static Protobuf.FestivalRequest createGetShowsForDateRequest(string date)
        {
            Protobuf.FestivalRequest request = new Protobuf.FestivalRequest();
            request.Type = Protobuf.FestivalRequest.Types.Type.GetShowsForDate;
            request.Date = date;
            return request;
        }

        public static Show getShowFromProto(Protobuf.Show showP)
        {
            return new Show(showP.Id, 
                showP.Location,
                DateTime.Parse(showP.Date), 
                showP.TicketsAvailable, 
                showP.TicketsSold,
                getArtistFromProto(showP.Artist));
        }

        public static List<Show> getShows(Protobuf.FestivalResponse response)
        {
            List<Show> shows = new List<Show>();

            for (int i = 0; i < response.Shows.Count; i++)
            {
                shows.Add(getShowFromProto(response.Shows.ElementAt(i)));
            }

            return shows;
        }
    }
}
