package firstLab.server;

import firstLab.matrix.Matrices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SequentialServer {
    public static final int PORT = 5634;
    private static int number = 1;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                System.out.println("Клиент " + number + " подключен");
                try {
                    out = new ObjectOutputStream(socket.getOutputStream());
                    in = new ObjectInputStream(socket.getInputStream());
                    Matrices.forServerCalculation(in, out);
                    System.out.println("Результат " + number + " отправлен клиенту");
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
