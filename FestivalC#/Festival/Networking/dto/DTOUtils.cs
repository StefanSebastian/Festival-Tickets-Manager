using Festival.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    public class DTOUtils
    {
        public static User getUserFromDTO(UserDTO userDTO)
        {
            string username = userDTO.Username;
            string password = userDTO.Password;
            return new User(username, password);
        }

        public static UserDTO getUserDTO(User user)
        {
            string username = user.Username;
            string password = user.Password;
            return new UserDTO(username, password);
        }

        public static Artist getArtistFromDTO(ArtistDTO artistDTO)
        {
            int id = artistDTO.Id;
            string name = artistDTO.Name;
            return new Artist(id, name);
        }

        public static ArtistDTO getArtistDTO(Artist artist)
        {
            int id = artist.IdArtist;
            string name = artist.Name;
            return new ArtistDTO(id, name);
        }

        public static Show getShowFromDTO(ShowDTO showDTO)
        {
            int id = showDTO.Id;
            string location = showDTO.Location;
            DateTime date = showDTO.Date;
            int ticketsAvailable = showDTO.TicketsAvailable;
            int ticketsSold = showDTO.TicketsSold;
            Artist artist = getArtistFromDTO(showDTO.Artist);
            return new Show(id, location, date,
                    ticketsAvailable, ticketsSold, artist);
        }

        public static ShowDTO getShowDTO(Show show)
        {
            int id = show.IdShow;
            string location = show.Location;
            DateTime date = show.Date;
            int ticketsAvailable = show.TicketsAvailable;
            int ticketsSold = show.TicketsSold;
            ArtistDTO artist = getArtistDTO(show.Artist);
            return new ShowDTO(id, location, date,
                    ticketsAvailable, ticketsSold, artist);
        }

        public static Transaction getTransactionFromDTO(TransactionDTO transactionDTO)
        {
            int id = transactionDTO.Id;
            string clientName = transactionDTO.ClientName;
            int numberOfTickets = transactionDTO.NumberOfTickets;
            Show show = getShowFromDTO(transactionDTO.Show);
            return new Transaction(id, clientName, numberOfTickets, show);
        }

        public static TransactionDTO getTransactionDTO(Transaction transaction)
        {
            int id = transaction.IdTransaction;
            string clientName = transaction.ClientName;
            int numberOfTickets = transaction.NumberOfTickets;
            ShowDTO show = getShowDTO(transaction.Show);
            return new TransactionDTO(id, clientName, numberOfTickets, show);
        }

        public static List<Artist> getListArtistFromDTO(List<ArtistDTO> artists)
        {
            List<Artist> artistList = new List<Artist>();
            foreach (ArtistDTO artistDTO in artists)
            {
                artistList.Add(getArtistFromDTO(artistDTO));
            }
            return artistList;
        }

        public static List<ArtistDTO> getListArtistDTO(List<Artist> artists)
        {
            List<ArtistDTO> artistDTOList = new List<ArtistDTO>();
            foreach (Artist artist in artists)
            {
                artistDTOList.Add(getArtistDTO(artist));
            }
            return artistDTOList;
        }

        public static List<Show> getListShowFromDTO(List<ShowDTO> shows)
        {
            List<Show> showList = new List<Show>();
            foreach (ShowDTO showDTO in shows)
            {
                showList.Add(getShowFromDTO(showDTO));
            }
            return showList;
        }

        public static List<ShowDTO> getListShowDTO(List<Show> shows)
        {
            List<ShowDTO> showDTOList = new List<ShowDTO>();
            foreach (Show show in shows)
            {
                showDTOList.Add(getShowDTO(show));
            }
            return showDTOList;
        }
    }
}
