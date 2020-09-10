package matrix;

import complex.Complex;
import org.testng.annotations.Test;

import java.io.*;

public class MatrixSerializeTest {

    @Test
    public void serialize(){

        try (ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream("big_matrix.bin"))) {
            Matrix<Complex> aMatrix = new Matrix<>(new String[][]{{"1", "0"}, {"0","1"}}, Complex.TYPE);
            Matrices.serialize(fileWriter, aMatrix);
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
