import element.DoubleElement;
import matrix.Matrix;
import matrix.DoubleMatrix;

public class Main {
    public static void main(String[] args) {
        Matrix<Double> matrix = new DoubleMatrix(5,5);
        matrix.set(new DoubleElement(5), 4,3);
        System.out.println(matrix.toStringView());
    }
}
