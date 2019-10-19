package matrix;

import complex.Complex;
import element.ComplexElement;
import org.testng.annotations.Test;

public class MatrixTest {

    @Test
    public void testAdd() {

        int n = 200;
        double[][][] numbers = new double[n][n][2];
        for (int i = 0; i < n; i++) {
            numbers[i] = new double[n][2];
            for (int j = 0; j < n; j++) {
                numbers[i][j][0] = Math.random();
                numbers[i][j][1] = Math.random();
            }
        }

        Matrix<Complex> matrix = new Matrix<>(numbers, Complex.TYPE);
        matrix = matrix.multiply(matrix);

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
}