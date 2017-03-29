package utils;

import AppServices.IFestivalServer;
import rpcprotocol.FestivalClientRpcWorker;

import java.net.Socket;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class FestivalRpcConcurrentServer extends AbstractConcurrentServer {
    //server over festival app services
    private IFestivalServer server;

    //constructor
    public FestivalRpcConcurrentServer(Integer port, IFestivalServer server) {
        super(port);
        this.server = server;
    }

    @Override
    protected Thread createWorker(Socket socketClient) {
        FestivalClientRpcWorker worker = new FestivalClientRpcWorker(server, socketClient);
        Thread tw = new Thread(worker);
        return tw;
    }
}
