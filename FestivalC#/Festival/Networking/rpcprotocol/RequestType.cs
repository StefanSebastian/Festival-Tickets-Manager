using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.rpcprotocol
{
    public enum RequestType
    {
       GET_ARTISTS, GET_SHOWS_FOR_ARTIST, GET_SHOWS_FOR_DATE, BUY_TICKETS, LOGIN, LOGOUT
    }
}
