package katas.sudoku.rules;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SudokuChecker {

    private final int[][] sudokuGrid;
    private final int gridSize;
    private final int blockNumber;

    private ContainsValidator row;
    private ContainsValidator column;
    private ContainsValidator[] blocks;

    public SudokuChecker(int[][] sudokuGrid) {
        this.sudokuGrid = sudokuGrid;
        gridSize = sudokuGrid.length;
        blockNumber = gridSize / 3;
    }

    public boolean checkSudoku() {
        resetRowAndColumn();
        resetBlocks();

        return IntStream.range(0, gridSize).allMatch(rowIndex -> {

            IntStream.range(0, gridSize).forEach(columnIndex -> {
                row.add(sudokuGrid[rowIndex][columnIndex]);
                column.add(sudokuGrid[columnIndex][rowIndex]);
                blocks[rowIndex % blockNumber].add(sudokuGrid[rowIndex][columnIndex]);
            });

            if (isEndOfBlock(rowIndex)) {
                if (oneBlockNonValid()) {
                    return false;
                }
                resetBlocks();
            }
            if (row.nonValid() || column.nonValid()) {
                return false;
            }
            resetRowAndColumn();
            return true;
        });

    }

    private boolean oneBlockNonValid() {
        return Arrays.stream(blocks).anyMatch(ContainsValidator::nonValid);
    }

    private boolean isEndOfBlock(int rowIndex) {
        return (rowIndex + 1) % blockNumber == 0;
    }

    private void resetRowAndColumn() {
        row = new ContainsValidator("row", gridSize);
        column = new ContainsValidator("column", gridSize);
    }

    private void resetBlocks() {

        blocks = IntStream.rangeClosed(1, blockNumber) //
                .mapToObj(blockNumber -> new ContainsValidator("block #" + blockNumber, gridSize)) //
                .toArray(ContainsValidator[]::new);
    }
}
