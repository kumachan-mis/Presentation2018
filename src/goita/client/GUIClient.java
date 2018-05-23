package goita.client;
import goita.ReadWrite;
import goita.client.state.ClientState;
import goita.client.state.Start;
import goita.client.viewer.*;
import processing.core.PApplet;

public class GUIClient extends PApplet{
    public int SIZE;
    private ReadWrite cc = null;
    private GameView view = new GameView(this);
    private ClientState state;

    @Override
    public void settings() {
        SIZE = 4 * displayHeight / 5;
        size(SIZE, SIZE);
        cc = GoitaClient.getReadWrite();

        ClientState.init(this);
        Button.init(this);
        HandPieceButtons.init(this);
        AlternativeButtons.init(this);
        MyFieldPiece.init(this);

        state = new Start();
    }

    @Override
    public void setup() {
        textFont(createFont("Hiragino Mincho ProN", 60));
    }

    @Override
    public void draw() {
        state = state.doState();
    }

    public ReadWrite getReadWrite() {
        return cc;
    }

    public GameView getView() {
        return view;
    }
}