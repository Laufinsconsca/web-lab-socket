package matrix;

import complex.Complex;
import org.testng.annotations.Test;

import java.io.*;

public class MatrixSerializeTest {

    @Test
    public void serialize(){

        try (ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream("big_matrix.bin"))) {
            int n = 500;
            double[][][] numbers = new double[n][n][2];
            for (int i = 0; i < n; i++) {
                numbers[i] = new double[n][2];
                for (int j = 0; j < n; j++) {
                    numbers[i][j][0] = 0.5;
                    numbers[i][j][1] = 1;
                }
            }
            Matrix<Complex> matrix = new Matrix<>(numbers, Complex.TYPE);
            //Matrix<Complex> aMatrix = new Matrix<>(new String[][]{{"1", "0"}, {"0","1"}}, Complex.TYPE);
            Matrices.serialize(fileWriter, matrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserialize(){

        try (ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream("matrix4.bin"))) {
            Matrix<?> aMatrix = Matrices.deserialize(fileReader);
            System.out.println(aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
