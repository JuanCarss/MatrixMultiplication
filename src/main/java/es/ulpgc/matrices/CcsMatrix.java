package es.ulpgc.matrices;

import es.ulpgc.Matrix;

import java.util.stream.IntStream;

public class CcsMatrix implements Matrix {

    public final int[] rows;
    public final int[] colPointers;
    public final double[] values;

    public CcsMatrix(int[] rows, int[] colPointers, double[] values) {
        this.rows = rows;
        this.colPointers = colPointers;
        this.values = values;
    }

    @Override
    public double value(int row, int col) {
        return IntStream.range(colPointers[col], colPointers[col + 1])
                .filter(currentValueId -> rows[currentValueId] == row)
                .mapToDouble(currentValueId -> values[currentValueId]).findFirst().orElse(0d);
    }

    @Override
    public double[][] raw() {
        int size = size();
        double[][] matrix = new double[size][size];
        IntStream.range(0, size).forEach(colId -> fillCol(matrix, colId));
        return matrix;
    }

    private void fillCol(double[][] matrix, int colId) {
        IntStream.range(colPointers[colId], colPointers[colId + 1]).forEach(index ->
                matrix[rows[index]][colId] = values[index]);
    }

    @Override
    public int size() {
        return colPointers.length - 1;
    }

    @Override
    public double density() {
        return (double) values.length / Math.pow(colPointers.length - 1, 2);
    }
}