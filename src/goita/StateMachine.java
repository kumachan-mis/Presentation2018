package goita;

public abstract class StateMachine {
    public static final int PLAYER_NUM = 4;
    public static final int TEAM_NUM = 2;
    public static final int WINNER_SCORE = 150;

    public StateMachine doState() {
        executeState();
        return decideState();
    }

    protected abstract void executeState();

    protected abstract StateMachine decideState();
}