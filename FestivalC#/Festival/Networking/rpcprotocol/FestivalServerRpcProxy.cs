using Festival.Service;
using Festival.Service.AppServices;
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
using Networking.dto;
using Services.Validation.Exceptions;
using System.Collections;

namespace Networking.rpcprotocol
{
    public class FestivalServerRpcProxy : IFestivalServer
    {
        private string host;
        private int port;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle waitHandle;

        private IFestivalClient client;

        public FestivalServerRpcProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        //sends a request to server
        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        //reads a response from queue
        private Response readResponse()
        {
            Response response = null;
            try
            {
                waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            } catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return response;
        }

        //initialize the connection
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
            } catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        //close the connection
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

        //starts the reader thread
        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        //checks if response type is update
        private bool isUpdate(Response response)
        {
            if (response.Type == ResponseType.SHOWS_UPDATED)
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
                    Response response = (Response)formatter.Deserialize(stream);
              
                    Console.WriteLine("Response received " + response);
                    if (isUpdate(response))
                    {

                    } else
                    {
                        lock (responses)
                        {
                            responses.Enqueue(response);
                        }
                        waitHandle.Set();
                    }
                } catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                }
            }
        }



        public List<Artist> getArtists()
        {
            List<Artist> artists = new List<Artist>();
            sendRequest(new Request(RequestType.GET_ARTISTS, null));
            Response response = readResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new ServiceException((string)response.Data);
            }
            List<ArtistDTO> artistDTO = ((IEnumerable)response.Data).Cast<ArtistDTO>().ToList();
            return DTOUtils.getListArtistFromDTO(artistDTO);
        }

        public List<Show> getShowsForArtist(int idArtist)
        {
            List<Show> shows = new List<Show>();
            sendRequest(new Request(RequestType.GET_SHOWS_FOR_ARTIST, idArtist));
            Response response = readResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new ServiceException((string)response.Data);
            }
            List<ShowDTO> showDTO = ((IEnumerable)response.Data).Cast<ShowDTO>().ToList();
            return DTOUtils.getListShowFromDTO(showDTO);
        }

        public List<Show> getShowsForDate(string date)
        {
            List<Show> shows = new List<Show>();
            sendRequest(new Request(RequestType.GET_SHOWS_FOR_DATE, date));
            Response response = readResponse();
            if (response.Type == ResponseType.ERROR)
            {
                throw new ServiceException((string)response.Data);
            }
            List<ShowDTO> showDTO = ((IEnumerable)response.Data).Cast<ShowDTO>().ToList();
            return DTOUtils.getListShowFromDTO(showDTO);
        }

        public void buyTicketsForShow(int idShow, string clientName, int numberOfTickets, string username)
        {
            throw new NotImplementedException();
        }

        //login request
        public void login(User user, IFestivalClient client)
        {
            initializeConnection();
            UserDTO userDTO = DTOUtils.getUserDTO(user);
            sendRequest(new Request(RequestType.LOGIN, userDTO));
            Response response = readResponse();
            if (response.Type == ResponseType.OK)
            {
                this.client = client;
                return;
            }
            if (response.Type == ResponseType.ERROR)
            {
                throw new ServiceException((string)response.Data);
            }
        }

        //logout request
        public void logout(User user, IFestivalClient client)
        {
            UserDTO userDTO = DTOUtils.getUserDTO(user);
            sendRequest(new Request(RequestType.LOGOUT, userDTO));
            Response response = readResponse();

            if (response.Type == ResponseType.OK)
            {
                this.client = null;
                closeConnection();
                return;
            }
            if (response.Type == ResponseType.ERROR)
            {
                throw new ServiceException((string)response.Data);
            }
        }
    }
}
