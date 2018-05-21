package goita.server.state;
import goita.server.GoitaServerThread;

public class Start extends ServerState{
    public Start(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        sc.writeMultiMessages(GoitaServerThread.nickname);
    }

    @Override
    protected ServerState decideState() {
        sc.readSingleMessage();
        synchro.synchro();
        return new ReceiveHandPiece(thread);
    }
}
