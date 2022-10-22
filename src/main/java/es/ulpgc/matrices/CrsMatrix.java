package es.ulpgc.matrices;

import es.ulpgc.Matrix;

import java.util.stream.IntStream;

public class CrsMatrix implements Matrix {

    public final int[] rowPointers;
    public final int[] columns;
    public final double[] values;

    public CrsMatrix(int[] rowPointers, int[] columns, double[] values) {
        this.rowPointers = rowPointers;
        this.columns = columns;
        this.values = values;
    }

    @Override
    public double value(int row, int col) {
        return IntStream.range(rowPointers[row], rowPointers[row + 1])
                .filter(currentValueId -> columns[currentValueId] == col)
                .mapToDouble(currentValueId -> values[currentValueId]).findFirst().orElse(0d);
    }

    @Override
    public double[][] raw() {
        int size = size();
        double[][] matrix = new double[size][size];
        IntStream.range(0, size).forEach(rowId -> fillRow(matrix, rowId));
        return matrix;
    }

    private void fillRow(double[][] matrix, int rowId) {
        IntStream.range(rowPointers[rowId], rowPointers[rowId + 1]).forEach(index ->
                matrix[rowId][columns[index]] = values[index]);
    }

    @Override
    public int size() {
        return rowPointers.length - 1;
    }

    @Override
    public double density() {
        return (double) values.length / Math.pow(rowPointers.length - 1, 2);
    }
}
