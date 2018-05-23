package goita.client.state;
import goita.StateMachine;
import goita.client.GUIClient;
import goita.client.viewer.Button;

public class Start extends ClientState {
    private Button ok;
    private boolean drawn = false, signalReceived = false;

    public Start() {
        ok = new Button(17 * gui.SIZE / 41, 37 * gui.SIZE / 41,
                7 * gui.SIZE / 41, 3 * gui.SIZE / 41, "./images/始める.jpg");
    }

    @Override
    protected void drawState() {
        if(!drawn) view.showStartView();
        ok.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("Start");
        if(!drawn) return;
        if(!signalReceived) {
            info.setNickname(cc.readMultiMessages(StateMachine.PLAYER_NUM));
            signalReceived = true;
        }
    }

    @Override
    protected ClientState decideState() {
        if(!ok.isClicked() || !signalReceived) {
            drawn = true;
            return this;
        }
        cc.writeYesOrNo(true);
        return new ReceiveHandPiece();
    }
}