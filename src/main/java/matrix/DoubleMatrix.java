package matrix;

import element.DoubleElement;
import element.Element;
import exceptions.IncompatibleDimensions;

public class DoubleMatrix implements Matrix<Double> {
    private int rows;
    private int columns;
    Element<Double>[][] elements;

    public DoubleMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        elements = new Element[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                elements[i][j] = new DoubleElement();
            }
        }
    }

    @Override
    public Matrix add(Matrix elements) throws IncompatibleDimensions {
        Matrix resultMatrix = new DoubleMatrix(this.rows, this.columns);
        if (elements.getCountColumns() != columns || elements.getCountRows() != rows) throw new IncompatibleDimensions();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.elements[i][j] = this.elements[i][j].add(elements.get(i, j));
            }
        }
        return this;
    }

    @Override
    public Matrix subtract(Matrix elements) throws IncompatibleDimensions {
        Matrix resultMatrix = new DoubleMatrix(this.rows, this.columns);
        if (elements.getCountColumns() != columns || elements.getCountRows() != rows) throw new IncompatibleDimensions();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.elements[i][j] = this.elements[i][j].subtract(elements.get(i, j));
            }
        }
        return this;
    }

    @Override
    public Matrix multilpy(Matrix elements) throws IncompatibleDimensions {
        Matrix resultMatrix = new DoubleMatrix(this.rows, this.columns);
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
    public Matrix multiply(double multiplyOnThe) {
        Matrix resultMatrix = new DoubleMatrix(this.rows, this.columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.elements[i][j] = this.elements[i][j].multiply(multiplyOnThe);
            }
        }
        return this;
    }

    @Override
    public Matrix det() {
        return null;
    }

    @Override
    public Matrix[] getLU() {
        return new Matrix[0];
    }

    @Override
    public Matrix gauss() {
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

    public void set(Element<Double> element, int row, int column) {
        elements[row - 1][column - 1] = element;
    }

    @Override
    public Element<Double> get(int row, int column) throws ArrayIndexOutOfBoundsException{
        return elements[row - 1][column - 1];
    }

    @Override
    public Class getType() {
        return Double.TYPE;
    }

    @Override
    public Matrix<Double> clone(){
        return Matrix.super.clone();
    }

}
