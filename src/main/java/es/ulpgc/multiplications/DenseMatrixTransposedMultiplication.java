package es.ulpgc.multiplications;

import es.ulpgc.Matrix;
import es.ulpgc.MatrixException;
import es.ulpgc.Multiplication;
import es.ulpgc.Transposer;
import es.ulpgc.matrices.DenseMatrix;

public class DenseMatrixTransposedMultiplication implements Multiplication {
    private final Transposer transposer;

    public DenseMatrixTransposedMultiplication(Transposer transposer) {
        this.transposer = transposer;
    }

    @Override
    public Matrix execute(Matrix a, Matrix b) {
        checkIsDenseMatrix(a);
        checkIsDenseMatrix(b);
        Matrix transposed = transposer.execute(b);
        int size = a.size();
        double[][] c = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size; k++)
                    c[i][j] += a.value(i, k) * transposed.value(j, k);
        return new DenseMatrix(c);
    }

    private void checkIsDenseMatrix(Matrix matrix) {
        if (matrix instanceof DenseMatrix) return;
        throw new MatrixException("Supplied Matrix is of unsupported type");
    }
}
