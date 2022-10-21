import es.ulpgc.deserializers.MtxToCssDeserializer;
import es.ulpgc.matrices.CcsMatrix;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class DeserializerTest {
    @Test
    public void get_the_matrix() {
        MtxToCssDeserializer deserializer = new MtxToCssDeserializer();
        CcsMatrix matrix = deserializer.deserialize("mc2depi - copia.mtx");
        assertThat(matrix.value(0,0)).isEqualTo(5d);
        assertThat(matrix.value(3, 2)).isEqualTo(9d);
        assertThat(matrix.value(1, 1)).isEqualTo(8d);
        assertThat(matrix.value(2, 2)).isEqualTo(3d);
    }
}
