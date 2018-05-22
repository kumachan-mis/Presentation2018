package goita.client.viewer;
import goita.client.GUIClient;

public class AlternativeButtons {
    private int upper, left,  width, height, interval;
    private final GUIClient gui;
    private Button button1, button2;

    public AlternativeButtons(GUIClient gui, String name1, String name2) {
        this.gui = gui;
        upper = 17 * gui.SIZE / 41;
        left = 13 * gui.SIZE / 41;
        width = 15 * gui.SIZE / 41;
        height = 3 * gui.SIZE / 41;
        interval = gui.SIZE / 41;

        button1 = new Button(gui, left, upper,
                width, height, "./" + name1 + ".jpg");
        upper += height + interval;
        button2 = new Button(gui, left, upper,
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