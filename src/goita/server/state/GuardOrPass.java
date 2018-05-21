package goita.server.state;
import goita.server.GoitaServerThread;

class GuardOrPass extends ServerState {
    private int whichClicked = -1;
    private boolean irregular = false;

    GuardOrPass(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        whichClicked = Integer.parseInt(sc.readSingleMessage());
        switch (whichClicked){
            case 1:
                synchro.setResultMessage("guard");
                break;

            case 2:
                synchro.updateMainPlayerId();
                irregular = (synchro.getMainPlayerId() == synchro.getParentId());
                synchro.setResultMessage("pass");
                break;
        }
        synchro.synchro();
        synchro.synchro();
    }

    @Override
    protected ServerState decideState() {
        switch (whichClicked) {
            case 1:
                return new PutGuardPiece(thread);
            case 2:
                if(irregular)
                    return new BeginningOfGame(thread);
                else
                    return new Waiting(thread);
            default:
                return this;
        }
    }
}
