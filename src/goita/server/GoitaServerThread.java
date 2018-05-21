package goita.server;
import java.net.Socket;
import goita.ReadWrite;
import goita.StateMachine;
import goita.server.state.Start;
import goita.server.state.ServerState;

public class GoitaServerThread extends Thread {
    public static final String[] nickname = new String[StateMachine.PLAYER_NUM];
    public final int threadId;
    private ReadWrite sc;
    private ServerState state;
    private boolean end = false;

    GoitaServerThread(Socket socket, int threadId) {
        this.threadId = threadId;
        sc = new ReadWrite(socket, "クライアントとの接続が切れました");
        state = new Start(this);
    }

    @Override
    public void run() {
        noticeMyNickname();
        while (!end) state = state.doState();
    }
    
    private void noticeMyNickname() {
        nickname[threadId] = sc.readSingleMessage();
        //R1: ニックネームを受信
        sc.writeSingleMessage(threadId);
        //S1: idを通知
        sc.writeSingleMessage(nickname[threadId] + "を登録しました");
        //S2: ニックネームの登録完了を送信
        ThreadSynchro.getInstance().synchro();
        sc.writeSingleMessage("プレイヤー全員を登録しました");
        //S3: 全員の登録完了を送信
    }

    public ReadWrite getReadWrite() {
        return sc;
    }

    public void endOfGame() {
        end = true;
    }
}