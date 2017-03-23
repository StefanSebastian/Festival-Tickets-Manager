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
    public class TransactionDbRepository : ITransactionRepository
    {
        //adds a transaction 
        public void save(Transaction transaction)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into transactions values(@id, @client, @nrTick, @show)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = transaction.IdTransaction;
                command.Parameters.Add(paramId);

                var paramClient = command.CreateParameter();
                paramClient.ParameterName = "@client";
                paramClient.Value = transaction.ClientName;
                command.Parameters.Add(paramClient);

                var paramTick = command.CreateParameter();
                paramTick.ParameterName = "@nrTick";
                paramTick.Value = transaction.NumberOfTickets;
                command.Parameters.Add(paramTick);

                var paramShow = command.CreateParameter();
                paramShow.ParameterName = "@show";
                paramShow.Value = transaction.Show.IdShow;
                command.Parameters.Add(paramShow);

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

        //updates a transaction
        public void update(int id, Transaction newTr)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "update transactions set idTransaction=@idTr, clientName=@client, " +
                    "numberOfTickets=@tick, idShow=@idSh where idTransaction=@id";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@idTr";
                paramId.Value = newTr.IdTransaction;
                command.Parameters.Add(paramId);

                var paramClient = command.CreateParameter();
                paramClient.ParameterName = "@client";
                paramClient.Value = newTr.ClientName;
                command.Parameters.Add(paramClient);

                var paramTick = command.CreateParameter();
                paramTick.ParameterName = "@tick";
                paramTick.Value = newTr.NumberOfTickets;
                command.Parameters.Add(paramTick);

                var paramShow = command.CreateParameter();
                paramShow.ParameterName = "@idSh";
                paramShow.Value = newTr.Show.IdShow;
                command.Parameters.Add(paramShow);

                var paramIdUpd = command.CreateParameter();
                paramIdUpd.ParameterName = "@id";
                paramIdUpd.Value = id;
                command.Parameters.Add(paramIdUpd);
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

        //deletes a transaction
        public void delete(int id)
        {
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "delete from transactions where idTransaction=@id";
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

        //gets an artist by id 
        private Artist getArtistById(int idArtist)
        {
            var connection = DbUtils.getConnection();
            using (var commandArt = connection.CreateCommand())
            {
                commandArt.CommandText = "select * from artists where idArtist=@idArt";
                var paramIdArt = commandArt.CreateParameter();
                paramIdArt.ParameterName = "@idArt";
                paramIdArt.Value = idArtist;
                commandArt.Parameters.Add(paramIdArt);

                using (var dataReadArt = commandArt.ExecuteReader())
                {
                    if (dataReadArt.Read())
                    {
                        String nameArt = dataReadArt.GetString(1);
                        return new Artist(idArtist, nameArt);
                    }
                }
            }

            return null;
        }

        //get a show by id
        private Show getShowById(int id)
        {
            var connection = DbUtils.getConnection();

            int idShow = 0;
            String location = null;
            String date = null;
            int available = 0;
            int sold = 0;
            int idArtist = 0;
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
                        idShow = dataR.GetInt32(0);
                        location = dataR.GetString(1);
                        date = dataR.GetString(2);
                        available = dataR.GetInt32(3);
                        sold = dataR.GetInt32(4);
                        idArtist = dataR.GetInt32(5);
                    }
                }

                if (idShow == 0 && location == null && date == null)
                {
                    return null; //show was not found 
                }
            }

            return new Show(idShow, location, date, available, sold, getArtistById(idArtist));
        }

        //get by id
        public Transaction getById(int id)
        {
            var connection = DbUtils.getConnection();

            int idTr = 0;
            String client = null;
            int tick = 0;
            int idShow = 0;
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from transactions where idTransaction=@id";
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);

                using (var dataR = command.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        idTr = dataR.GetInt32(0);
                        client = dataR.GetString(1);
                        tick = dataR.GetInt32(2);
                        idShow = dataR.GetInt32(3);
                    }
                }
                if (idTr == 0 && client == null)
                {
                    return null; //transaction not found 
                }
            }
            return new Transaction(idTr, client, tick, getShowById(idShow));
        }

        //get all transactions
        public List<Transaction> getAll()
        {
            List<Transaction> transactions = new List<Transaction>();
            List<int> idList = new List<int>();

            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from transactions";
                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idTr = dataR.GetInt32(0);
                        idList.Add(idTr);
                    }
                }
            }

            foreach (int idTr in idList)
            {
                Transaction tr = getById(idTr);
                if (tr != null)
                {
                    transactions.Add(tr);
                }
            }
            return transactions;
        }

        public void saveWithoutId(Transaction transaction)
        {
            var connection = DbUtils.getConnection();

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "insert into transactions values(@client, @nrTick, @show)";
                var paramClient = command.CreateParameter();
                paramClient.ParameterName = "@client";
                paramClient.Value = transaction.ClientName;
                command.Parameters.Add(paramClient);

                var paramTick = command.CreateParameter();
                paramTick.ParameterName = "@nrTick";
                paramTick.Value = transaction.NumberOfTickets;
                command.Parameters.Add(paramTick);

                var paramShow = command.CreateParameter();
                paramShow.ParameterName = "@show";
                paramShow.Value = transaction.Show.IdShow;
                command.Parameters.Add(paramShow);

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
    }
}
