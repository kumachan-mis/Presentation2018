package goita.server.state;

import goita.Piece;
import goita.server.GoitaServerThread;

class ReceiveHandPiece extends ServerState{
    private int nextState = 0;

    ReceiveHandPiece(GoitaServerThread thread) {
        super(thread);
    }

    @Override
    protected void executeState() {
        if(thread.threadId == synchro.getParentId()) Piece.getInstance().shuffle();
        synchro.synchro();

        int hp[] = new int[Piece.PIECE_KIND];
        for(int i = 0; i < Piece.PIECE_KIND; i++) hp[i] = 0;

        int index = thread.threadId;
        for(int i = 0; i < Piece.FIELD_PIECE; i++) {
            hp[Piece.getInstance().get(index)]++;
            index += PLAYER_NUM;
        }
        sc.writeMultiMessages(hp);


        int infantry = hp[Piece.PIECE_KIND - 1];
        synchro.setSpecialRule(infantry);
        synchro.synchro();
        nextState = synchro.getResultInt();
        if(nextState == 1 && infantry < Piece.PIECE_NUM[Piece.PIECE_KIND - 1]) nextState = 4;
        sc.writeSingleMessage(nextState);

        if(nextState == 3) {
            synchro.setMaxInfantry(infantry);
            synchro.synchro();
            sc.writeSingleMessage(synchro.getResultInt());
        }
    }

    @Override
    protected ServerState decideState() {
        switch (nextState) {
            case 0:
                return new BeginningOfGame(thread);
            case 1:
                return new TalkWithFriend(thread);
            case 2:
                return new ReceiveHandPiece(thread);
            case 3:
                return new CalcSpecialScore(thread);
            case 4:
                return new Waiting(thread);
            default:
                return this;
        }
    }
}
