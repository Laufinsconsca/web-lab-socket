package matrix;

import element.Element;
import exceptions.IncompatibleDimensions;

import java.io.*;

public interface Matrix<T> extends Serializable {

    Matrix add(Matrix elements) throws IncompatibleDimensions;
    Matrix subtract(Matrix elements) throws IncompatibleDimensions;
    Matrix multilpy(Matrix elements) throws IncompatibleDimensions;
    Matrix multiply(double multiplyOnThe);
    Matrix det();
    Matrix[] getLU();
    Matrix gauss();

    int getCountRows();
    int getCountColumns();

    void set(Element<T> element, int row, int column);
    Element<T> get(int row, int column);

    Class getType();

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

    default String toStringView(){
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i <= getCountRows(); i++) {
            for (int j = 1; j <= getCountColumns(); j++) {
                builder.append(this.get(i, j).get()).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
