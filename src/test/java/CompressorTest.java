import es.ulpgc.builders.SparseMatrixBuilder;
import es.ulpgc.compressors.CcsCompressor;
import es.ulpgc.compressors.CrsCompressor;
import es.ulpgc.matrices.CcsMatrix;
import es.ulpgc.matrices.CrsMatrix;
import es.ulpgc.matrices.DenseMatrix;
import es.ulpgc.matrices.SparseMatrix;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class CompressorTest {


    @Test
    public void test_CRS_arrays() {
        SparseMatrix matrix = getMatrixForCRS();
        int[] rowPointers = {0,1,2,2,3};
        int[] columns = {0,1,1};
        double[] values = {5d,8d,6d};
        CrsMatrix crsMatrix = (CrsMatrix) new CrsCompressor(matrix).compress();
        assertThat(rowPointers).isEqualTo(crsMatrix.rowPointers);
        assertThat(columns).isEqualTo(crsMatrix.columns);
        assertThat(values).isEqualTo(crsMatrix.values);
    }

    @Test
    public void test_CRS_values() {
        SparseMatrix matrix = getMatrixForCRS();
        CrsMatrix crsMatrix = (CrsMatrix) new CrsCompressor(matrix).compress();
        assertThat(5d).isEqualTo(crsMatrix.value(0,0));
        assertThat(8d).isEqualTo(crsMatrix.value(1,1));
        assertThat(6d).isEqualTo(crsMatrix.value(3,1));
    }

    @Test
    public void test_CCS_arrays() {
        SparseMatrix matrix = getMatrixForCCS();
        int[] rows = {0,1,3,2};
        int[] columnPointers = {0,1,3,4,4};
        double[] values = {5d,8d,6d,3d};
        CcsMatrix ccsMatrix = (CcsMatrix) new CcsCompressor(matrix).compress();
        assertThat(rows).isEqualTo(ccsMatrix.rows);
        assertThat(columnPointers).isEqualTo(ccsMatrix.colPointers);
        assertThat(values).isEqualTo(ccsMatrix.values);
    }

    @Test
    public void test_CCS_values() {
        SparseMatrix matrix = getMatrixForCCS();
        CcsMatrix ccsMatrix = (CcsMatrix) new CcsCompressor(matrix).compress();
        assertThat(8d).isEqualTo(ccsMatrix.value(1,1));
        assertThat(5d).isEqualTo(ccsMatrix.value(0,0));
        assertThat(6d).isEqualTo(ccsMatrix.value(3,1));
        assertThat(3d).isEqualTo(ccsMatrix.value(2,2));
        assertThat(0d).isEqualTo(ccsMatrix.value(3,3));
    }



    private SparseMatrix getMatrixForCCS() {
        SparseMatrixBuilder sparseMatrixBuilder = new SparseMatrixBuilder(4);
        sparseMatrixBuilder.set(0,0,5);
        sparseMatrixBuilder.set(1,1,8);
        sparseMatrixBuilder.set(2,2,3);
        sparseMatrixBuilder.set(3,1,6);
        return (SparseMatrix) sparseMatrixBuilder.build();
    }

    private SparseMatrix getMatrixForCRS() {
        SparseMatrixBuilder sparseMatrixBuilder = new SparseMatrixBuilder(4);
        sparseMatrixBuilder.set(0,0,5);
        sparseMatrixBuilder.set(1,1,8);
        sparseMatrixBuilder.set(3,1,6);
        return (SparseMatrix) sparseMatrixBuilder.build();
    }
}
