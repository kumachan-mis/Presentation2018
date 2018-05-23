package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.AlternativeButtons;
import goita.client.viewer.HandPieceButtons;

class TalkWithFriend extends ClientState {
    private boolean drawn = false;
    private int whichClicked = -1;
    private AlternativeButtons contiOrReset = new AlternativeButtons("続行", "配り直し");
    private HandPieceButtons handPiece;

    TalkWithFriend() {
        handPiece = new HandPieceButtons();
        handPiece.killAll();
    }

    @Override
    protected void drawState() {
        if(!drawn) {
            view.resetGameView();
            view.showMessage("五し成立\n続行か配り直しか決めてください");
        }
        contiOrReset.draw();
        handPiece.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("TalkWithFriend");
        whichClicked = contiOrReset.whichClicked();
        switch (whichClicked) {
            case 1:
                cc.writeSingleMessage(whichClicked);
                break;
            case 2:
                cc.writeSingleMessage(whichClicked);
                break;
            default:
                break;
        }
    }

    @Override
    protected ClientState decideState() {
        switch (whichClicked) {
            case 1:
                return new BeginningOfGame();
            case 2:
                return new ReceiveHandPiece();
            default:
                drawn = true;
                return this;
        }
    }
}
