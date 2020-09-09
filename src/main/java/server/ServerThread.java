package server;

import client.Action;
import matrix.Matrices;
import matrix.Matrix;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private static Socket socket; //сокет для общения
    private static ObjectInputStream in; // поток чтения из сокета
    private static ObjectOutputStream out; // поток записи в сокет

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run(){
        try {
            Matrix firstMatrix = Matrices.deserialize(in);
            Matrix secondMatrix = Matrices.deserialize(in);
            Action action = (Action)in.readObject();
            Matrix resultMatrix = null;
            switch (action) {
                case MUL -> resultMatrix = Matrices.multiply(firstMatrix, secondMatrix);
                case SUM -> resultMatrix = Matrices.add(firstMatrix, secondMatrix);
            }
            Matrices.serialize(out, resultMatrix);
            out.flush(); // выталкиваем все из буфера
            System.out.println("Клиент завершил работу");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}