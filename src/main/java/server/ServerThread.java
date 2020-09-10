package server;

import client.Action;
import matrix.Matrices;
import matrix.Matrix;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket socket; //сокет для общения
    private int number;
    private ObjectInputStream in; // поток чтения из сокета
    private ObjectOutputStream out; // поток записи в сокет

    public ServerThread(Socket socket, int number) throws IOException {
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