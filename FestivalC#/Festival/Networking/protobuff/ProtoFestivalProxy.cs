using Festival.Service;
using Festival.Service.AppServices;
using Google.Protobuf;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Festival.Model;
using Services.Validation.Exceptions;

namespace Networking.protobuff
{
    public class ProtoFestivalProxy : IFestivalServer
    {
        private string host;
        private int port;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Protobuf.FestivalResponse> responses;
        private volatile bool finished;
        private EventWaitHandle waitHandle;

        private IFestivalClient client;

        public ProtoFestivalProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Protobuf.FestivalResponse>();
        }

        private void sendRequest(Protobuf.FestivalRequest request)
        {
            try
            {
                request.WriteDelimitedTo(stream);
                stream.Flush();
            } catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private Protobuf.FestivalResponse readResponse()
        {
            Protobuf.FestivalResponse response = null;
            try
            {
                waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return response;

        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                waitHandle.Close();
                client = null;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        private bool isUpdate(Protobuf.FestivalResponse response)
        {
            if (response.Type == Protobuf.FestivalResponse.Types.Type.ShowUpdated)
            {
                return true;
            }
            return false;
        }

        //background reader thread run method
        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    Protobuf.FestivalResponse response = Protobuf.FestivalResponse.Parser.ParseDelimitedFrom(stream);

                    Console.WriteLine("Response received " + response);
                    if (isUpdate(response))
                    {
                        handleUpdate(response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue(response);
                        }
                        waitHandle.Set();
                    }
                }
                catch (Exception ex)
                {
                    
                }
            }
        }

        private void handleUpdate(Protobuf.FestivalResponse response)
        {
           
        }

        public List<Artist> getArtists()
        {
            sendRequest(ProtoUtils.createGetArtistsRequest());
            Protobuf.FestivalResponse response = readResponse();
            if (response.Type == Protobuf.FestivalResponse.Types.Type.Error)
            {
                throw new ServiceException(response.Error);
            }
            return ProtoUtils.getArtists(response);
        }

        public List<Show> getShowsForArtist(int idArtist)
        {
            sendRequest(ProtoUtils.createGetShowsForArtistRequest(idArtist));
            Protobuf.FestivalResponse response = readResponse();
            if (response.Type == Protobuf.FestivalResponse.Types.Type.Error)
            {
                throw new ServiceException(response.Error);
            }
            return ProtoUtils.getShows(response);
        }

        public List<Show> getShowsForDate(string date)
        {
            sendRequest(ProtoUtils.createGetShowsForDateRequest(date));
            Protobuf.FestivalResponse response = readResponse();
            if (response.Type == Protobuf.FestivalResponse.Types.Type.Error)
            {
                throw new ServiceException(response.Error);
            }
            return ProtoUtils.getShows(response);
        }

        public void buyTicketsForShow(int idShow, string clientName, int numberOfTickets, string username)
        {
            throw new NotImplementedException();
        }

        public void login(User user, IFestivalClient client)
        {
            initializeConnection();
            sendRequest(ProtoUtils.createLoginRequest(user));
            Protobuf.FestivalResponse response = readResponse();
            if (response.Type == Protobuf.FestivalResponse.Types.Type.Ok)
            {
                this.client = client;
                return;
            }
            if (response.Type == Protobuf.FestivalResponse.Types.Type.Error)
            {
                throw new ServiceException(response.Error);
            }
        }

        public void logout(User user, IFestivalClient client)
        {
            sendRequest(ProtoUtils.createLogoutRequest(user));
            Protobuf.FestivalResponse response = readResponse();
            closeConnection();
            if (response.Type == Protobuf.FestivalResponse.Types.Type.Error)
            {
                throw new ServiceException(response.Error);
            }
        }
    }
}
