package es.ulpgc.compressors;

import es.ulpgc.Matrix;
import es.ulpgc.MatrixCompressor;
import es.ulpgc.matrices.CcsMatrix;

import java.util.ArrayList;
import java.util.List;

public class CcsCompressor implements MatrixCompressor {

    private final int size;
    private final List<Integer> rows = new ArrayList<>();
    private final int[] columnPointers;
    private final List<Double> compressedValues = new ArrayList<>();
    private final double[][] allMatrixValues;

    public CcsCompressor(Matrix matrix) {
        this.size = matrix.size();
        this.columnPointers = new int[size + 1];
        this.allMatrixValues = matrix.raw();
    }

    @Override
    public Matrix compress() {
        int colCurrentPointer = 0;
        for (int columnId = 0; columnId < size; columnId++) {
            for (int rowId = 0; rowId < size; rowId++) {
                if (allMatrixValues[rowId][columnId] == 0) continue;
                rows.add(rowId);
                compressedValues.add(allMatrixValues[rowId][columnId]);
                colCurrentPointer++;
            }
            columnPointers[columnId + 1] = colCurrentPointer;
        }
        return new CcsMatrix(
            rows.stream()
                .mapToInt(i->i)
                .toArray(),
            columnPointers,
            compressedValues.stream()
                .mapToDouble(i->i).toArray()
        );
    }
}
