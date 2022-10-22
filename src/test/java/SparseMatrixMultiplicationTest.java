import es.ulpgc.Matrix;
import es.ulpgc.Multiplication;
import es.ulpgc.builders.SparseMatrixBuilder;
import es.ulpgc.multiplications.SparseMatrixStandardMultiplication;
import es.ulpgc.multiplications.SparseMatrixTransposedMultiplication;
import es.ulpgc.transposers.SparseMatrixTransposer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(Parameterized.class)
public class SparseMatrixMultiplicationTest {
    private final int SIZE = 80;
    private final Multiplication multiplication;

    public SparseMatrixMultiplicationTest(Multiplication multiplication) {
        this.multiplication = multiplication;
    }

    @Test
    public void should_multiply_two_random_sparse_matrices() {
        Matrix a = randomSparseMatrix();
        Matrix b = randomSparseMatrix();
        Matrix c = multiplication.execute(a, b);
        Vector vector = new Vector(SIZE);
        assertThat(vector.multiply(c)).isEqualTo(vector.multiply(b).multiply(a));
    }

    private Matrix randomSparseMatrix() {
        Random random = new Random();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(SIZE);
        for (int i = 0; i < SIZE; i++) {
            List<Integer> positions = RandomPositions(random);
            for (Integer position : positions) builder.set(position, i, random.nextDouble());
        }
        return builder.build();
    }

    private List<Integer> RandomPositions(Random random) {
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

    @Parameterized.Parameters
    public static Collection<Multiplication> implementation() {
        return List.of(
                new SparseMatrixStandardMultiplication(),
                new SparseMatrixTransposedMultiplication(new SparseMatrixTransposer())
        );
    }
}
