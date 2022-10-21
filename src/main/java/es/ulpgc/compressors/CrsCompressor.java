package es.ulpgc.compressors;

import es.ulpgc.Matrix;
import es.ulpgc.MatrixCompressor;
import es.ulpgc.matrices.CrsMatrix;

import java.util.ArrayList;
import java.util.List;

public class CrsCompressor implements MatrixCompressor {

    private final int size;
    private final int[] rowPointers;
    private final List<Integer> columns = new ArrayList<>();
    private final List<Double> compressedValues = new ArrayList<>();
    private final double[][] allMatrixValues;

    public CrsCompressor(Matrix matrix) {
        size = matrix.size();
        rowPointers = new int[size + 1];
        allMatrixValues = matrix.raw();
    }

    @Override
    public Matrix compress() {
        int rowCurrentPointer = 0;
        for (int rowId = 0; rowId < size; rowId++) {
            for (int columnId = 0; columnId < size; columnId++) {
                if (allMatrixValues[rowId][columnId] == 0) continue;
                columns.add(columnId);
                compressedValues.add(allMatrixValues[rowId][columnId]);
                rowCurrentPointer++;
            }
            rowPointers[rowId + 1] = rowCurrentPointer
            ;
        }
        return new CrsMatrix(
            rowPointers,
            columns.stream()
                .mapToInt(i->i).toArray(),
            compressedValues.stream()
                .mapToDouble(i->i).toArray()
        );
    }
}
