import es.ulpgc.Matrix;
import es.ulpgc.builders.SparseMatrixBuilder;
import es.ulpgc.compressors.CcsCompressor;
import es.ulpgc.compressors.CrsCompressor;
import es.ulpgc.multiplications.CompressedSparseMatrixMultiplication;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class CompressedSparseMatrixMultiplicationTest {
    private final int SIZE = 1024;
    @Test
    public void should_multiply_two_random_compressed_sparse_matrices() {
        Matrix a = randomCrsMatrix();
        Matrix b = randomCcsMatrix();
        CompressedSparseMatrixMultiplication multiplication = new CompressedSparseMatrixMultiplication();
        Matrix c = multiplication.execute(a, b);
        Vector vector = new Vector(SIZE);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(b).multiply(a));
    }

    private Matrix randomCcsMatrix() {
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

    private Matrix randomCrsMatrix() {
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

    private List<Integer> getRandomPositions(Random random) {
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
