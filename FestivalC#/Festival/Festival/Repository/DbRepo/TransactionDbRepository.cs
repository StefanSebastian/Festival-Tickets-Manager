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

        //get a show by id
        private Show getShowById(int id)
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

        //get by id
        public Transaction getById(int id)
        {
            var connection = DbUtils.getConnection();
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
                        int idTr = dataR.GetInt32(0);
                        String client = dataR.GetString(1);
                        int tick = dataR.GetInt32(2);
                        int idShow = dataR.GetInt32(3);
                        return new Transaction(idTr, client, tick, getShowById(idShow));
                    }
                }
            }
            return null;
        }

        //get all transactions
        public List<Transaction> getAll()
        {
            List<Transaction> transactions = new List<Transaction>();
            var connection = DbUtils.getConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from transactions";
                using (var dataR = command.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idTr = dataR.GetInt32(0);
                        String client = dataR.GetString(1);
                        int tick = dataR.GetInt32(2);
                        int idShow = dataR.GetInt32(3);
                        transactions.Add(new Transaction(idTr, client, tick, getShowById(idShow)));
                    }
                }
            }
            return transactions;
        }

    }
}
