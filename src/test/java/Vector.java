import es.ulpgc.Matrix;

import java.util.Arrays;
import java.util.Random;

public class Vector {
    private static final double EPSILON = 1E-5;
    private final double[] values;

    public Vector(int size) {
        this.values = randomVector(size);
    }

    private Vector(double[] values) {
        this.values = values;
    }

    private double[] randomVector(int size) {
        Random random = new Random();
        double[] result = new double[size];
        for (int i = 0; i < size; i++)
            result[i] = random.nextDouble();
        return result;
    }

    public Vector multiply(Matrix matrix) {
        double[] result = new double[values.length];
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < result.length; j++) {
                result[i] += matrix.value(i, j) * values[j];
            }
        }
        return new Vector(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        for (int i = 0; i < values.length; i++) {
            if (Math.abs(values[i] - vector.values[i]) > EPSILON) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}

