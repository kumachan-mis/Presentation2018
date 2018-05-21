package goita.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import goita.Piece;
import goita.StateMachine;

public class GoitaServer {
    private static int port = 8080;
    private static ServerSocket s;
    private static Socket socket;
    private static GoitaServerThread[] threads = new GoitaServerThread[StateMachine.PLAYER_NUM];

    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.err.println("通信するポート番号を指定してください");
            System.exit(1);
        }

        port = Integer.parseInt(args[0]);
        s = new ServerSocket(port);

        System.out.println("準備完了 : " + s);
        System.out.println("プレイヤーの接続待機中...");
        setFirstParent();
        for(int id = 0; id < StateMachine.PLAYER_NUM;) {
            socket = s.accept();
            if(socket != null) {
                threads[id] = new GoitaServerThread(socket, id);
                System.out.println("新しいプレイヤーが参加しました. " + (id + 1) + "/" + StateMachine.PLAYER_NUM);
                threads[id].start();

                id++;
            }
        }
        System.out.println("プレイヤーの募集を終了します.");

        try {
            for(int id = 0; id < StateMachine.PLAYER_NUM; ++id)
                threads[id].join();
        } catch(InterruptedException e) {
            System.err.println(e);
        } finally {
            System.out.println("ゲームを終了します.");
        }
        
        s.close();
    }

    private static void setFirstParent() {
        ThreadSynchro synchro = ThreadSynchro.getInstance();
        int parentId = (new Random()).nextInt(StateMachine.PLAYER_NUM);
        synchro.setParentId(parentId);
    }
}