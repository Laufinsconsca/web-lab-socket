package matrix;

import element.DoubleElement;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MatrixTest {

    @Test
    public void testAdd() {
        Matrix<Double> first = new Matrix<>(2, 2, Double.TYPE);
        first.set(new DoubleElement(5), 1, 1);
        Matrix<Double> second = new Matrix<>(2, 2, Double.TYPE);
        System.out.println(first.add(second).toString());
    }
}