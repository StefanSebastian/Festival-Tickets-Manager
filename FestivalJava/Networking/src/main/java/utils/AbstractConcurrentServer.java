package utils;

import java.net.Socket;

/**
 * Created by Sebi on 29-Mar-17.
 */
public abstract class AbstractConcurrentServer extends AbstractServer {

    //constructor
    public AbstractConcurrentServer(Integer port) {
        super(port);
    }

    @Override
    protected void processRequest(Socket socketClient){
        Thread tw = createWorker(socketClient);
        tw.start();
    }

    //creates a thread to process client requests
    protected abstract Thread createWorker(Socket socketClient);
}
