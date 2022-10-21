package es.ulpgc.deserializers;

import es.ulpgc.MatrixDeserializer;
import es.ulpgc.matrices.CcsMatrix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MtxToCssDeserializer implements MatrixDeserializer {

    private final List<Integer> rows = new ArrayList<>();
    private int[] columnPointers;
    private final List<Double> values = new ArrayList<>();
    private int previousColumn = 0;

    @Override
    public CcsMatrix deserialize(String filename) {
        InputStream is = MtxToCssDeserializer.getInputStream(filename);
        BufferedReader reader = MtxToCssDeserializer.getBufferedReader(is);
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

    private CcsMatrix readFile(BufferedReader reader) {
        int size = reader.lines().filter(line -> !line.startsWith("%"))
            .map(this::getSize).findFirst().orElse(0);
        columnPointers = new int[size + 1];
        reader.lines().filter(line -> !line.startsWith("%")).skip(1)
            .forEach(line -> saveValues(getLineValues(line), previousColumn));
        fillLastPointers(previousColumn, size);
        return new CcsMatrix(
            rows.stream()
                .mapToInt(i->i)
                .toArray(),
            columnPointers,
            values.stream()
                .mapToDouble(i->i).toArray()
        );
    }

    private void fillLastPointers(int previousColumn, int size) {
        for (int i = previousColumn; i < size + 1; i++) {
            columnPointers[i + 1] = columnPointers[previousColumn];
        }
    }

    private int getSize(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray()[0];
    }

    private void saveValues(double[] lineValues, int previousColumn) {
        if (lineValues[1] != previousColumn) this.columnPointers[previousColumn] = this.values.size();
        this.rows.add((int) lineValues[0] - 1);
        this.values.add(lineValues[2]);
        this.previousColumn = (int) lineValues[1];
    }

    private double[] getLineValues(String line) {
        return Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }
}
