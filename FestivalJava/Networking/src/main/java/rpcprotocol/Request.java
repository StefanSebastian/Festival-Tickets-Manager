package rpcprotocol;

import java.io.Serializable;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class Request implements Serializable {
    //type of request
    private RequestType type;

    //data sent
    private Object data;

    //private constructor
    private Request(){}

    //getters and setters
    public RequestType getType(){
        return type;
    }

    private void setType(RequestType type){
        this.type = type;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    @Override
    public String toString(){
        return "Request{ type = " + getType() + " data = " + getData() + " } ";
    }

    public static class Builder{
        private Request request = new Request();

        public Builder type(RequestType type){
            request.setType(type);
            return this;
        }

        public Builder data(Object data){
            request.setData(data);
            return this;
        }

        public Request build(){
            return request;
        }
    }


}
