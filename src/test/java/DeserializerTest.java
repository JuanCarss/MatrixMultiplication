import es.ulpgc.Matrix;
import es.ulpgc.MatrixBuilder;
import es.ulpgc.MatrixDeserializer;
import es.ulpgc.builders.DenseMatrixBuilder;
import es.ulpgc.builders.SparseMatrixBuilder;
import es.ulpgc.deserializers.MtxToCssDeserializer;
import es.ulpgc.deserializers.MtxToSparseDeserializer;
import es.ulpgc.matrices.CcsMatrix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@RunWith(Parameterized.class)
public class DeserializerTest {

    private final MatrixDeserializer deserializer;

    public DeserializerTest(MatrixDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Test
    public void test_matrix_values() {
        Matrix matrix = deserializer.deserialize("mc2depi - copia.mtx");
        assertThat(matrix.value(0,0)).isEqualTo(5d);
        assertThat(matrix.value(3, 2)).isEqualTo(9d);
        assertThat(matrix.value(1, 1)).isEqualTo(8d);
        assertThat(matrix.value(2, 2)).isEqualTo(3d);
    }

    @Parameterized.Parameters
    public static Collection<MatrixDeserializer> implementations() {
        return List.of(
            new MtxToSparseDeserializer()
        );
    }
}
