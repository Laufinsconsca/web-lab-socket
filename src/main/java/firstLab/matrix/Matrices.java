package firstLab.matrix;

import firstLab.client.Action;
import exceptions.IncompatibleDimensions;
import firstLab.client.Code;
import firstLab.complex.Complex;

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
        Matrix<T> resultMatrix = new Matrix<>(f.getCountRows(), s.getCountColumns(), f.getType());
        for (int i = 0; i < f.getCountRows(); i++) {
            for (int j = 0; j < s.getCountColumns(); j++) {
                for (int k = 0; k < f.getCountColumns(); k++) {
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

    public static void writeToFile(FileWriter fileWriter, Matrix<?> matrix) {
        try {
            fileWriter.write(matrix.getCountRows() + "\n");
            fileWriter.write(matrix.getCountColumns() + "\n");
            fileWriter.write(matrix.toString());
            fileWriter.flush();
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

    public static Matrix<?> readFromFile(FileReader fileReader) throws IllegalArgumentException{
        Matrix<?> matrix = null;
        StreamTokenizer streamTokenizer = new StreamTokenizer(fileReader);
        try {
            streamTokenizer.nextToken();
            int rows = (int)streamTokenizer.nval;
            streamTokenizer.nextToken();
            int columns = (int)streamTokenizer.nval;
            if (rows < 0 || columns < 0) {
                throw new IllegalArgumentException("Одна (или обе) размерности меньше нуля");
            }
            streamTokenizer.nextToken();
            matrix = new Matrix(rows, columns, Double.TYPE);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix.set(streamTokenizer.nval, i + 1, j + 1);
                    streamTokenizer.nextToken();
                }
            }
            //todo
            streamTokenizer.nextToken();
        } catch (IOException e) {
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
                case MUL -> {
                    if (firstMatrix.getCountColumns() == secondMatrix.getCountRows()) {
                        resultMatrix = Matrices.multiply(firstMatrix, secondMatrix);
                        out.writeObject(Code.VALID);
                    } else {
                        out.writeObject(Code.INCONSISTENT_DIMENSIONS);
                    }
                }
                case SUM -> {
                    if (firstMatrix.hasEqualDimensionsWith(secondMatrix)) {
                        resultMatrix = Matrices.add(firstMatrix, secondMatrix);
                        out.writeObject(Code.VALID);
                    } else {
                        out.writeObject(Code.INCONSISTENT_DIMENSIONS);
                    }
                }
            }
            Matrices.serialize(out, resultMatrix);
            out.flush();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
