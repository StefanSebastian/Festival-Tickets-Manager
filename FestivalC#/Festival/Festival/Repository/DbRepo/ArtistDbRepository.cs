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
    public class ArtistDbRepository : IArtistRepository
    {
        //saves an artist
        public void save(Artist artist)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into artists values(@id, @name)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = artist.IdArtist;
                command.Parameters.Add(paramId);

                var paramName = command.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = artist.Name;
                command.Parameters.Add(paramName);
                try
                {
                    command.ExecuteNonQuery();
                } catch(MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        //deletes an artist
        public void delete(int id)
        {
            var connection = DbUtils.getConnection();
            using(var command = connection.CreateCommand())
            {
                command.CommandText = "delete from artists where idArtist=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                try
                {
                    command.ExecuteNonQuery();
                } catch(MySqlException e)
                {
                    Console.Out.WriteLine(e);
                }
            }
        }

        //updates an artist
        public void update(int id, Artist newArtist)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "update artists set idArtist=@idArt, name=@name where idArtist=@id";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idArt";
                paramId.Value = newArtist.IdArtist;
                command.Parameters.Add(paramId);

                var paramName = command.CreateParameter();
                paramName.ParameterName = "@name";
                paramName.Value = newArtist.Name;
                command.Parameters.Add(paramName);

                var paramOldId = command.CreateParameter();
                paramOldId.ParameterName = "@id";
                paramOldId.Value = id;
                command.Parameters.Add(paramOldId);
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

        //get by id
        public Artist getById(int id)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from artists where idArtist=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var dataR = command.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idArtist = dataR.GetInt32(0);
                        String nameArtist = dataR.GetString(1);
                        return new Artist(idArtist, nameArtist);
                    }
                }
            }
            return null;
        }

        //get all artists
        public List<Artist> getAll()
        {
            List<Artist> artists = new List<Artist>();
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from artists";
                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idArtist = dataR.GetInt32(0);
                        String nameArtist = dataR.GetString(1);
                        artists.Add(new Artist(idArtist, nameArtist));
                    }
                }
            }
            return artists;
        }

    }
}
