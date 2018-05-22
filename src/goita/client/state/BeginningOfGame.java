package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class BeginningOfGame extends ClientState {
    private boolean drawn = false;
    private int parentId;
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;

    BeginningOfGame(GUIClient gui) {
        super(gui);
        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
    }

    @Override
    protected void drawState() {
        if(!drawn) view.resetGameView();
        myFieldPiece.draw();
        handPiece.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("BeginningOfGame");
        if(!drawn) return;
        parentId = Integer.parseInt(cc.readSingleMessage());
        info.setParentId(parentId);
    }

    @Override
    protected ClientState decideState() {
        if(!drawn) {
            drawn = true;
            return this;
        } else if(info.getPlayerId() == parentId) {
            return new PutSecretPiece(gui);
        }
        return new Waiting(gui, info.getNickname()[parentId] +"が伏せる駒を選択中");
    }
}