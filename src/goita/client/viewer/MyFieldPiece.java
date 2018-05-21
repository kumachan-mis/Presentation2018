package goita.client.viewer;

import goita.Piece;
import goita.client.GUIClient;
import goita.client.state.GameInfo;
import processing.core.PImage;

public class MyFieldPiece {
    private static GUIClient gui;
    private static GameInfo info;
    private static int id;
    private static PImage secret;
    private int numOfFieldPiece;
    private PImage[] fieldPiece;

    private static int ulx, uly;
    private static int width, height;
    private static int interval;

    public static void init(GUIClient gui) {
        MyFieldPiece.gui = gui;
        info = GameInfo.getInstance();
        id = info.getPlayerId();
        secret = gui.loadImage(Piece.getSecretImgpath(0));

        int SIZE = gui.SIZE;
        ulx = 13 * SIZE / 41;
        uly = 25 * SIZE / 41;
        width = 3 * SIZE / 41;
        height = 4 * SIZE / 41;
        interval = SIZE / 41;
        secret.resize(width, height);
    }

    public MyFieldPiece() {
        numOfFieldPiece = info.getNumOfFieldPiece(id);
        fieldPiece = new PImage[numOfFieldPiece];

        int[] fp = info.getFieldPiece()[id];

        for(int i = 0; i < numOfFieldPiece; ++i) {
            fieldPiece[i] = gui.loadImage(Piece.getImgpath(fp[i], 0));
            fieldPiece[i].resize(width, height);
        }
    }

    public void draw() {
        int x = ulx;
        for(int j = 0; j < Piece.FIELD_PIECE / 2; ++j) {
            int y = uly;
            if(2 * j >= numOfFieldPiece) break;
            if(info.getIsOpen(id, 2 * j) || isClicked(2 * j))
                gui.image(fieldPiece[2 * j], x, y);
            else
                gui.image(secret, x, y);

            y = uly + height + interval;
            if(2 * j + 1 >= numOfFieldPiece) break;
            gui.image(fieldPiece[2 * j + 1], x, y);

            x += width + interval;
        }
    }

    private boolean isClicked(int index) {
        int x = ulx + (index / 2) * (width + interval);
        return gui.mousePressed &&
                x <= gui.mouseX && gui.mouseX <= x + width &&
                uly <= gui.mouseY && gui.mouseY <= uly + height;
    }
}
