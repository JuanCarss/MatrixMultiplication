import es.ulpgc.Matrix;
import es.ulpgc.Multiplication;
import es.ulpgc.matrices.DenseMatrix;
import es.ulpgc.multiplications.sequentials.DenseMatrixLoopInterchangeMultiplication;
import es.ulpgc.multiplications.sequentials.DenseMatrixStandardMultiplication;
import es.ulpgc.multiplications.sequentials.DenseMatrixTransposedMultiplication;
import es.ulpgc.multiplications.parallels.*;
import es.ulpgc.transposers.DenseMatrixTransposer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@RunWith(Parameterized.class)
public class DenseMatrixMultiplicationTest {

    private final int SIZE = 250;

    private final Multiplication multiplication;

    public DenseMatrixMultiplicationTest(Multiplication multiplication) {
        this.multiplication = multiplication;
    }

    @Test
    public void should_multiply_two_random_dense_matrices() {
        Matrix a = randomMatrix();
        Matrix b = randomMatrix();
        Matrix c = multiplication.execute(a,b);
        Vector vector = new Vector(SIZE);
        assertThat(vector.multiply(c)).as("testing..." + multiplication).isEqualTo(vector.multiply(b).multiply(a));
    }

    private Matrix randomMatrix() {
        Random random = new Random();
        double[][] values = new double[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values[i][j] = random.nextDouble();
            }
        }
        return new DenseMatrix(values);
    }

    @Parameterized.Parameters
    public static Collection<Multiplication> implementations() {
        return List.of(
            new DenseMatrixStandardMultiplication(),
            new DenseMatrixLoopInterchangeMultiplication(),
            new DenseMatrixTransposedMultiplication(new DenseMatrixTransposer()),
            new DenseMatrixParallelMultiplication(),
            new DenseMatrixParallelStreamMultiplication(),
            new DenseMatrixThreadPoolMultiplication(),
            new DenseMatrixParallelSynchronizedMultiplication(),
            new DenseMatrixSemaphoreMultiplication(),
            new DenseMatrixAtomicMultiplication()
        );
    }
}
