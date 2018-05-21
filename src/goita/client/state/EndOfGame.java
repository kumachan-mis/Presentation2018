package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.Button;

class EndOfGame extends ClientState {
    private Button ok;
    boolean drawn = false;

    EndOfGame(GUIClient gui) {
        super(gui);
        ok = new Button(gui, 17 * gui.SIZE / 41, 37 * gui.SIZE / 41,
                7 * gui.SIZE / 41, 3 * gui.SIZE / 41, "./終わる.jpg");
    }

    @Override
    protected void drawState() {
        if(!drawn) view.showGameResult();
        ok.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("EndOfGame");
    }

    @Override
    protected ClientState decideState() {
        if(!ok.isClicked()) {
            drawn = true;
            return this;
        }
        gui.exit();
        return null;
    }
}
