package benchmarkings;

import es.ulpgc.Matrix;
import es.ulpgc.Multiplication;
import es.ulpgc.matrices.DenseMatrix;
import es.ulpgc.multiplications.DenseMatrixParallelStreamMultiplication;
import es.ulpgc.multiplications.DenseMatrixRowMultiplication;
import es.ulpgc.multiplications.DenseMatrixStandardMultiplication;
import es.ulpgc.multiplications.DenseMatrixTransposedMultiplication;
import es.ulpgc.transposers.DenseMatrixTransposer;
import org.openjdk.jmh.annotations.*;

import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
public class DenseMatrixMultiplicationBenchmark {
    private static final int SIZE = 20;
    private static final Random random = new Random();

    @Benchmark
    public static void DenseMatrixStandardMultiplication() {
        executeWith(new DenseMatrixStandardMultiplication());
    }

    @Benchmark
    public static void DenseMatrixRowMultiplication() {
        executeWith(new DenseMatrixRowMultiplication());
    }

    @Benchmark
    public static void DenseMatrixTransposedMultiplication() {
        executeWith(new DenseMatrixTransposedMultiplication(new DenseMatrixTransposer()));
    }

    @Benchmark
    public static void DenseMatrixParallelStreamMultiplication() {
        executeWith(new DenseMatrixParallelStreamMultiplication());
    }

    private static void executeWith(Multiplication multiplication) {
        multiplication.execute(denseRandomMatrix(), denseRandomMatrix());
    }

    private static Matrix denseRandomMatrix() {
        double[][] values = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values[i][j] = random.nextDouble();
            }
        }
        return new DenseMatrix(values);
    }

}
