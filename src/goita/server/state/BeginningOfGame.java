package goita.server.state;
import goita.StateMachine;
import goita.server.GoitaServerThread;

class BeginningOfGame extends ServerState {
    private int parentId;

    BeginningOfGame(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        parentId = synchro.getParentId();
        //System.err.println(parentId);
        sc.writeSingleMessage(parentId);
    }

    @Override
    protected ServerState decideState() {
        if(thread.threadId == parentId) return new PutSecretPiece(thread);
        return new Waiting(thread);

    }
}
