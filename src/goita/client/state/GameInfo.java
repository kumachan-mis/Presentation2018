package goita.client.state;
import goita.Piece;
import goita.StateMachine;

public class GameInfo {
    private int playerId;
    private int mainPlayerId;
    private int parentId;
    private String[] nickname = new String[StateMachine.PLAYER_NUM];

    private int attackPiece;
    private int[] handPiece = new int[Piece.PIECE_KIND];
    private int[] handPieceIndex = new int[Piece.PIECE_KIND];

    private int[] numOfFieldPiece = new int[StateMachine.PLAYER_NUM];
    private boolean[][] isOpen = new boolean[StateMachine.PLAYER_NUM][Piece.FIELD_PIECE / 2];
    private int[][] fieldPiece = new int[StateMachine.PLAYER_NUM][Piece.FIELD_PIECE];

    private int[] score = new int[StateMachine.TEAM_NUM];

    private GameInfo() {
        playerId = -1;
        for(int t = 0; t < StateMachine.TEAM_NUM; ++t) score[t] = 0;
        resetGameInfo();
    }

    void resetGameInfo() {
        parentId = -1;
        mainPlayerId = -1;
        attackPiece = -1;

        for(int i = 0; i < Piece.PIECE_KIND; ++i) {
            handPieceIndex[i] = 0;
            handPiece[i] = -1;
        }

        for(int id = 0; id < StateMachine.PLAYER_NUM; ++id) numOfFieldPiece[id] = 0;

        for(int id = 0; id < StateMachine.PLAYER_NUM; ++id) {
            for(int p = 0; p < Piece.FIELD_PIECE; ++ p) {
                fieldPiece[id][p] = -1;
                if(p % 2 == 0) isOpen[id][p / 2] = false;
            }
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public int getParentId() {
        return parentId;
    }

    void setParentId(int parentId) {
        this.parentId = parentId;
        this.mainPlayerId = parentId;
    }

    int getMainPlayerId() {
        return mainPlayerId;
    }

    void updateMainPlayerId()  {
        mainPlayerId = (mainPlayerId + 1) % StateMachine.PLAYER_NUM;
    }

    public String[] getNickname() {
        return nickname;
    }

    void setNickname(String[] nickname) {
        this.nickname = nickname;
    }

    public int getAttackPiece() {
        return attackPiece;
    }

    void setAttackPiece(int attackPiece) {
        this.attackPiece = attackPiece;
    }

    public int[] getHandPiece() {
        return handPiece;
    }

    public int[] getHandPieceIndex() { return handPieceIndex; }

    void setHandPiece(int[] handPiece) {
        this.handPiece = handPiece;
        handPieceIndex[0] = handPiece[0];
        for(int i = 1; i < Piece.PIECE_KIND; ++i) handPieceIndex[i] = handPiece[i] + handPieceIndex[i - 1];
    }

    void useHandPiece(int pieceKind) {
        this.handPiece[pieceKind]--;
        for(int i = pieceKind; i < Piece.PIECE_KIND; ++i) handPieceIndex[i]--;
    }

    public int[][] getFieldPiece() {
        return fieldPiece;
    }

    public boolean getIsOpen(int id, int index) {
        if(index % 2 == 1) return true;
        else return isOpen[id][index / 2];
    }

    void fullOpen() {
        for(int id = 0; id < StateMachine.PLAYER_NUM; ++id) {
            for(int index = 0; index < Piece.PIECE_KIND / 2; index++) {
                isOpen[id][index] = true;
            }
        }
    }
    public int getNumOfFieldPiece(int id) {
        return numOfFieldPiece[id];
    }

    void putFieldPiece(int id, int pieceKind, boolean isOpen) {
        this.fieldPiece[id][numOfFieldPiece[id]] = pieceKind;
        if(numOfFieldPiece[id] % 2 == 0) this.isOpen[id][numOfFieldPiece[id] / 2] = isOpen;
        numOfFieldPiece[id]++;
    }

    public int[] getScore() {
        return score;
    }

    void addScore(int team, int scoreDiff) {
        score[team] += scoreDiff;
    }

    private static GameInfo info = new GameInfo();
    public static GameInfo getInstance() {
        return info;
    }
}
