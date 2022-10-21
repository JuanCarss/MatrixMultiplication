package es.ulpgc.builders;

import es.ulpgc.Matrix;
import es.ulpgc.MatrixBuilder;
import es.ulpgc.matrices.SparseMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SparseMatrixBuilder implements MatrixBuilder {

    private final List<SparseMatrix.Coordinate> coordinates = new ArrayList<>();
    private final int size;

    public SparseMatrixBuilder(int size) {
        this.size = size;
    }

    @Override
    public Matrix build() {
        Collections.sort(coordinates);
        return new SparseMatrix(coordinates, size);
    }

    @Override
    public void set(int row, int col, double val) {
        coordinates.add(new SparseMatrix.Coordinate(row, col, val));
    }

    @Override
    public void set(Matrix matrix) {
        isEmpty();
        for (int row = 0; row < matrix.size(); row++) {
            for (int col = 0; col < matrix.size(); col++) {
                if (matrix.value(row, col) == 0d) continue;
                coordinates.add(new SparseMatrix.Coordinate(row, col, matrix.value(row, col)));
            }
        }
    }

    private void isEmpty() {
        if (coordinates.isEmpty()) return;
        throw new RuntimeException("There are already values in this builder");
    }
}
