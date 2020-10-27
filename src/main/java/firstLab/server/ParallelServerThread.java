package firstLab.server;

import firstLab.matrix.Matrices;

import java.io.*;
import java.net.Socket;

public class ParallelServerThread extends Thread {

    private Socket socket;
    private int number;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ParallelServerThread(Socket socket, int number) throws IOException {
        this.number = number;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run(){
        Matrices.forServerCalculation(in, out);
        System.out.println("Результат " + number + " отправлен клиенту");
    }
}