using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.rpcprotocol
{
    [Serializable]
    public class Response
    {
        //type of response
        private ResponseType type;

        //data sent
        private object data;

        public ResponseType Type
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

        public Response(ResponseType type, object data)
        {
            this.type = type;
            this.data = data;
        }

        public override string ToString()
        {
            return "Response{ type = " + Type + " data = " + Data + " } ";
        }

    }
}
