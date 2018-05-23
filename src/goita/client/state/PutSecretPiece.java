package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class PutSecretPiece extends ClientState{
    private boolean drawn = false;
    private int whichClicked = -1;
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;

    PutSecretPiece() {
        super();
        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
    }

    @Override
    protected void drawState() {
        if(!drawn) {
            view.resetGameView();
            view.showMessage("場に伏せておく駒を選択してください");
        }
        myFieldPiece.draw();
        handPiece.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("PutSecretPiece");
        whichClicked = handPiece.whichClicked();
        if(whichClicked == -1) return;

        info.useHandPiece(whichClicked);
        info.putFieldPiece(info.getPlayerId(), whichClicked, false);
        cc.writeSingleMessage(whichClicked);
    }

    @Override
    protected ClientState decideState() {
        if(whichClicked == -1) {
            drawn = true;
            return this;
        }
        return new PutAttackPiece();
    }
}
