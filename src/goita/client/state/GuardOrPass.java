package goita.client.state;
import goita.client.GUIClient;
import goita.client.viewer.AlternativeButtons;
import goita.client.viewer.HandPieceButtons;
import goita.client.viewer.MyFieldPiece;

class GuardOrPass extends ClientState{
    private boolean drawn = false;
    private int whichClicked = -1;
    private boolean irregular = false;
    private boolean hasGuardian;
    private boolean kingIsAvailable;

    private AlternativeButtons guardOrPass = new AlternativeButtons("防御", "流す");
    private MyFieldPiece myFieldPiece;
    private HandPieceButtons handPiece;

    GuardOrPass() {
        int attackPiece = info.getAttackPiece();
        hasGuardian = (info.getHandPiece()[attackPiece] > 0);
        kingIsAvailable = (info.getHandPiece()[0] > 0 && 1 <= attackPiece && attackPiece <= 5);

        myFieldPiece = new MyFieldPiece();
        handPiece = new HandPieceButtons();
        handPiece.killAll();
        if(!hasGuardian && !kingIsAvailable) guardOrPass.kill(1);
    }

    @Override
    protected void drawState() {
        if(!drawn) {
            view.resetGameView();

            String message;
            if(!hasGuardian && !kingIsAvailable)
                message = info.getNickname()[info.getParentId()] + "が攻めてきました\n防御できません";
            else if(!hasGuardian)
                message = info.getNickname()[info.getParentId()] + "が攻めてきました\n防御できるのは王だけです";
            else
                message = info.getNickname()[info.getParentId()] + "が攻めてきました";
            view.showMessage(message);
        }
        myFieldPiece.draw();
        handPiece.draw();
        guardOrPass.draw();
    }

    @Override
    protected void executeState() {
        //System.err.println("GuardOrPass");
        whichClicked = guardOrPass.whichClicked();
        switch (whichClicked) {
            case 1:
                cc.writeSingleMessage(whichClicked);
                break;
            case 2:
                cc.writeSingleMessage(whichClicked);
                info.updateMainPlayerId();
                irregular = (info.getMainPlayerId() == info.getParentId());
                break;
            default:
                break;
        }

    }

    @Override
    protected ClientState decideState() {
        switch (whichClicked) {
            case 1:
                return new PutGuardPiece();
            case 2:
                if(irregular)
                    return new BeginningOfGame();
                else
                    return new Waiting(info.getNickname()[info.getMainPlayerId()] + " が防御か流すか選択中");
            default:
                drawn = true;
                return this;
        }
    }
}
