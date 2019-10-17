package matrix;

import element.Element;
import exceptions.IncompatibleDimensions;

import java.io.*;

public interface Matrices<T> extends Serializable {

    Matrix<T> add(Matrix<T> elements) throws IncompatibleDimensions;

    Matrix<T> subtract(Matrix<T> elements) throws IncompatibleDimensions;

    Matrix<T> multiply(Matrix<T> elements) throws IncompatibleDimensions;

    Matrix<T> multiply(double multiplyOnThe);

    Matrix<T> det();

    Matrix<T>[] getLU();

    Matrix<T> gauss();

    int getCountRows();

    int getCountColumns();

    void set(Element<T> element, int row, int column);

    Element<T> get(int row, int column);

    default Matrix<T> clone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous;
            ous = new ObjectOutputStream(baos);
            ous.writeObject(this);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Matrix<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
