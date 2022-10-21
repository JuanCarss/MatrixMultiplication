package benchmarkings;

import es.ulpgc.Matrix;
import es.ulpgc.Multiplication;
import es.ulpgc.builders.SparseMatrixBuilder;
import es.ulpgc.compressors.CcsCompressor;
import es.ulpgc.compressors.CrsCompressor;
import es.ulpgc.multiplications.CompressedSparseMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
public class CompressedSparseMatrixMultiplicationBenchmark {
    private static final int SIZE = 1024;
    private static final Random random = new Random();

    public static void compressedSparseMatrixMultiplication() {executeWith(new CompressedSparseMatrixMultiplication());}

    private static void executeWith(Multiplication implementation) {
        implementation.execute(CrsRandomMatrix(), CcsRandomMatrix());
    }

    private static Matrix CcsRandomMatrix() {
        Random random = new Random();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(SIZE);
        for (int column = 0; column < SIZE; column++) {
            List<Integer> randomPositions = getRandomPositions(random);
            for (int row : randomPositions) {
                builder.set(row, column ,random.nextDouble());
            }
        }
        return new CcsCompressor(builder.build()).compress();
    }

    private static Matrix CrsRandomMatrix() {
        Random random = new Random();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(SIZE);
        for (int column = 0; column < SIZE; column++) {
            List<Integer> randomPositions = getRandomPositions(random);
            for (int row : randomPositions) {
                builder.set(row, column ,random.nextDouble());
            }
        }
        return new CrsCompressor(builder.build()).compress();
    }

    private static List<Integer> getRandomPositions(Random random) {
        List<Integer> result = new ArrayList<>();
        int amountToAdd = random.ints(0, SIZE / 2)
                .findFirst()
                .getAsInt();
        for (int i = 0; i < amountToAdd; i++) {
            result.add(random.ints(0, SIZE)
                    .findFirst()
                    .getAsInt());
        }
        return result;
    }
}
