package goita.client;
import goita.ReadWrite;
import goita.client.state.ClientState;
import goita.client.state.Start;
import goita.client.viewer.GameView;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;
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
        state = new Start(this);
        HandPieceButtons.init(this);
        MyFieldPiece.init(this);
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