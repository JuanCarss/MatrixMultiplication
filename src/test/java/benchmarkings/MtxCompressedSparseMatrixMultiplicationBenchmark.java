package benchmarkings;

import es.ulpgc.Multiplication;
import es.ulpgc.deserializers.MtxToCssMatrixDeserializer;
import es.ulpgc.matrices.CcsMatrix;
import es.ulpgc.multiplications.sequentials.CompressedSparseMatrixMultiplication;
import es.ulpgc.transposers.CcsMatrixTransposer;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
public class MtxCompressedSparseMatrixMultiplicationBenchmark {

    @Benchmark
    public static void mtxCompressedSparseMatrixMultiplicationBenchmark() {executeWith(new CompressedSparseMatrixMultiplication());}

    private static void executeWith(Multiplication implementation) {
        CcsMatrix matrix = new MtxToCssMatrixDeserializer().deserialize("mc2depi.mtx");
        implementation.execute(new CcsMatrixTransposer().execute(matrix), matrix);
    }
}
