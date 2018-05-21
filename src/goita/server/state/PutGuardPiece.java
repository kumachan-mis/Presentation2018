package goita.server.state;
import goita.server.GoitaServerThread;

class PutGuardPiece extends ServerState{
    PutGuardPiece(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        synchro.setParentId(thread.threadId);
        synchro.setResultMessage("guardCompleted");
        synchro.setResultInt(
                Integer.parseInt(
                        sc.readSingleMessage()
                )
        );
        synchro.synchro();
        synchro.synchro();
    }

    @Override
    protected ServerState decideState(){
        return new PutAttackPiece(thread);
    }
}
