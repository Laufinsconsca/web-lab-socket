package element.factory;

import complex.Complex;
import element.ComplexElement;
import element.DoubleElement;
import element.Element;
import exceptions.IllegalTypeException;

import java.util.Arrays;

public class ElementFactory {

    private Class type;

    public ElementFactory(Class type) {
        switch (type.getSimpleName()) {
            case "double", "Double", "Complex", "BigDecimal", "ComplexBigDecimal" -> this.type = type;
            default -> throw new IllegalTypeException();
        }
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Element<?> create(Object arg) {
        switch (arg.getClass().getSimpleName()) {
            case "double[]" -> arg = Arrays.stream((double[]) arg).boxed().toArray(Double[]::new);
            case "int[]" -> arg = Arrays.stream((int[]) arg).boxed().toArray(Integer[]::new);
            case "long[]" -> arg = Arrays.stream((long[]) arg).boxed().toArray(Long[]::new);
        }
        switch (type.getSimpleName()) {
            case "double", "Double" -> {
                switch (arg.getClass().getSimpleName()) {
                    case "Double[]", "Integer[]", "Long[]" -> {
                        if (((Number[]) arg).length == 2 && ((Number[]) arg)[1].doubleValue() == 0) {
                            return new DoubleElement(((Number[]) arg)[0].doubleValue());
                        }
                        return null;
                    }
                    case "double", "Double", "int", "Integer", "long", "Long" -> {
                        return new DoubleElement(((Number) arg).doubleValue());
                    }
                    case "BigDecimal" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    default -> throw new IllegalTypeException();
                }
            }
            case "Complex" -> {
                switch (arg.getClass().getSimpleName()) {
                    case "Complex" -> {
                        return new ComplexElement((Complex) arg);
                    }
                    case "double", "Double", "int", "Integer", "long", "Long" -> {
                        return new ComplexElement(((Number) arg).doubleValue(), 0);
                    }
                    case "Double[]", "Integer[]", "Long[]" -> {
                        if (((Number[]) arg).length == 2) {
                            return new ComplexElement(((Number[]) arg)[0].doubleValue(), ((Number[]) arg)[1].doubleValue());
                        }
                        return null;
                    }
                    case "ComplexBigDecimal" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    default -> throw new IllegalTypeException();
                }
            }
            case "BigDecimal" -> {
                switch (arg.getClass().getSimpleName()) {
                    case "BigDecimal" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    case "double", "Double", "int", "Integer", "long", "Long" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    default -> throw new IllegalTypeException();
                }
            }
            case "ComplexBigDecimal" -> {
                switch (arg.getClass().getSimpleName()) {
                    case "ComplexBigDecimal" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    case "double", "Double", "int", "Integer", "long", "Long" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    case "double[]", "Double[]", "int[]", "Integer[]", "long[]", "Long[]" -> {
                        // TODO
                        // createInstanceOfArray for ComplexBigDecimal
                        return null;
                    }
                    case "Complex" -> {
                        // TODO
                        // doSomething();
                        return null;
                    }
                    default -> throw new IllegalTypeException();
                }
            }
            default -> throw new IllegalTypeException();
        }
    }

    // ************************
    // for further optimization

    public Element<?>[] createVector(Object[] args) {
        return null;
    }

    public Element<?>[] createMatrix(Object[][] args) {
        return null;
    }

    // *************************

}
