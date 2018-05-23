package goita.client.state;
import goita.Piece;
import goita.client.GUIClient;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class WinnerJudge extends ClientState {
    private boolean drawn = false;
    private boolean isWinner;
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;

    WinnerJudge() {
        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
        handPiece.killAll();
    }
    
    @Override
    protected void drawState() {
        if(!drawn) view.resetGameView();
        myFieldPiece.draw();
        handPiece.draw();
    }

    @Override
    protected void executeState() {
        if(!drawn) return;
        isWinner = (info.getNumOfFieldPiece(info.getPlayerId()) == Piece.FIELD_PIECE);
        cc.writeYesOrNo(isWinner);
        if(isWinner) {
            cc.writeSingleMessage(calcScore());
        } else {
            info.updateMainPlayerId();
        }
    }

    private int calcScore() {
        int scoreDiff;
        int[] myFieldPiece = info.getFieldPiece()[info.getPlayerId()];
        int keyPiece = myFieldPiece[Piece.FIELD_PIECE - 1];
        int subPiece = myFieldPiece[Piece.FIELD_PIECE - 2];
        boolean subIsOpen = info.getIsOpen(info.getPlayerId(), Piece.FIELD_PIECE - 2);

        if(keyPiece == subPiece && !subIsOpen) {
            scoreDiff = 2 * Piece.score[keyPiece];
        } else {
            scoreDiff = Piece.score[keyPiece];
        }
        return scoreDiff;
    }

    @Override
    protected ClientState decideState() {
        if(!drawn) {
            drawn = true;
            return this;
        }
        if(isWinner) return new ShowFullOpen();
        return new Waiting(info.getNickname()[info.getMainPlayerId()] + "が防御か流すか選択中");
    }
}
