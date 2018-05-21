package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class PutGuardPiece extends ClientState {
    private boolean drawn = false;
    private int whichClicked = -1;
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;

    PutGuardPiece(GUIClient gui) {
        super(gui);
        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
        handPiece.setForGuard();
    }

    @Override
    protected void drawState() {
        if(!drawn) {
            view.resetGameView();
            view.showMessage("防御に使う駒を選択してください");
        }
        myFieldPiece.draw();
        handPiece.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("PutGuardPiece");
        whichClicked = handPiece.whichClicked();
        if(whichClicked == -1) return;

        info.setParentId(info.getPlayerId());
        info.setAttackPiece(-1);
        info.useHandPiece(whichClicked);
        info.putFieldPiece(info.getPlayerId(), whichClicked, true);
        cc.writeSingleMessage(whichClicked);
    }

    @Override
    protected ClientState decideState() {
        if(whichClicked == -1) {
            drawn = true;
            return this;
        }
        return new PutAttackPiece(gui);
    }
}
