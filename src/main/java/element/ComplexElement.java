package element;

import complex.Complex;

public class ComplexElement implements Element<Complex> {

    @Override
    public Element<Complex> add(Element<Complex> element) {
        return null;
    }

    @Override
    public Element<Complex> multiply(Element<Complex> element) {
        return null;
    }

    @Override
    public Element<Complex> multiply(double num) {
        return null;
    }

    @Override
    public Element<Complex> divide(Element<Complex> element) {
        return null;
    }

    @Override
    public Element<Complex> reciprocal() {
        return null;
    }

    @Override
    public boolean equals(Element<Complex> element) {
        return false;
    }

    @Override
    public int compareTo(Element<Complex> element) {
        return 0;
    }

    @Override
    public void set(Element<Complex> element) {

    }

    @Override
    public void set(Complex element) {

    }

    @Override
    public Complex get() {
        return null;
    }
}
