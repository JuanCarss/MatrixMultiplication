package es.ulpgc.deserializers;

import es.ulpgc.MatrixDeserializer;
import es.ulpgc.matrices.SparseMatrix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MtxToSparseDeserializer implements MatrixDeserializer {

    private final List<SparseMatrix.Coordinate> items = new ArrayList<>();
    private int size;

    @Override
    public SparseMatrix deserialize(String filename) {
        InputStream is = MtxToSparseDeserializer.getInputStream(filename);
        BufferedReader reader = MtxToSparseDeserializer.getBufferedReader(is);
        return readFile(reader);
    }

    private static BufferedReader getBufferedReader(InputStream is) {
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }

    private static InputStream getInputStream(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(filename);
    }

    private SparseMatrix readFile(BufferedReader reader) {
        size = reader.lines().filter(line -> !line.startsWith("%"))
            .map(this::getSize).findFirst().orElse(0);
        reader.lines().forEach(line -> saveValues(getLineValues(line)));
        return new SparseMatrix(items, size);
    }

    private int getSize(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray()[0];
    }

    private void saveValues(double[] lineValues) {
        this.items.add(new SparseMatrix.Coordinate((int) lineValues[0] - 1, (int) lineValues[1] - 1, lineValues[2]));
    }

    private double[] getLineValues(String line) {
        return Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }
}
