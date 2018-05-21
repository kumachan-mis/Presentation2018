package goita.server.state;
import goita.server.GoitaServerThread;

class WinnerJudge extends ServerState {
    private boolean isWinner = false;

    WinnerJudge(GoitaServerThread thread) {
        super(thread);

    }

    @Override
    protected void executeState() {
        synchro.setResultMessage("winnerJudge");
        isWinner = sc.readYesOrNo();
        synchro.setResultInt(isWinner? 1 : 0);

        if(isWinner) {
            synchro.setScoreDiff(
                    Integer.parseInt(
                            sc.readSingleMessage()
                    )
            );
        } else {
            synchro.updateMainPlayerId();
        }
        synchro.synchro();
        synchro.synchro();
    }

    @Override
    protected ServerState decideState() {
        if(isWinner) return new ShowScore(thread);
        return new Waiting(thread);
    }
}
