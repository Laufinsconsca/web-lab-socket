package matrix;

import element.ComplexElement;
import element.DoubleElement;
import element.Element;
import exceptions.IllegalTypeException;
import exceptions.IncompatibleDimensions;

import java.util.function.Function;

public class Matrix<T> implements Matrices<T> {
    Class<? extends Number> clazz;
    private int rows;
    private int columns;
    private Element[][] elements;

    public Matrix(int rows, int columns, Class<? extends Number> clazz) throws IllegalTypeException {
        this.rows = rows;
        this.columns = columns;
        this.clazz = clazz;
        elements = new Element[rows][columns];
        switch (clazz.getSimpleName()) {
            case "double", "Double" -> {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        elements[i][j] = new DoubleElement();
                    }
                }
            }
            case "Complex" -> {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        elements[i][j] = new ComplexElement();
                    }
                }
            }
            default -> throw new IllegalTypeException();
        }
    }

    private Matrix<T> calculate(Matrix<T> l, Function<Element<T>[], Element<T>> f) {
        Matrix<T> resultMatrix = new Matrix<>(this.rows, this.columns, clazz);
        if (l.getCountColumns() != columns || l.getCountRows() != rows)
            throw new IncompatibleDimensions();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix.set(f.apply(new Element[]{this.elements[i][j], l.get(i + 1, j + 1)}), i + 1, j + 1);
            }
        }
        return resultMatrix;
    }

    public Matrix add(Matrix<T> elements) throws IncompatibleDimensions {
        return calculate(elements, element -> element[0].add(element[1]));
    }

    @Override
    public Matrix<T> subtract(Matrix<T> elements) throws IncompatibleDimensions {
        return calculate(elements, element -> element[0].subtract(element[1]));
    }

    @Override
    public Matrix<T> multiply(Matrix<T> elements) throws IncompatibleDimensions {
        Matrix<T> resultMatrix = new Matrix<>(this.rows, this.columns, clazz);
        for (int i = 0; i < this.getCountRows(); i++) {
            for (int j = 0; j < elements.getCountColumns(); j++) {
                for (int k = 0; k < this.getCountRows(); k++) {
                    resultMatrix.set(resultMatrix.get(i + 1, j + 1).add(this.get(i + 1, k + 1).multiply(elements.get(k + 1, j + 1))), i + 1, j + 1);
                }
            }
        }
        return resultMatrix;
    }

    @Override
    public Matrix<T> multiply(double multiplyOnThe) {
        Matrix<T> resultMatrix = new Matrix<>(this.rows, this.columns, clazz);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix.set(this.elements[i][j].multiply(multiplyOnThe), i + 1, j + 1);
            }
        }
        return resultMatrix;
    }

    @Override
    public Matrix<T> det() {
        return null;
    }

    @Override
    public Matrix<T>[] getLU() {
        return null;
    }

    @Override
    public Matrix<T> gauss() {
        return null;
    }

    @Override
    public int getCountRows() {
        return rows;
    }

    @Override
    public int getCountColumns() {
        return columns;
    }

    public void set(Element<T> element, int row, int column) {
        elements[row - 1][column - 1] = element;
    }

    public Element<T> get(int row, int column) throws ArrayIndexOutOfBoundsException {
        return elements[row - 1][column - 1];
    }

    @Override
    public Matrix<T> clone() {
        return Matrices.super.clone();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= getCountRows(); i++) {
            for (int j = 1; j <= getCountColumns(); j++) {
                builder.append(this.get(i, j).get()).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

}
