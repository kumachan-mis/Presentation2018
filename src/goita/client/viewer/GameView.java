package goita.client.viewer;
import goita.Piece;
import goita.StateMachine;
import goita.client.GUIClient;
import goita.client.state.GameInfo;
import processing.core.PImage;

public class GameView {
    private final GUIClient gui;
    private int SIZE;
    private int UNIT;
    private GameInfo info = GameInfo.getInstance();
    private PImage bgImage;

    public GameView(GUIClient gui) {
        this.gui = gui;
    }

    private void initSettings() {
        SIZE = gui.SIZE;
        UNIT = SIZE / 41;
        bgImage = gui.loadImage("./背景.jpg");
        bgImage.resize(SIZE, SIZE);
        gui.textAlign(gui.LEFT, gui.CENTER);
    }

    public void showStartView() {
        initSettings();
        PImage start = gui.loadImage("./スタート画面.jpg");
        start.resize(SIZE, SIZE);
        gui.background(start);
    }

    public void resetGameView() {
        gui.background(bgImage);
        showPlayername();
        showOthersField();
        showAttackPieces();
    }

    public void showMessage(String message) {
        gui.textSize(20);
        gui.fill(0x0, 0x0, 0x0);
        gui.text(message, 13 * UNIT, 12 * UNIT, 15 * UNIT, 4 * UNIT);
    }

    public void showScore() {
        gui.background(bgImage);

        int[] score = info.getScore();
        gui.textSize(60);
        gui.fill(0x0, 0x0, 0x0);
        gui.text("成績", SIZE / 2 - gui.textWidth("成績") / 2, 3 * UNIT);
        gui.textSize(45);
        String[] nickname = info.getNickname();

        for (int t = 0; t < StateMachine.TEAM_NUM; ++t) {
            int dy = 0;
            for(int p = t; p < StateMachine.PLAYER_NUM; p += StateMachine.TEAM_NUM) {
                gui.text(nickname[p], (t +1) * SIZE / (StateMachine.TEAM_NUM + 1) - gui.textWidth(nickname[p]) / 2, 15 * UNIT + dy);
                dy += 6 * UNIT;
            }
            String scoreString = Integer.toString(score[t]) + "点";
            gui.text(scoreString, (t +1) * SIZE / (StateMachine.TEAM_NUM + 1) - gui.textWidth(scoreString) / 2, 15 * UNIT + dy);
        }
    }

    public void showGameResult() {
        gui.background(bgImage);
        int winTeam = -1;
        int[] finalScore = info.getScore();
        for (int t = 0; t < StateMachine.TEAM_NUM; ++t) {
            if (finalScore[t] >= StateMachine.WINNER_SCORE) {
                winTeam = t;
                break;
            }
        }

        gui.textSize(60);
        gui.fill(0x0, 0x0, 0x0);
        gui.text("勝者", SIZE / 2 - gui.textWidth("勝者") / 2, 3 * UNIT);
        gui.textSize(6 * UNIT);
        String[] nickname = info.getNickname();
        int dy = 0;

        for(int p = winTeam; p < StateMachine.PLAYER_NUM; p += StateMachine.TEAM_NUM) {
            gui.text(nickname[p], SIZE / 2 - gui.textWidth(nickname[p]) / 2, 15 * UNIT + dy);
            dy += 6 * UNIT;
        }
    }

    private void showPlayername() {
        int ulx = UNIT, uly = UNIT;
        int width = 11 * UNIT, height = 2 * UNIT;
        int interval = UNIT;
        String[] nickname = info.getNickname();

        for(int p = 0; p < StateMachine.PLAYER_NUM; ++p) {
            if(p == info.getParentId()) {
                gui.image(gui.loadImage("./親.jpg"),
                        ulx, uly, width, height);
            } else {
                gui.image(gui.loadImage("./子.jpg"),
                        ulx, uly, width, height);
            }
            gui.fill(0xff, 0xff, 0xff);
            gui.textSize(UNIT);
            gui.text(nickname[p], ulx + width / 2 - gui.textWidth(nickname[p]) / 2, uly + height / 2);
            uly += height + interval;
        }
    }

    private void showPartOfField(int type, int ulx, int uly) {
        int id = (info.getPlayerId() + type) % StateMachine.PLAYER_NUM;
        int numOfFieldPiece = info.getNumOfFieldPiece(id);
        int[] fieldPiece = info.getFieldPiece()[id];

        int x = ulx;
        for(int j = 0; j < Piece.FIELD_PIECE / 2; ++j) {
            int y = uly;
            if(2 * j < numOfFieldPiece) {
                int pieceKind = info.getIsOpen(id, 2 * j)?
                        fieldPiece[2 * j] : -1;
                drawImage(type, pieceKind, x, y, 3 * UNIT, 4 * UNIT);
            } else {
                break;
            }

            y = uly + 5 * UNIT;
            if(2 * j + 1 < numOfFieldPiece)
                drawImage(type, fieldPiece[2 * j + 1], x, y, 3 * UNIT, 4 * UNIT);
            else break;

            x += 4 * UNIT;
        }
    }

    private void drawImage(int type, int pieceKind, int x, int y, int width, int height) {
        type %= 4;
        PImage img = pieceKind != -1?
                gui.loadImage(Piece.getImgpath(pieceKind, type)) :
                gui.loadImage(Piece.getSecretImgpath(type));

        switch (type) {
            case 1:
                gui.image(img, SIZE - (y + height), x, height, width);
                break;
            case 2:
                gui.image(img, SIZE - (x + width), SIZE - (y + height), width, height);
                break;
            case 3:
                gui.image(img, y, SIZE - (x + width), height, width);
                break;
        }
    }

    private void showOthersField() {
        showPartOfField(1, 13 * UNIT, 30 * UNIT);
        showPartOfField(2, 13 * UNIT, 30 * UNIT);
        showPartOfField(3, 13 * UNIT, 30 * UNIT);
    }

    private void showAttackPieces() {
        int attackPiece = info.getAttackPiece();
        if(attackPiece == -1) return;
        gui.image(
                gui.loadImage(Piece.getImgpath(attackPiece, 0)),
                32 * UNIT, 2 * UNIT,
                6 * UNIT, 8 * UNIT
        );
    }
}
