package goita.client;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import goita.client.state.GameInfo;
import processing.core.PApplet;
import goita.ReadWrite;

public class GoitaClient {
    private static ReadWrite cc;

    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("通信する　ポート番号 と ローカルホスト名　を指定してください");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        InetAddress addr;

        try {
            addr = InetAddress.getByName(args[1]);
            makeConnection(addr, port);
        } catch(UnknownHostException e) {
            System.err.println(e);
            System.exit(1);
        }
        noticeMyNickname();
        PApplet.main("goita.client.GUIClient");
    }

    private static void makeConnection(InetAddress addr, int port) {
        Socket socket;
        try {
            socket = new Socket(addr, port);
            cc = new ReadWrite(socket, "サーバとの接続が切れました");
        } catch(IOException e) {
            System.err.println(e);
        }
    }

    private static void noticeMyNickname() {
        String nickname = null;
        boolean correctInput = false;
        Scanner scan = new Scanner(System.in);

        while(!correctInput) {
            System.out.println("ニックネームを入力してください");
            nickname = scan.nextLine();
            System.out.println("ニックネームは " + nickname + " でいいですか?(y/n)");
            String confirm = scan.nextLine().toLowerCase();
            correctInput = confirm.equals("y") || confirm.equals("yes");
        }
        
        cc.writeSingleMessage(nickname);
        //S1: ニックネームを送信
        GameInfo.getInstance().setPlayerId(
                Integer.parseInt(
                        cc.readSingleMessage()
                )
        );
        //R1; idを受信
        System.out.println(cc.readSingleMessage());
        //R2: ニックネームの登録完了を受信
        System.out.println("他のプレイヤーの登録が完了するまでしばらくお待ちください");
        System.out.println(cc.readSingleMessage());
        //R3: 全員の登録完了を送信
    }

    static ReadWrite getReadWrite() {
        return cc;
    }
}