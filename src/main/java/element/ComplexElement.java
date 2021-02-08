package element;

import complex.Complex;
import complex.ComplexBigDecimal;
import exceptions.IllegalTypeException;

public class ComplexElement implements Element<Complex> {

    private Complex element;

    public ComplexElement(Complex number) {
        element = number;
    }

    public ComplexElement(ComplexBigDecimal number) {
        element = new Complex(number.getReal().doubleValue(), number.getImag().doubleValue());
    }

    public ComplexElement(String text) {
        Double[] element = Complex.parseStringToComplex(text);
        this.element = new Complex(element[0], element[1]);
    }

    public ComplexElement(double real, double imag) {
        this(new Complex(real, imag));
    }

    @Override
    public Element<Complex> add(Element<Complex> element) {
        return new ComplexElement(this.element.plus(element.get()));
    }

    @Override
    public Element<Complex> multiply(Element<Complex> element) {
        return new ComplexElement(this.element.times(element.get()));
    }

    @Override
    public Element<Complex> multiply(double num) {
        return new ComplexElement(this.element.scale(num));
    }

    @Override
    public Element<Complex> divide(Element<Complex> element) {
        return new ComplexElement(this.element.times(element.get().reciprocal()));
    }

    @Override
    public Element<Complex> reciprocal() {
        return new ComplexElement(this.element.reciprocal());
    }

    @Override
    public boolean equals(Element<Complex> element) {
        return this.element.equals(element);
    }

    @Override
    public int compareTo(Element<Complex> element) {
        throw new IllegalTypeException("The type doesn't possess a operation of compare");
    }

    @Override
    public void set(Element<Complex> element) {
        this.element = element.get();
    }

    @Override
    public void set(Complex element) {
        this.element = element;
    }

    @Override
    public Complex get() {
        return element;
    }
}
