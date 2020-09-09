package client;

import complex.Complex;
import matrix.Matrices;
import matrix.Matrix;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 5634);
                System.out.println("Подключен");
                in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                String firstMatrixName = args[0];
                String secondMatrixName = args[1];
                String resultMatrixName = args[2];
                String action = args[3];

                Matrix<?> firstMatrix = null;
                Matrix<?> secondMatrix = null;

                try (ObjectInputStream firstMatrixReader = new ObjectInputStream(new FileInputStream(firstMatrixName))) {
                    firstMatrix = Matrices.deserialize(firstMatrixReader);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (ObjectInputStream secondMatrixReader = new ObjectInputStream(new FileInputStream(secondMatrixName))) {
                    secondMatrix = Matrices.deserialize(secondMatrixReader);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Matrices.serialize(out, firstMatrix);
                Matrices.serialize(out, secondMatrix);
                out.writeObject(Action.valueOf(action));
                out.flush();
                Matrix resultMatrix = Matrices.deserialize(in);
                System.out.println(resultMatrix);
                try (ObjectOutputStream resultMatrixWriter = new ObjectOutputStream(new FileOutputStream(resultMatrixName))) {
                    Matrices.serialize(resultMatrixWriter, resultMatrix);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Готово"); // получив - выводим на экран
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
