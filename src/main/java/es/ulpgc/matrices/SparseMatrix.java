package es.ulpgc.matrices;

import es.ulpgc.Matrix;

import java.util.List;

public class SparseMatrix implements Matrix {

    private final List<Coordinate> values;
    private final int size;

    public SparseMatrix(List<Coordinate> values, int size) {
        this.values = values;
        this.size = size;
    }

    @Override
    public double value(int row, int col) {
        if (row > size() || col > size()) throw new RuntimeException("Exceeded the matrix length");
        return values.stream().filter(coordinate -> coordinate.checkEquals(row, col))
            .findFirst().stream().mapToDouble(coordinate -> coordinate.value).findFirst().orElse(0);
    }

    @Override
    public double[][] raw() {
        double[][] raw = new double[size][size];
        values.forEach(coordinate -> raw[coordinate.row][coordinate.col] = coordinate.value);
        return raw;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public double density() {
        return values.size() / Math.pow(size,2);
    }

    public static class Coordinate implements Comparable<Coordinate> {

        public final int row;
        public final int col;
        public final double value;

        public Coordinate(int row, int col, double value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        public boolean checkEquals(int newRow, int newCol) {
            return (newRow == row && newCol == col);
        }

        @Override
        public int compareTo(Coordinate o) {
            return this.row - o.row;
        }
    }
}
