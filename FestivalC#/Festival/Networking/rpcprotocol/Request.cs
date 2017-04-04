using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.rpcprotocol
{
    [Serializable]
    public class Request
    {
        //type of request
        private RequestType type;

        //data sent
        private object data;

        public RequestType Type
        {
            get
            {
                return type;
            }

            set
            {
                type = value;
            }
        }

        public object Data
        {
            get
            {
                return data;
            }

            set
            {
                data = value;
            }
        }

        public Request(RequestType type, object data)
        {
            this.type = type;
            this.data = data;
        }

        public override string ToString()
        {
            return "Request{ type = " + Type + " data = " + Data + " } ";
        }

    }
}
