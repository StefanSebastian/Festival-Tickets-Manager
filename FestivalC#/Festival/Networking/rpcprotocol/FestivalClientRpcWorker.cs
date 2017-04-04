using Festival.Service;
using System;
using Festival.Model;
using Festival.Service.AppServices;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using Networking.dto;
using Services.Validation.Exceptions;
using System.Collections.Generic;

namespace Networking.rpcprotocol
{
    public class FestivalClientRpcWorker : IFestivalClient
    {
        //server
        private IFestivalServer server;

        //connection to client
        private TcpClient connection;

        //object formatter
        private IFormatter formatter;

        //object stream
        private NetworkStream stream;

        //connection status
        private volatile bool connected;

        public FestivalClientRpcWorker(IFestivalServer server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    //read request
                    object request = formatter.Deserialize(stream);

                    //handle request
                    Response response = handleRequest((Request)request); 

                    //send response
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                    break;
                }
            }

            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }


        public void showUpdated(Show show)
        {
            ShowDTO showDTO = DTOUtils.getShowDTO(show);
            Response response = new Response(ResponseType.SHOWS_UPDATED, showDTO);
            try
            {
                sendResponse(response);
            }
            catch (Exception e)
            {
                throw new ServiceException("Update notify send response error");
            }
        }


        private Response handleRequest(Request request)
        {
            if (request.Type == RequestType.LOGIN)
            {
                Console.WriteLine("Login request ...");
                UserDTO userDTO = (UserDTO)request.Data;
                User user = DTOUtils.getUserFromDTO(userDTO);
                try
                {
                    lock (server)
                    {
                        server.login(user, this);
                    }
                    return new Response(ResponseType.OK, "ok");
                }
                catch (ServiceException ex)
                {
                    return new Response(ResponseType.ERROR, ex.Message);
                }
            }

            if (request.Type == RequestType.LOGOUT)
            {
                Console.WriteLine("Logout request");
                UserDTO userDTO = (UserDTO)request.Data;
                User user = DTOUtils.getUserFromDTO(userDTO);
                try
                {
                    lock (server)
                    {
                        server.logout(user, this);
                    }
                    connected = false;
                    return new Response(ResponseType.OK, "ok");
                } 
                catch(ServiceException ex)
                {
                    return new Response(ResponseType.ERROR, ex.Message);
                }
            }

            if (request.Type == RequestType.GET_ARTISTS)
            {
                Console.WriteLine("Artist list request");
               
                try
                {
                    List<Artist> artists = new List<Artist>();

                    lock (server)
                    {
                        artists = server.getArtists();
                    }
                    return new Response(ResponseType.OK, DTOUtils.getListArtistDTO(artists));
                }
                catch (ServiceException ex)
                {
                    return new Response(ResponseType.ERROR, ex.Message);
                }
            }

            if (request.Type == RequestType.GET_SHOWS_FOR_ARTIST)
            {
                Console.WriteLine("Shows for artist request");

                try
                {
                    int idArtist = (Int32)request.Data;

                    List<Show> shows = new List<Show>();

                    lock (server)
                    {
                        shows = server.getShowsForArtist(idArtist);
                    }
                    return new Response(ResponseType.OK, DTOUtils.getListShowDTO(shows));
                }
                catch (ServiceException ex)
                {
                    return new Response(ResponseType.ERROR, ex.Message);
                }
            }

            if (request.Type == RequestType.GET_SHOWS_FOR_DATE)
            {
                Console.WriteLine("Shows for date request");

                try
                {
                    string date = (string)request.Data;

                    List<Show> shows = new List<Show>();

                    lock (server)
                    {
                        shows = server.getShowsForDate(date);
                    }
                    return new Response(ResponseType.OK, DTOUtils.getListShowDTO(shows));
                }
                catch (ServiceException ex)
                {
                    return new Response(ResponseType.ERROR, ex.Message);
                }
            }

            if (request.Type == RequestType.BUY_TICKETS)
            {
                Console.WriteLine("Buy tickets request");

                try
                {
                    BuyTicketsDTO buyTicketsDTO = (BuyTicketsDTO)request.Data;

                    lock (server)
                    {
                        server.buyTicketsForShow(buyTicketsDTO.IdShow,
                            buyTicketsDTO.ClientName,
                            buyTicketsDTO.NumberOfTickets,
                            buyTicketsDTO.Username);
                    }
                    return new Response(ResponseType.OK, "ok");
                }
                catch (ServiceException ex)
                {
                    return new Response(ResponseType.ERROR, ex.Message);
                }
            }

            return null;
        }

        //send response over network
        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }
    }
}

