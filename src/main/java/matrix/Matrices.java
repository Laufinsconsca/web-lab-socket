package matrix;

import client.Action;
import exceptions.IncompatibleDimensions;

import java.io.*;

public class Matrices {
    private Matrices() {
    }

    public static <T> Matrix<T> add(Matrix<T> f, Matrix<T> s) {
        Matrix<T> resultMatrix = new Matrix<>(f.getCountRows(), f.getCountColumns(), f.getType());
        if (s.getCountColumns() != f.getCountRows() || s.getCountRows() != f.getCountColumns())
            throw new IncompatibleDimensions();
        for (int i = 0; i < f.getCountRows(); i++) {
            for (int j = 0; j < f.getCountColumns(); j++) {
                resultMatrix.set(f.get(i + 1,j + 1).add(s.get(i + 1, j + 1)), i + 1, j + 1);
            }
        }
        return resultMatrix;
    }

    public static <T> Matrix<T> subtract(Matrix<T> f, Matrix<T> s) {
        Matrix<T> resultMatrix = new Matrix<>(f.getCountRows(), f.getCountColumns(), f.getType());
        if (s.getCountColumns() != f.getCountRows() || s.getCountRows() != f.getCountColumns())
            throw new IncompatibleDimensions();
        for (int i = 0; i < f.getCountRows(); i++) {
            for (int j = 0; j < f.getCountColumns(); j++) {
                resultMatrix.set(f.get(i + 1,j + 1).subtract(s.get(i + 1, j + 1)), i + 1, j + 1);
            }
        }
        return resultMatrix;
    }

    public static <T> Matrix<T> multiply(Matrix<T> f, Matrix<T> s) throws IncompatibleDimensions {
        Matrix<T> resultMatrix = new Matrix<>(f.getCountRows(), f.getCountColumns(), f.getType());
        for (int i = 0; i < f.getCountRows(); i++) {
            for (int j = 0; j < s.getCountColumns(); j++) {
                for (int k = 0; k < f.getCountRows(); k++) {
                    resultMatrix.set(resultMatrix.get(i + 1, j + 1).add(f.get(i + 1, k + 1).multiply(s.get(k + 1, j + 1))), i + 1, j + 1);
                }
            }
        }
        return resultMatrix;
    }

    public static <T> Matrix<T> multiply(Matrix<T> f, double multiplyOnThe) {
        Matrix<T> resultMatrix = new Matrix<>(f.getCountRows(), f.getCountColumns(), f.getType());
        for (int i = 0; i < f.getCountRows(); i++) {
            for (int j = 0; j < f.getCountColumns(); j++) {
                resultMatrix.set(f.get(i + 1, j + 1).multiply(multiplyOnThe), i + 1, j + 1);
            }
        }
        return resultMatrix;
    }

    public static void serialize(ObjectOutputStream outputStream, Matrix<?> matrix) {
        try {
            outputStream.writeObject(matrix.getType());
            outputStream.writeInt(matrix.getCountRows());
            outputStream.writeInt(matrix.getCountColumns());
            for (int i = 0; i < matrix.getCountRows(); i++) {
                for (int j = 0; j < matrix.getCountColumns(); j++) {
                    outputStream.writeObject(matrix.get(i + 1, j + 1));
                }
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix<?> deserialize(ObjectInputStream inputStream) {
        Matrix<?> matrix = null;
        try {
            Class<?> type = (Class<?>) inputStream.readObject();
            int rows = inputStream.readInt();
            int columns = inputStream.readInt();
            matrix = new Matrix<>(rows, columns, type);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix.set(inputStream.readObject(), i + 1, j + 1);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static void forServerCalculation(ObjectInputStream in, ObjectOutputStream out) {
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
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
