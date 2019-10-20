package matrix;

import complex.Complex;
import element.Element;
import element.factory.ElementFactory;
import exceptions.IllegalTypeException;
import exceptions.IncompatibleDimensions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Function;

public class Matrix<T> implements Tensor<T>, Serializable {
    private Class<?> type;
    private int rows;
    private int columns;
    private Element[][] elements;

    // ******************************************
    // TODO
    // private Collection<Collection<T>> elements;
    // ******************************************

    private ElementFactory factory;

    private Matrix() {
    }

    public Matrix(String[][] elements, Class<?> type) throws IllegalTypeException {
        isLegalType(type);
        this.rows = elements.length;
        this.columns = elements[0].length;
        this.type = type;
        factory = new ElementFactory(type);
        this.elements = new Element[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.elements[i][j] = parseStringToElement(elements[i][j]);
            }
        }
    }

    public Matrix(double[][] elements, Class<?> type) throws IllegalTypeException {
        this(new double[][][]{elements, new double[elements.length][elements[0].length]}, type);
    }

    public Matrix(double[][][] elements, Class<?> type) throws IllegalTypeException {
        isLegalType(type);
        this.rows = elements.length;
        this.columns = elements[0].length;
        this.type = type;
        factory = new ElementFactory(type);
        this.elements = new Element[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.elements[i][j] = factory.create(elements[i][j]);
            }
        }
    }

    public Matrix(int rows, int columns, Class<?> type) throws IllegalTypeException {
        isLegalType(type);
        this.rows = rows;
        this.columns = columns;
        this.type = type;
        factory = new ElementFactory(type);
        elements = new Element[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                elements[i][j] = factory.create(0);
            }
        }
    }

    private Matrix<T> calculate(Matrix<T> l, Function<Element<T>[], Element<T>> f) {
        Matrix<T> resultMatrix = new Matrix<>(this.rows, this.columns, type);
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
        Matrix<T> resultMatrix = new Matrix<>(this.rows, this.columns, type);
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
        Matrix<T> resultMatrix = new Matrix<>(this.rows, this.columns, type);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                resultMatrix.set(this.elements[i][j].multiply(multiplyOnThe), i + 1, j + 1);
            }
        }
        return resultMatrix;
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

    @Override
    public void set(Object element, int row, int column) {
        elements[row - 1][column - 1] = factory.create(element);
    }

    // ************************
    // for further optimization

    @Override
    public void setVectorInRow(Vector elements, int row) {

    }

    @Override
    public void setVectorInColumn(Vector elements, int column) {

    }

    // ************************

    public Element<T> get(int row, int column) throws ArrayIndexOutOfBoundsException {
        return elements[row - 1][column - 1];
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public Matrix<T> clone() {
        return Tensor.super.clone();
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

    private Element<?> parseStringToElement(String text) {
        double[] element = Complex.parseStringToComplex(text);
        if (element[1] != 0 && (type.equals(Double.TYPE) || type.equals(BigDecimal.class))) {
            throw new IllegalTypeException();
        }
        return factory.create(element);
    }

}
