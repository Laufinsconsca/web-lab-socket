package matrix;

import complex.Complex;
import element.ComplexElement;
import element.Element;
import exceptions.IllegalTypeException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertThrows;

public class MatrixTest {

    @Test
    public void test() {

        int n = 3;
        double[][][] numbers = new double[n][n][2];
        for (int i = 0; i < n; i++) {
            numbers[i] = new double[n][2];
            for (int j = 0; j < n; j++) {
                numbers[i][j][0] = 0.5;
                numbers[i][j][1] = 1;
            }
        }

        double[][][] result = multiplyDirectly(numbers, numbers);
        assertThrows(IllegalTypeException.class, () -> new Matrix<>(5, 5, String.class));
        Matrix<Complex> matrix = new Matrix<>(numbers, Complex.TYPE);
        matrix = matrix.multiply(matrix);
        System.out.println(matrix);

        Matrix<Complex> first = new Matrix<>(2, 2, Complex.TYPE);
        first.set(new int[]{1, 2}, 1, 1);
        first.set(new int[]{2, 3}, 2, 2);
        first.set(new double[]{1, -1}, 1, 2);
        first.set(new ComplexElement("2 + 3i"), 1, 2);
        Matrix<Complex> second = new Matrix<>(2, 2, Complex.TYPE);
        second.set(new double[]{1, 2}, 1, 1);
        second.set(new double[]{2, 3}, 2, 2);
        second.set(new double[]{1, -1}, 1, 2);
        second.set(new double[]{1, 0}, 1, 2);

        Matrix<Complex> third = new Matrix<>(new String[][]{{"1 + 2i"}, {"6 - 3i"}}, Complex.TYPE);

        System.out.println(first.toString());
        System.out.println(second.toString());
        System.out.println(third.toString());
        System.out.println(third.getClass().getSimpleName());
    }

    public double[][][] multiplyDirectly(double[][][]... arrays) {
        int countRows = arrays.length;
        int countColumns = arrays[0].length;
        double[][][] resultMatrix = new double[countRows][countColumns][2];
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                for (int k = 0; k < countRows; k++) {
                    double reA = arrays[i][k][0][0];
                    double imA = arrays[i][k][1][0];
                    double reB = arrays[k][j][0][1];
                    double imB = arrays[k][j][1][1];
                    resultMatrix[i][j][0] = reA * reB - imA * imB;
                    resultMatrix[i][j][1] = reB * imA + reA * imB;
                }
            }
        }
        return resultMatrix;
    }

    public double[][][] multiplyDirectlyWithTypeComplex(double[][][]... arrays) {
        int countRows = arrays.length;
        int countColumns = arrays[0].length;
        Element<Complex> a;
        Element<Complex> b;
        double[][][] resultMatrix = new double[countRows][countColumns][2];
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < countColumns; j++) {
                for (int k = 0; k < countRows; k++) {
                    a = new ComplexElement(arrays[i][k][0][0], arrays[i][k][1][0]);
                    b = new ComplexElement(arrays[k][j][0][1], arrays[k][j][1][1]);
                    a = a.multiply(b);
                    resultMatrix[i][j][0] = a.get().re();
                    resultMatrix[i][j][1] = a.get().im();
                }
            }
        }
        return resultMatrix;
    }
}