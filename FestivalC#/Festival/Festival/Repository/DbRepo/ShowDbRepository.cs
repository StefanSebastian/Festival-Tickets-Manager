using Festival.Model;
using Festival.Repository.Interface;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Repository
{
    public class ShowDbRepository : IShowRepository
    {
        //adds a show 
        public void save(Show show)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into shows values(@id, @loc, @date, @available, @sold, @artist)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = show.IdShow;
                command.Parameters.Add(paramId);

                var paramLoc = command.CreateParameter();
                paramLoc.ParameterName = "@loc";
                paramLoc.Value = show.Location;
                command.Parameters.Add(paramLoc);

                var paramDate = command.CreateParameter();
                paramDate.ParameterName = "@date";
                paramDate.Value = show.Date;
                command.Parameters.Add(paramDate);

                var paramAv = command.CreateParameter();
                paramAv.ParameterName = "@available";
                paramAv.Value = show.TicketsAvailable;
                command.Parameters.Add(paramAv);

                var paramSold = command.CreateParameter();
                paramSold.ParameterName = "@sold";
                paramSold.Value = show.TicketsSold;
                command.Parameters.Add(paramSold);

                var paramArt = command.CreateParameter();
                paramArt.ParameterName = "@artist";
                paramArt.Value = show.Artist.IdArtist;
                command.Parameters.Add(paramArt);
                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        //deletes a show
        public void delete(int id)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "delete from shows where idShow=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        //updates a show
        public void update(int id, Show newShow)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "update shows set idShow=@idShow, location=@loc, " +
                    "date=@date, ticketsAvailable=@available, " +
                    "ticketsSold=@sold, idArtist=@artist where idShow=@id";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idShow";
                paramId.Value = newShow.IdShow;
                command.Parameters.Add(paramId);

                var paramLoc = command.CreateParameter();
                paramLoc.ParameterName = "@loc";
                paramLoc.Value = newShow.Location;
                command.Parameters.Add(paramLoc);

                var paramDate = command.CreateParameter();
                paramDate.ParameterName = "@date";
                paramDate.Value = newShow.Date;
                command.Parameters.Add(paramDate);

                var paramAv = command.CreateParameter();
                paramAv.ParameterName = "@available";
                paramAv.Value = newShow.TicketsAvailable;
                command.Parameters.Add(paramAv);

                var paramSold = command.CreateParameter();
                paramSold.ParameterName = "@sold";
                paramSold.Value = newShow.TicketsSold;
                command.Parameters.Add(paramSold);

                var paramArt = command.CreateParameter();
                paramArt.ParameterName = "@artist";
                paramArt.Value = newShow.Artist.IdArtist;
                command.Parameters.Add(paramArt);

                var paramIdUpdate = command.CreateParameter();
                paramIdUpdate.ParameterName = "@id";
                paramIdUpdate.Value = id;
                command.Parameters.Add(paramIdUpdate);
                try
                {
                    command.ExecuteNonQuery();
                }
                catch (MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        //read an artist for id 
        private Artist getArtist(int idArt)
        {
            var connection = DbUtils.getConnection();
            using (var commandArt = connection.CreateCommand())
            {
                commandArt.CommandText = "select * from artists where idArtist=@idArt";
                var paramIdArt = commandArt.CreateParameter();
                paramIdArt.ParameterName = "@idArt";
                paramIdArt.Value = idArt;
                commandArt.Parameters.Add(paramIdArt);

                using (var dataReadArt = commandArt.ExecuteReader())
                {
                    if (dataReadArt.Read())
                    {
                        String nameArt = dataReadArt.GetString(1);
                        return new Artist(idArt, nameArt);
                    }
                }
            }
            return null;
        }

        //get by id
        public Show getById(int id)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from shows where idShow=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var dataR = command.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idShow = dataR.GetInt32(0);
                        String location = dataR.GetString(1);
                        String date = dataR.GetString(2);
                        int available = dataR.GetInt32(3);
                        int sold = dataR.GetInt32(4);
                        int idArtist = dataR.GetInt32(5);

                        return new Show(idShow, location, date, available, sold, getArtist(idArtist));
                    }
                }
            }
            return null;
        }

        //get all shows
        public List<Show> getAll()
        {
            List<Show> shows = new List<Show>();
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from shows";
                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idShow = dataR.GetInt32(0);
                        String location = dataR.GetString(1);
                        String date = dataR.GetString(2);
                        int available = dataR.GetInt32(3);
                        int sold = dataR.GetInt32(4);
                        int idArtist = dataR.GetInt32(5);
                        shows.Add(new Show(idShow, location, date, available, sold, getArtist(idArtist)));
                    }
                }
            }
            return shows;
        }

        public List<Show> getShowsForArtist(Artist artist)
        {
            List<Show> shows = new List<Show>();
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from shows where idArtist=@idArt";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idArt";
                paramId.Value = artist.IdArtist;
                command.Parameters.Add(paramId);

                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idShow = dataR.GetInt32(0);
                        String location = dataR.GetString(1);
                        String date = dataR.GetString(2);
                        int available = dataR.GetInt32(3);
                        int sold = dataR.GetInt32(4);
                        int idArtist = dataR.GetInt32(5);
                        shows.Add(new Show(idShow, location, date, available, sold, getArtist(idArtist)));
                    }
                }
            }
            return shows;
        }

        public List<Show> getShowsForDate(string date)
        {
            List<Show> shows = new List<Show>();
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from shows where date=@date";
                var paramDate = command.CreateParameter();
                paramDate.ParameterName = "@date";
                paramDate.Value = date;
                command.Parameters.Add(paramDate);

                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idShow = dataR.GetInt32(0);
                        String location = dataR.GetString(1);
                        String date = dataR.GetString(2);
                        int available = dataR.GetInt32(3);
                        int sold = dataR.GetInt32(4);
                        int idArtist = dataR.GetInt32(5);
                        shows.Add(new Show(idShow, location, date, available, sold, getArtist(idArtist)));
                    }
                }
            }
            return shows;
        }
    }
}
