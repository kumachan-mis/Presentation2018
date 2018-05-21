package goita.server;
import goita.Piece;
import goita.StateMachine;

public class ThreadSynchro {
    private boolean in = false;
    private boolean out = true;
    private int waitnum = 0;

    private int parentId;
    private int mainPlayerId;
    private int scoreDiff = 0;
    private String resultMessage = null;
    private int resultInt = 0;

    private ThreadSynchro() {
        parentId = -1;
        mainPlayerId = -1;
        resetParams();
    }

    public synchronized int getParentId() {
        return parentId;
    }

    public synchronized void setParentId(int parentId) {
        this.parentId = parentId;
        this.mainPlayerId = parentId;
    }

    public synchronized int getMainPlayerId() {
        return mainPlayerId;
    }

    public synchronized void updateMainPlayerId() {
        mainPlayerId = (mainPlayerId + 1) % StateMachine.PLAYER_NUM;
    }

    public synchronized int getScoreDiff() {
        return scoreDiff;
    }

    public synchronized void setScoreDiff(int scoreDiff) {
        this.scoreDiff = scoreDiff;
    }

    public synchronized String getResultMessage() {
        return resultMessage;
    }

    public synchronized void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public synchronized int getResultInt() { return resultInt; }
    public synchronized void setResultInt(int resultInt) { this.resultInt = resultInt; }

    public synchronized void setSpecialRule(int infantry) {
        if(resultInt == 3 || resultInt == 2) return;

        if(infantry > Piece.PIECE_NUM[Piece.PIECE_KIND - 1] / 2) resultInt = 3;
        else if(infantry == Piece.PIECE_NUM[Piece.PIECE_KIND - 1] / 2 && resultInt == 1) resultInt = 2;
        else if(infantry == Piece.PIECE_NUM[Piece.PIECE_KIND - 1] / 2) resultInt = 1;
    }

    public synchronized void setMaxInfantory(int infantory) {
        if(resultInt < infantory) resultInt = infantory;
    }

    public synchronized void resetParams() {
        scoreDiff = 0;
        resultMessage = null;
        resultInt = 0;
    }

    private synchronized void in() {
        try {
            if(waitnum == StateMachine.PLAYER_NUM - 1) {
                waitnum++;
                out = false;
                in = true;
                notifyAll();
            }
            while(!in) {
                waitnum++;
                wait();
            }
        } catch(InterruptedException e) {
            System.err.println(e);
        }
    }

    private synchronized void out() {
        try {
            if(waitnum == 1) {
                waitnum--;
                in = false;
                out = true;
                notifyAll();
            }

            while(!out) {
                waitnum--;
                wait();
            }
        } catch(InterruptedException e) {
            System.err.println(e);
        }
    }

    public void synchro() {
        in();
        out();  
    }

    private static ThreadSynchro instance = new ThreadSynchro();
    public static ThreadSynchro getInstance() {
        return instance;
    }
}