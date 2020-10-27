package firstLab.matrix;

import firstLab.complex.Complex;
import org.testng.annotations.Test;

import java.io.*;

public class MatrixSerializeTest {

    @Test
    public void serialize(){

        try (ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream("bigMatrix.bin"))) {
            Matrix<Complex> aMatrix = new Matrix<>(new String[][]{{"-1", "-2-i"}, {"8","6i"}, {"3", "5"}}, Complex.TYPE);
            Matrices.serialize(fileWriter, aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserialize(){

        try (ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream("thirdMatrix.bin"))) {
            Matrix<?> aMatrix = Matrices.deserialize(fileReader);
            System.out.println(aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
