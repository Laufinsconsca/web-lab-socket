package element.factory;

import complex.Complex;
import element.ComplexElement;
import element.DoubleElement;
import element.Element;
import exceptions.IllegalTypeException;
import exceptions.InvalidArrayLength;

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
        switch (type.getSimpleName()) {
            case "double", "Double" -> {
                switch (arg.getClass().getSimpleName()) {
                    case "double", "Double", "int", "Integer", "long", "Long" -> {
                        return new DoubleElement((double) arg);
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
                    case "double[]", "Double[]", "int[]", "Integer[]", "long[]", "Long[]" -> {
                        return createInstanceOfArray(arg);
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

    private ComplexElement createInstanceOfArray(Object obj) {
        if (obj instanceof double[]) {
            return calculateComplexElement(Arrays.stream((double[]) obj).boxed().toArray(Double[]::new));
        } else if (obj instanceof Double[]) {
            return calculateComplexElement(obj);
        } else if (obj instanceof int[]) {
            return calculateComplexElement(Arrays.stream((int[]) obj).boxed().toArray(Integer[]::new));
        } else if (obj instanceof Integer[]) {
            return calculateComplexElement(obj);
        } else if (obj instanceof long[]) {
            return calculateComplexElement(Arrays.stream((long[]) obj).boxed().toArray(Long[]::new));
        } else if (obj instanceof Long[]) {
            return calculateComplexElement(obj);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> ComplexElement calculateComplexElement(Object arg) {
        if (((T[]) arg).length == 2) {
            return new ComplexElement(((Number) (((T[]) arg)[0])).doubleValue(), ((Number) (((T[]) arg)[1])).doubleValue());
        }
        throw new InvalidArrayLength();
    }
}
