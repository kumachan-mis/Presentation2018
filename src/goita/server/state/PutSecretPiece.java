package goita.server.state;
import goita.server.GoitaServerThread;

class PutSecretPiece extends ServerState {
    PutSecretPiece(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        synchro.setResultMessage("secret");
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
        return new PutAttackPiece(thread);
    }
}
