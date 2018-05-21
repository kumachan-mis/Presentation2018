package goita;
import java.util.ArrayList;
import java.util.Collections;

public class Piece {
    public static final int PIECE_KIND = 8;
    public static final int FIELD_PIECE = 8;
    public static final int[] PIECE_NUM = {2, 2, 2, 4, 4, 4, 4, 10};
    public static final int[] score = {50, 40, 40, 30, 30, 20, 20, 10};
    private static final String[] name = {"王", "飛", "角", "金", "銀", "馬", "香", "し"};

    private ArrayList<Integer> pieceList = new ArrayList<>();
    private static Piece piece = new Piece();

    public static String getImgpath(int pieceKind, int type) {
        if(type >= StateMachine.PLAYER_NUM || type < 0) return "";
        else return "./piece/" + name[pieceKind] + type + ".jpg";
    }

    public static String getSecretImgpath(int type) {
        if(type >= StateMachine.PLAYER_NUM || type < 0) return "";
        else return "./piece/伏" + type + ".jpg";
    }
    public static Piece getInstance() {
        return piece;
    }

    private Piece() {
        for(int i = 0; i < Piece.PIECE_NUM.length; ++i) {
			for(int j = 0; j < Piece.PIECE_NUM[i]; ++j) {
                pieceList.add(i);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(pieceList);
    }

    public int get(int index) {
        return pieceList.get(index);
    }
}