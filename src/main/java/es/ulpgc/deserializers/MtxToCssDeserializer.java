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
    private final List<Double> values = new ArrayList<>();
    private int[] columnPointers;
    private int previousColumn = 0;
    private int size;

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
        size = reader.lines().filter(line -> !line.startsWith("%"))
            .map(this::getSize).findFirst().orElse(0);
        columnPointers = new int[size + 1];
        reader.lines().forEach(line -> saveValues(getLineValues(line), previousColumn));
        fillLastPointers(previousColumn);
        return new CcsMatrix(
            rows.stream()
                .mapToInt(i->i)
                .toArray(),
            columnPointers,
            values.stream()
                .mapToDouble(i->i).toArray()
        );
    }

    private void fillLastPointers(int previousColumn) {
        for (int i = previousColumn + 1; i < size + 1; i++) {
            columnPointers[i] = this.values.size();
        }
    }

    private int getSize(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray()[0];
    }

    private void saveValues(double[] lineValues, int previousColumn) {
        if ((lineValues[1] - 1) != previousColumn) this.columnPointers[(int) (lineValues[1] - 1)] = this.values.size();
        this.rows.add((int) lineValues[0] - 1);
        this.values.add(lineValues[2]);
        this.previousColumn = (int) lineValues[1] - 1;
    }

    private double[] getLineValues(String line) {
        return Arrays.stream(line.split(" ")).mapToDouble(Double::parseDouble).toArray();
    }
}
