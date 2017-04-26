package utils;

import AppServices.IFestivalServer;
import protobuf.ProtoFestivalWorker;

import java.net.Socket;

/**
 * Created by Sebi on 25-Apr-17.
 */
public class FestivalProtobufConcurrentServer extends AbstractConcurrentServer {
    private IFestivalServer festivalServer;

    public FestivalProtobufConcurrentServer(int port, IFestivalServer festivalServer){
        super(port);
        this.festivalServer = festivalServer;
    }

    @Override
    protected Thread createWorker(Socket socketClient) {
        ProtoFestivalWorker worker = new ProtoFestivalWorker(festivalServer, socketClient);
        Thread tw = new Thread(worker);
        return tw;
    }
}
