package katas.sudoku.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ContainsValidator {

    private final String valueName;
    private final int gridSize;

    private final int[] valuesCounter;

    public ContainsValidator(String valueName, int gridSize) {
        this.valuesCounter = new int[gridSize];
        this.valueName = valueName;
        this.gridSize = gridSize;
        IntStream.range(0, 9).forEach(valueIndex -> valuesCounter[valueIndex] = 0);
    }

    public void add(int value) {
        if (value >= 1 && value <= gridSize) {
            valuesCounter[value - 1]++;
        }
    }

    public boolean nonValid() {
        return Arrays.stream(valuesCounter).anyMatch(valueCounter -> valueCounter != 1);
    }

    @Override
    public String toString() {
        return "ContainsValidator{" +
                "valueName='" + valueName + '\'' +
                ", valuesCounter=" + Arrays.toString(valuesCounter) +
                '}';
    }
}
