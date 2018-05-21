package goita.server.state;
import goita.server.GoitaServerThread;

class TalkWithFriend extends ServerState{
    private int whichClicked = -1;

    TalkWithFriend(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        whichClicked = Integer.parseInt(sc.readSingleMessage());
        switch (whichClicked) {
            case 1:
                synchro.setResultMessage("conti");
                break;
            case 2:
                synchro.setResultMessage("reset");
                break;
        }
        synchro.synchro();
        synchro.synchro();
    }

    @Override
    protected ServerState decideState() {
        switch (whichClicked) {
            case 1:
                return new BeginningOfGame(thread);
            case 2:
                return new ReceiveHandPiece(thread);
            default:
                return this;
        }
    }
}
