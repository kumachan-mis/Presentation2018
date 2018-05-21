package goita.client.state;
import goita.Piece;
import goita.StateMachine;
import goita.client.GUIClient;

class CalcSpecialScore extends ClientState {
    private boolean drawn = false;
    private int maxInfantry;

    CalcSpecialScore(GUIClient gui, int maxInfantory) {
        super(gui);
        this.maxInfantry = maxInfantory;
    }

    @Override
    protected void drawState() {
        if(drawn) return;
        view.resetGameView();
        view.showMessage(specialScoreMessage());
    }

    private String specialScoreMessage() {
        switch (maxInfantry) {
            case 6:
                return "六し成立";
            case 7:
                return "七し成立";
            case 8:
                return "八し成立";
            default:
                return "";
        }
    }

    @Override
    protected void executeState() {
        //System.err.println("CalcSpecialScore");
        if(!drawn) return;
        int infantry = info.getHandPiece()[Piece.PIECE_KIND - 1];
        boolean isScoreGetter = info.getHandPiece()[Piece.PIECE_KIND - 1] > Piece.PIECE_NUM[Piece.PIECE_KIND - 1] / 2;

        cc.writeYesOrNo(isScoreGetter);
        if(isScoreGetter) cc.writeSingleMessage(calcScore(infantry));
    }

    private int calcScore(int infantry) {
        if(infantry == Piece.FIELD_PIECE) {
            return 100;
        } else if(infantry == Piece.FIELD_PIECE - 1) {
            for(int i = 0; i < Piece.PIECE_KIND - 1; ++i) {
                if(info.getHandPiece()[i] > 0) return 2 * Piece.score[i];
            }
        } else {
            for(int i = 0; i < Piece.PIECE_KIND - 1; ++i) {
                if(info.getHandPiece()[i] > 0) return Piece.score[i];
            }
        }
        return 0;
    }

    @Override
    protected ClientState decideState() {
        if(!drawn) {
            drawn = true;
            return this;
        }
        return new ShowScore(gui);
    }
}
