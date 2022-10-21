package es.ulpgc.transposers;

import es.ulpgc.Matrix;
import es.ulpgc.Transposer;
import es.ulpgc.builders.SparseMatrixBuilder;

public class SparseMatrixTransposer implements Transposer {
    @Override
    public Matrix execute(Matrix matrix) {
        int size = matrix.size();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(size);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (matrix.value(i, j) == 0) continue;
                builder.set(j, i, matrix.value(i, j));
            }
        return builder.build();
    }
}
