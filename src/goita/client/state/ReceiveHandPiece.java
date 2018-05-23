package goita.client.state;
import goita.Piece;
import goita.client.GUIClient;

class ReceiveHandPiece extends ClientState {
    private int nextState = 0;
    private int maxInfantry = -1;
    ReceiveHandPiece() {

    }

    @Override
    protected void drawState() {

    }

    @Override
    protected void executeState() {
        //System.err.println("ReceiveHandPiece");
        info.resetGameInfo();
        int[] handPiece = new int[Piece.FIELD_PIECE];
        String[] hp = cc.readMultiMessages(Piece.FIELD_PIECE);

        for(int i = 0; i < Piece.FIELD_PIECE; ++i) {
            handPiece[i] = Integer.parseInt(hp[i]);
        }
        info.setHandPiece(handPiece);

        nextState = Integer.parseInt(cc.readSingleMessage());
        if (nextState == 3) maxInfantry = Integer.parseInt(cc.readSingleMessage());

    }

    @Override
    protected ClientState decideState() {
        switch (nextState) {
            case 0:
                return new BeginningOfGame();
            case 1:
                return new TalkWithFriend();
            case 2:
                return new ReceiveHandPiece();
            case 3:
                return new CalcSpecialScore(maxInfantry);
            case 4:
                return new Waiting("五し成立\n続行か配り直しか決定中");
            default:
                return this;
        }
    }
}