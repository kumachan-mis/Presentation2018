package goita.server.state;
import goita.server.GoitaServerThread;

class Waiting extends ServerState {
    private String resultMessage;
    private boolean irregular = false;

    Waiting(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        synchro.synchro();
        resultMessage = synchro.getResultMessage();
        sc.writeSingleMessage(resultMessage);

        switch (resultMessage) {
            case "conti":
                break;
            case "reset":
                break;
            case "secret":
                sc.writeSingleMessage(synchro.getResultInt());
                break;
            case "attack":
                sc.writeSingleMessage(synchro.getResultInt());
                break;
            case "winnerJudge":
                irregular = (synchro.getResultInt() == 1);
                sc.writeYesOrNo(irregular);
                break;
            case "guard":
                break;
            case "guardCompleted":
                sc.writeSingleMessage(synchro.getParentId());
                sc.writeSingleMessage(synchro.getResultInt());
                break;
            case "pass":
                int mainPlayerId = synchro.getMainPlayerId();
                irregular = (mainPlayerId == synchro.getParentId());
                break;
        }
        synchro.synchro();
    }

    @Override
    protected ServerState decideState() {
        switch (resultMessage) {
            case "conti":
                return new BeginningOfGame(thread);
            case "reset":
                return new ReceiveHandPiece(thread);
            case "secret":
                return stillWaiting();
            case "attack":
                return stillWaiting();
            case "winnerJudge":
                if(irregular)
                    return new ShowScore(thread);
                else if(thread.threadId == synchro.getMainPlayerId())
                    return new GuardOrPass(thread);
            case "guard":
                return stillWaiting();
            case "guardCompleted":
                return stillWaiting();
            case "pass":
                if(irregular)
                    return new BeginningOfGame(thread);
                else if(thread.threadId == synchro.getMainPlayerId())
                    return new GuardOrPass(thread);
            default:
                return stillWaiting();
        }
    }

    private ServerState stillWaiting() {
        irregular = false;
        resultMessage = null;
        return this;
    }
}
