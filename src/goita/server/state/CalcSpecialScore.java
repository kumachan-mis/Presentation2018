package goita.server.state;
import goita.server.GoitaServerThread;

class CalcSpecialScore extends ServerState {
    CalcSpecialScore(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        boolean isScoreGetter = sc.readYesOrNo();
        if(isScoreGetter) {
            synchro.setParentId(thread.threadId);
            synchro.setScoreDiff(
                    Integer.parseInt(
                            sc.readSingleMessage()
                    )
            );
        }
        synchro.synchro();
        synchro.synchro();
    }

    @Override
    protected ServerState decideState() {
        return new ShowScore(thread);
    }
}
