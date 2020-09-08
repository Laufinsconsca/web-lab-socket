package matrix;

import exceptions.IncompatibleDimensions;

import java.io.*;

public class Matrices {
    private Matrices() {
    }

    private static <T> Matrix<T> add(Matrix<T> f, Matrix<T> s) {
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

    private static <T> Matrix<T> subtract(Matrix<T> f, Matrix<T> s) {
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

    public <T> Matrix<T> multiply(Matrix<T> f, Matrix<T> s) throws IncompatibleDimensions {
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

    public <T> Matrix<T> multiply(Matrix<T> f, double multiplyOnThe) {
        Matrix<T> resultMatrix = new Matrix<>(f.getCountRows(), f.getCountColumns(), f.getType());
        for (int i = 0; i < f.getCountRows(); i++) {
            for (int j = 0; j < f.getCountColumns(); j++) {
                resultMatrix.set(f.get(i + 1, j + 1).multiply(multiplyOnThe), i + 1, j + 1);
            }
        }
        return resultMatrix;
    }

    public static void serialize(BufferedOutputStream outputStream, Matrix<?> matrix) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(matrix.getType());
            objectOutputStream.writeInt(matrix.getCountRows());
            objectOutputStream.writeInt(matrix.getCountColumns());
            for (int i = 0; i < matrix.getCountRows(); i++) {
                for (int j = 0; j < matrix.getCountColumns(); j++) {
                    objectOutputStream.writeObject(matrix.get(i + 1, j + 1));
                }
            }
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Matrix<?> deserialize(BufferedInputStream inputStream) {
        Matrix<?> matrix = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Class<?> type = (Class<?>) objectInputStream.readObject();
            int rows = objectInputStream.readInt();
            int columns = objectInputStream.readInt();
            matrix = new Matrix<>(rows, columns, type);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix.set(objectInputStream.readObject(), i + 1, j + 1);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return matrix;
    }
}
