package benchmarkings;

import es.ulpgc.Matrix;
import es.ulpgc.Multiplication;
import es.ulpgc.builders.SparseMatrixBuilder;
import es.ulpgc.matrices.SparseMatrix;
import es.ulpgc.multiplications.SparseMatrixStandardMultiplication;
import es.ulpgc.multiplications.SparseMatrixTransposedMultiplication;
import es.ulpgc.transposers.SparseMatrixTransposer;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
public class SparseMatrixMultiplicationBenchmark {
    private static final int SIZE = 20;
    private static final Random random = new Random();

    @Benchmark
    public static void sparseMatrixMultiplication() {
        executeWith(new SparseMatrixStandardMultiplication());
    }

    @Benchmark
    public static void sparseMatrixTransposedMultiplication() {executeWith(new SparseMatrixTransposedMultiplication(new SparseMatrixTransposer()));}

    private static void executeWith(Multiplication implementation) {
        implementation.execute(randomSparseMatrix(), randomSparseMatrix());
    }

    private static Matrix randomSparseMatrix() {
        Random random = new Random();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(SIZE);
        for (int i = 0; i < SIZE; i++) {
            List<Integer> positions = RandomPositions(random);
            for (Integer position : positions) builder.set(position, i, random.nextDouble());
        }
        return builder.build();
    }

    private static List<Integer> RandomPositions(Random random) {
        List<Integer> result = new ArrayList<>();
        int nonzeros = random.ints(0, SIZE / 2)
                .findFirst()
                .getAsInt();
        for (int i = 0; i < nonzeros; i++) {
            result.add(random.ints(0, SIZE)
                    .findFirst()
                    .getAsInt());
        }
        return result;
    }
}
