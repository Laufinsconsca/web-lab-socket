package firstLab.matrix;

import firstLab.complex.Complex;
import org.testng.annotations.Test;

import java.io.*;

public class MatrixSerializeTest {

    @Test
    public void serialize(){

        try (FileWriter fileWriter = new FileWriter(new File("thirdMatrix.txt"))) {
            Matrix<Complex> aMatrix = new Matrix<>(new String[][]{{"5", "4"}, {"1","3"}}, Double.TYPE);
            //System.out.println(aMatrix);
            Matrices.writeToFile(fileWriter, aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserialize(){

        try (FileReader fileReader = new FileReader(new File("matrix.txt"))) {
            Matrix<?> aMatrix = Matrices.readFromFile(fileReader);
            System.out.println(aMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
