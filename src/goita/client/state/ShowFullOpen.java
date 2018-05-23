package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.Button;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class ShowFullOpen extends ClientState {
    private Button ok;
    private boolean drawn = false;
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;

    ShowFullOpen() {
        super();
        ok = new Button(17 * gui.SIZE / 41, 19 * gui.SIZE / 41,
                7 * gui.SIZE / 41, 3 * gui.SIZE / 41,"./了解.jpg");
        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
        handPiece.killAll();
        info.fullOpen();
    }

    @Override
    protected void drawState() {
        if(!drawn) {
            view.resetGameView();
            view.showMessage("全員の場の駒を開示します");
        }
        myFieldPiece.draw();
        handPiece.draw();
        ok.draw();
    }

    @Override
    protected void executeState() {

    }

    @Override
    protected ClientState decideState() {
        if(!ok.isClicked()) {
            drawn = true;
            return this;
        }
        return new ShowScore();
    }
}
