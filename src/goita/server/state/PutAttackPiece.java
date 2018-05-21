package goita.server.state;
import goita.server.GoitaServerThread;

class PutAttackPiece extends ServerState {
    PutAttackPiece(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        synchro.setResultMessage("attack");
        synchro.setResultInt(
                Integer.parseInt(
                        sc.readSingleMessage()
                )
        );
        synchro.synchro();
        synchro.synchro();
    }

    @Override
    protected ServerState decideState() {
        return new WinnerJudge(thread);
    }
}
