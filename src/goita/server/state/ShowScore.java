package goita.server.state;
import goita.server.GoitaServerThread;

class ShowScore extends ServerState {
    private boolean endOfGame = false;

    ShowScore(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        sc.writeSingleMessage(synchro.getScoreDiff());
        endOfGame = sc.readYesOrNo();
    }

    @Override
    protected ServerState decideState() {
        sc.readSingleMessage();
        System.gc();
        synchro.synchro();

        if(endOfGame) {
            thread.endOfGame();
            return null;
        }
        synchro.resetParams();
        return new ReceiveHandPiece(thread);
    }
}
