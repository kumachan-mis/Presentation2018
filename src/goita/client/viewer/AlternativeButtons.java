package goita.client.viewer;
import goita.client.GUIClient;

public class AlternativeButtons {
    private static int upper, left,  width, height, interval;
    private Button button1, button2;

    public static void init(GUIClient gui) {
        upper = 17 * gui.SIZE / 41;
        left = 13 * gui.SIZE / 41;
        width = 15 * gui.SIZE / 41;
        height = 3 * gui.SIZE / 41;
        interval = gui.SIZE / 41;
    }

    public AlternativeButtons(String name1, String name2) {
        int y = upper;
        button1 = new Button(left, y,
                width, height, "./" + name1 + ".jpg");
        y += height + interval;
        button2 = new Button(left, y,
                width, height, "./" + name2 + ".jpg");
    }

    public void draw() {
        button1.draw();
        button2.draw();
    }

    public void kill(int index) {
        if(index != 1 && index != 2) return;
        if(index == 1) button1.kill();
        else button2.kill();
    }

    public int whichClicked() {
        int clicked = -1;
        if(button1.isClicked()) clicked = 1;
        else if(button2.isClicked()) clicked = 2;
        return clicked;
    }
}