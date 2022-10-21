package es.ulpgc.multiplications;

import es.ulpgc.Matrix;
import es.ulpgc.MatrixException;
import es.ulpgc.Multiplication;
import es.ulpgc.matrices.DenseMatrix;

public class DenseMatrixRowMultiplication implements Multiplication {
    @Override
    public Matrix execute(Matrix a, Matrix b) {
        checkIsDenseMatrix(a);
        checkIsDenseMatrix(b);
        int size = a.size();
        double[][] c = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int k = 0; k < size; k++)
                for (int j = 0; j < size; j++)
                    c[i][j] += a.value(i, k) * b.value(k, j);
        return new DenseMatrix(c);
    }

    private void checkIsDenseMatrix(Matrix matrix) {
        if (matrix instanceof DenseMatrix) return;
        throw new MatrixException("Supplied Matrix is of unsupported type");
    }
}
