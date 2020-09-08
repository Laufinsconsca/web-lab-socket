package matrix;

import complex.Complex;
import org.testng.annotations.Test;

import java.io.*;

public class MatrixSerializeTest {

    @Test
    public void serialize(){

        try (BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream("matrix.bin"))) {
            Matrix<Complex> aMatrix = new Matrix<>(new String[][]{{"1", "2"}, {"4 + 5i","6"}}, Complex.TYPE);
            Matrices.serialize(fileWriter, aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserialize(){

        try (BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream("matrix.bin"))) {
            Matrix<?> aMatrix = Matrices.deserialize(fileReader);
            System.out.println(aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
