package goita;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ReadWrite {
    private BufferedReader reader;
    private PrintWriter writer;
    private final String errorMessage;
    private static final String yes = "YES";
    private static final String no = "NO";
    private static final String end = "END";

    public ReadWrite(Socket socket, String errorMessage) {
        this.errorMessage = errorMessage;

        try {
            reader
            = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );

            writer
            = new PrintWriter(
                new BufferedWriter(
                    new OutputStreamWriter(
                        socket.getOutputStream()
                    )
                ), true
            );
        } catch(IOException e) {
            System.err.println(errorMessage);
        }
    }

    public String readSingleMessage() {
        String message = null;
        try {
            while(true) {
                message = reader.readLine();
                if(message != null) break;
            }
            return message;
         } catch(IOException e) {
            System.err.println(errorMessage);
            return "";
        }
    }

    public String[] readMultiMessages(int num) {
        String[] messages = new String[num];
        String dammy = null;

        int i = 0;
        try {
            while(true) {
                dammy = readSingleMessage();
                if(dammy.equals("")) throw new IOException();
                if(dammy.equals(end)) break;
                messages[i++] = dammy;
            }
            return messages;
        } catch(IOException e) {
            return new String[0];
        }
    }

    public boolean readYesOrNo() {
        return readSingleMessage().equals(yes);
    }
    
    public void writeSingleMessage(String message) {
        writer.println(message);
    }

    public void writeSingleMessage(int number) {
        writer.println(number);
    }

    public void writeMultiMessages(String[] messages) {
        for(int i = 0; i < messages.length; ++i) writer.println(messages[i]);
        writer.println(end);
    }

    public void writeMultiMessages(int[] numbers) {
        for(int i = 0; i < numbers.length; ++i) writer.println(numbers[i]);
        writer.println(end);
    }

    public void writeYesOrNo(boolean b) {
        if(b) {
            writer.println(yes);
        } else {
            writer.println(no);
        }
    }
}