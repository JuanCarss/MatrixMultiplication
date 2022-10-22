package benchmarkings;

import es.ulpgc.Multiplication;
import es.ulpgc.compressors.CcsCompressor;
import es.ulpgc.compressors.CrsCompressor;
import es.ulpgc.deserializers.MtxToSparseMatrixDeserializer;
import es.ulpgc.matrices.SparseMatrix;
import es.ulpgc.multiplications.CompressedSparseMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.AverageTime)
@Fork(value = 2)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 2)
public class MtxCompressedSparseMatrixMultiplicationBenchmark {

    @Benchmark
    public static void mtxCompressedSparseMatrixMultiplicationBenchmark() {executeWith(new CompressedSparseMatrixMultiplication());}

    private static void executeWith(Multiplication implementation) {
        SparseMatrix matrix = new MtxToSparseMatrixDeserializer().deserialize("mc2depi.mtx");
        implementation.execute(new CrsCompressor(matrix).compress(), new CcsCompressor(matrix).compress());
    }
}
