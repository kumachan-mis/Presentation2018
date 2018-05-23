package goita.client.state;
import goita.ReadWrite;
import goita.StateMachine;
import goita.client.GUIClient;
import goita.client.viewer.GameView;

public abstract class ClientState extends StateMachine {
    static GUIClient gui;
    static ReadWrite cc;
    static GameInfo info = GameInfo.getInstance();
    static GameView view;

    public static void init(GUIClient gui) {
        ClientState.gui = gui;
        cc = gui.getReadWrite();
        view = gui.getView();
    }

    ClientState() {

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