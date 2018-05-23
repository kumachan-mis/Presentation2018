package goita.client.viewer;
import goita.client.GUIClient;
import processing.core.PImage;
import java.util.HashMap;

public class Button {
    private static final float CHANGE_RATE = 0.01f;
    private static HashMap<Character, Float> SCALE = new HashMap<Character, Float>() {
        { put('S', 0.98f);  put('M', 1.00f);  put('L', 1.02f);}
    };

    private static GUIClient gui;
    private final int ulx, uly;
    private final int width, height;
    private final PImage img;
    private char targetLevel = 'M';
    private float currentScale = 1.00f;
    private boolean isClicked = false;
    private boolean clickCompleted = false;
    private  boolean isActive = true;

    public static void init(GUIClient gui) {
        Button.gui = gui;
    }

    public Button(int ulx, int uly, int width, int height, String imgpath) {
        this.ulx = ulx;
        this.uly = uly;
        this.width = width;
        this.height = height;
        PImage img = gui.loadImage(imgpath);
        img.resize(width, height);
        this.img = img;

    }

    private boolean mouseOn() {
        return ulx <= gui.mouseX && gui.mouseX <= ulx + width &&
                uly <= gui.mouseY && gui.mouseY <= uly + height;
    }

    private void setTargetScale() {
        boolean on = mouseOn();
        if(gui.mousePressed && on) {
            targetLevel = 'S';
            isClicked = true;
        } else if(isClicked || on) {
            targetLevel = 'L';
        } else {
            targetLevel = 'M';
        }

    }

    private void changeStale() {
        float targetScale = SCALE.get(targetLevel);
        if(currentScale < targetScale) {
            currentScale = Math.min(targetScale, (1.00f + CHANGE_RATE) * currentScale);
        } else {
            currentScale = Math.max(targetScale, (1.00f - CHANGE_RATE) * currentScale);
        }
    }

    public void draw() {
        if(!isActive) {
            gui.image(img, ulx, uly);
            return;
        }

        float maxWidth = SCALE.get('L') * width;
        float maxHeight = SCALE.get('L') * height;
        float dx = (width - maxWidth) / 2.00f;
        float dy = (height - maxHeight) / 2.00f;
        gui.fill(0xc6, 0xf1, 0xb7);
        gui.noStroke();
        gui.rect(ulx + dx, uly + dy, maxWidth, maxHeight);

        setTargetScale();
        changeStale();
        float currentWidth = currentScale * width;
        float currentHeight = currentScale * height;
        dx = (width - currentWidth) / 2.00f;
        dy = (height - currentHeight) / 2.00f;

        gui.image(img, ulx + dx, uly + dy, currentWidth, currentHeight);
        clickCompleted = (isClicked && currentScale == SCALE.get('L'));
    }

    public boolean isClicked() {
        return clickCompleted;
    }

    private void reset() {
        clickCompleted = false;
        isClicked = false;
    }

    void kill() {
        isActive = false;
        reset();
    }
}
