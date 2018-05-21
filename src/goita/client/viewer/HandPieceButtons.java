package goita.client.viewer;
import goita.Piece;
import goita.client.GUIClient;
import goita.client.state.GameInfo;

public class HandPieceButtons {
    private static GUIClient gui;
    private static GameInfo info;
    private int[] handPieceIndex;
    private Button[] handPiece = new Button[Piece.FIELD_PIECE];

    private static int ulx, uly;
    private static int width, height;
    private static int interval;

    public static void init(GUIClient gui) {
        HandPieceButtons.gui = gui;
        info = GameInfo.getInstance();

        int SIZE = gui.SIZE;
        ulx = 5 * SIZE / 41;
        uly = 36 * SIZE / 41;
        width = 3 * SIZE / 41;
        height = 4 * SIZE / 41;
        interval = SIZE / 41;
    }

    public HandPieceButtons() {
        handPieceIndex = info.getHandPieceIndex();
        int x = ulx;
        for(int pieceKind = 0; pieceKind < Piece.PIECE_KIND; ++pieceKind) {
            int begin = (pieceKind == 0)? 0 : handPieceIndex[pieceKind - 1];
            int end = handPieceIndex[pieceKind];

            for(int n = begin; n < end; ++n) {
                handPiece[n] = new Button(gui, x, uly,
                        width, height, Piece.getImgpath(pieceKind, 0));
                x += width + interval;
            }
        }
    }

    public void draw() {
        for(int i = 0; i < handPieceIndex[Piece.PIECE_KIND - 1]; ++i) handPiece[i].draw();
    }

    public void killAll() {
        for(int i = 0; i < handPieceIndex[Piece.PIECE_KIND - 1]; ++i) handPiece[i].kill();
    }

    private void kill(int pieceKind) {
        int begin = pieceKind == 0? 0 : handPieceIndex[pieceKind - 1];
        int end = handPieceIndex[pieceKind];
        for(int i = begin; i < end; ++i) handPiece[i].kill();
    }

    public void setForAttack() {
        if(info.getHandPiece()[0] < Piece.PIECE_NUM[0]
                && handPieceIndex[Piece.PIECE_KIND - 1] > 1) kill(0);
    }

    public void setForGuard() {
        int attackPiece = info.getAttackPiece();
        for(int i = 1; i < Piece.PIECE_KIND; ++i) {
            if(i != attackPiece) kill(i);
        }
        if(attackPiece == 0 || attackPiece > 5) kill(0);
    }

    public int whichClicked() {
        int clicked = -1;

        for(int i = 0; i < handPieceIndex[Piece.PIECE_KIND - 1]; ++i) {
            if(handPiece[i].isClicked()) {
                clicked = i + 1;
                break;
            }
        }

        if(clicked == -1) return -1;

        for(int pieceKind = 0; pieceKind < Piece.PIECE_KIND; ++pieceKind) {
            if(clicked <= handPieceIndex[pieceKind]) return pieceKind;
        }
        return -1;
    }
}