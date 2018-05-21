package goita.client.state;
import goita.ReadWrite;
import goita.StateMachine;
import goita.client.GUIClient;
import goita.client.viewer.GameView;

public abstract class ClientState extends StateMachine {
    final GUIClient gui;
    final ReadWrite cc;
    final GameInfo info = GameInfo.getInstance();
    final GameView view;

    ClientState(GUIClient gui) {
        super();
        this.gui = gui;
        cc = gui.getReadWrite();
        view = gui.getView();
    }

    @Override
    public ClientState doState() {
        drawState();
        executeState();
        return decideState();
    }
    protected abstract void drawState();
    @Override
    protected abstract ClientState decideState();
}