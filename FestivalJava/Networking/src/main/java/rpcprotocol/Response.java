package rpcprotocol;

import java.io.Serializable;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class Response implements Serializable {
    //type
    private ResponseType type;

    //data
    private Object data;

    private Response(){}

    //getters and setters
    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "Response { type = " + getType() + " data = " + getData() + " } ";
    }

    public static class Builder{
        private Response response = new Response();

        public Builder type(ResponseType type){
            response.setType(type);
            return this;
        }

        public Builder data(Object data){
            response.setData(data);
            return this;
        }

        public Response build(){
            return response;
        }
    }
}
