package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class Waiting extends ClientState{
    private boolean drawn = false;
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;
    private String waitingMessage;
    private String resultMessage;
    private boolean irregular = false;

    Waiting(GUIClient gui, String waitingMessage) {
        super(gui);
        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
        handPiece.killAll();
        this.waitingMessage = waitingMessage;
    }

    @Override
    protected void drawState() {
        if(!drawn) {
            view.resetGameView();
            view.showMessage(waitingMessage);
        }
        myFieldPiece.draw();
        handPiece.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("Waiting");
        if(!drawn) return;
        resultMessage = cc.readSingleMessage();
        if(resultMessage == null) return;
        System.err.println(resultMessage);

        switch (resultMessage) {
            case "conti":
                break;
            case "reset":
                break;
            case "secret":
                int newFieldPiece = Integer.parseInt(cc.readSingleMessage());
                info.putFieldPiece(info.getParentId(), newFieldPiece, false);
                break;
            case "attack":
                newFieldPiece = Integer.parseInt(cc.readSingleMessage());
                info.putFieldPiece(info.getParentId(), newFieldPiece, true);
                info.setAttackPiece(newFieldPiece);
                break;
            case "winnerJudge":
                irregular = cc.readYesOrNo();
                if(irregular) break;
                info.updateMainPlayerId();
            case "guard":
                break;
            case "guardCompleted":
                int newParentId = Integer.parseInt(cc.readSingleMessage());
                info.setParentId(newParentId);
                info.setAttackPiece(-1);
                newFieldPiece = Integer.parseInt(cc.readSingleMessage());
                info.putFieldPiece(newParentId, newFieldPiece, true);
                break;
            case "pass":
                info.updateMainPlayerId();
                int mainPlayerId = info.getMainPlayerId();
                irregular = (mainPlayerId == info.getParentId());
                break;
        }
    }

    @Override
    protected ClientState decideState() {
        if(resultMessage == null) {
            drawn = true;
            return this;
        }

        switch (resultMessage) {
            case "conti":
                return new BeginningOfGame(gui);
            case "reset":
                return new ReceiveHandPiece(gui);
            case "secret":
                return stillWaiting(info.getNickname()[info.getParentId()] + "が攻撃に使う駒を選択中");
            case "attack":
                return stillWaiting("");
            case "winnerJudge":
                if(irregular)
                    return new ShowFullOpen(gui);
                else if(info.getPlayerId() == info.getMainPlayerId())
                    return new GuardOrPass(gui);
                else
                    return stillWaiting(info.getNickname()[info.getMainPlayerId()] + "が防御か流すか選択中");
            case "guard":
                return stillWaiting(info.getNickname()[info.getMainPlayerId()] + "は防御を選択したようです");
            case "guardCompleted":
                return stillWaiting("防御成功\n" + info.getNickname()[info.getParentId()] + "が攻撃に使う駒を選択中");
            case "pass":
                if(irregular)
                    return new BeginningOfGame(gui);
                else if(info.getPlayerId() == info.getMainPlayerId())
                    return new GuardOrPass(gui);
                else
                    return stillWaiting(info.getNickname()[info.getMainPlayerId()] + "が防御か流すか選択中");
            default:
                return stillWaiting("");
        }
    }

    private ClientState stillWaiting(String waitingMessage) {
        drawn = false;
        irregular = false;
        resultMessage = null;
        this.waitingMessage = waitingMessage;
        return this;
    }
}