package goita.client.state;
import goita.StateMachine;
import goita.client.GUIClient;
import goita.client.viewer.Button;

class ShowScore extends ClientState {
    private boolean drawn = false;
    private boolean endOfGame = false;
    private Button ok;
    ShowScore() {
        ok = new Button(17 * gui.SIZE / 41, 37 * gui.SIZE / 41,
                7 * gui.SIZE / 41, 3 * gui.SIZE / 41, "./了解.jpg");
    }

    @Override
    protected void executeState() {
        //System.err.println("ShowScore");
        if(drawn) return;
        int scoreGetter = info.getParentId();
        int scoreDiff = Integer.parseInt(cc.readSingleMessage());
        info.addScore(scoreGetter % StateMachine.TEAM_NUM, scoreDiff);

        int score = info.getScore()[scoreGetter % StateMachine.TEAM_NUM];
        endOfGame = (score >= StateMachine.WINNER_SCORE);
        cc.writeYesOrNo(endOfGame);
    }

    @Override
    protected void drawState() {
        if(!drawn) view.showScore();
        ok.draw();
    }

    @Override
    protected ClientState decideState() {
        if(!ok.isClicked()) {
            drawn = true;
            return this;
        }

        cc.writeYesOrNo(true);
        System.gc();

        if(endOfGame){
            return new EndOfGame();
        }
        info.resetGameInfo();
        return new ReceiveHandPiece();
    }

    @Override
    public ClientState doState() {
        executeState();
        drawState();
        return decideState();
    }
}
