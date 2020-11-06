package firstLab.client;

import firstLab.matrix.Matrices;
import firstLab.matrix.Matrix;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Socket clientSocket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {
        try {
            try {
                Code code = Code.VALID;
                clientSocket = new Socket("localhost", 5635);
                System.out.println("Соединение с сервером установлено\n");
                in = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                out = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

                String firstMatrixName = args[0];
                String secondMatrixName = args[1];
                String resultMatrixName = args[2];
                String action = args[3];

                Matrix<?> firstMatrix = null;
                Matrix<?> secondMatrix = null;

                try (FileReader firstMatrixReader = new FileReader(new File(firstMatrixName))) {
                    firstMatrix = Matrices.readFromFile(firstMatrixReader);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException iaex) {
                    code = Code.A_DIMENSION_LESS_THAN_ZERO;
                }

                try (FileReader secondMatrixReader = new FileReader(new File(secondMatrixName))) {
                    secondMatrix = Matrices.readFromFile(secondMatrixReader);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException iaex) {
                    code = Code.A_DIMENSION_LESS_THAN_ZERO;
                }

                if (code.equals(Code.VALID)) {
                    System.out.println("Первая матрица:");
                    if (firstMatrix.getCountRows() <= 10 && firstMatrix.getCountColumns() <= 10) {
                        System.out.println(firstMatrix);
                    } else {
                        System.out.println("Матрица велика для отображения");
                    }
                    System.out.println("Вторая матрица:");
                    if (secondMatrix.getCountRows() <= 10 && secondMatrix.getCountColumns() <= 10) {
                        System.out.println(secondMatrix);
                    } else {
                        System.out.println("Матрица велика для отображения");
                    }
                    System.out.println("Действие: " + action);

                    Matrices.serialize(out, firstMatrix);
                    Matrices.serialize(out, secondMatrix);
                    out.writeObject(Action.valueOf(action));
                    out.flush();
                    System.out.println("Идёт вычисление...");
                    code = (Code) in.readObject();
                }
                switch (code) {
                    case VALID -> {
                        Matrix resultMatrix = Matrices.deserialize(in);
                        System.out.println("Результат:");
                        if (resultMatrix.getCountRows() <= 10 && resultMatrix.getCountColumns() <= 10) {
                            System.out.println(resultMatrix);
                        } else {
                            System.out.println("Матрица велика для отображения");
                        }
                        try (FileWriter resultMatrixWriter = new FileWriter(new File(resultMatrixName))) {
                            Matrices.writeToFile(resultMatrixWriter, resultMatrix);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Готово, результат записан в " + resultMatrixName);
                    }
                    case INCONSISTENT_DIMENSIONS -> {
                        try (FileWriter errorReportWriter = new FileWriter(new File("error-report.txt"))) {
                            errorReportWriter.write("Размерности матриц не соответствуют: (" +
                                    firstMatrix.getCountRows() + "," + firstMatrix.getCountColumns() + ") и (" +
                                    secondMatrix.getCountRows() + "," + secondMatrix.getCountColumns() + ")");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Сервер вернул ошибку код " + code.ordinal() + " подробности в error-report.txt");
                    }
                    case A_DIMENSION_LESS_THAN_ZERO -> {
                        try (FileWriter errorReportWriter = new FileWriter(new File("error-report.txt"))) {
                            errorReportWriter.write("Одна (или обе) размерности меньше нуля");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Ошибка при считывании код " + code.ordinal() + " подробности в error-report.txt");
                    }
                }
                System.out.println("Соединение разорвано");
            } finally {
                System.out.println("Клиент был закрыт");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }

    }
}
