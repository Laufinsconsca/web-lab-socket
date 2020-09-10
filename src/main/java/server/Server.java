package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 5634;
    private static volatile int number = 1;
    public static LinkedList<ServerThread> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                System.out.println("Клиент " + number + " подключен");
                try {
                    serverList.add(new ServerThread(socket, number));
                    number++;
                } catch (IOException e) {
                    System.out.println("Клиент " + number + " завершил работу в связи с ошибкой");
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }


}