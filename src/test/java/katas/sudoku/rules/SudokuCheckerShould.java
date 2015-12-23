package katas.sudoku.rules;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SudokuCheckerShould {

    private static final Logger LOGGER = LoggerFactory.getLogger(SudokuCheckerShould.class);

    @Test
    @Parameters(method = "sudokuGridProvider")
    public void check_a_sudoku_grid(int[][] sudokuGrid, boolean result) {
        Arrays.stream(sudokuGrid).forEach(this::showSudokuRow);
        SudokuChecker sudokuChecker = new SudokuChecker(sudokuGrid);
        assertThat(sudokuChecker.checkSudoku()).isEqualTo(result);
    }

    public Object sudokuGridProvider() {
        return new Object[][]{ //
                {new int[][]{ //
                        {5, 9, 7, 2, 4, 6, 3, 1, 8}, //
                        {2, 8, 4, 9, 1, 3, 6, 5, 7}, //
                        {3, 6, 1, 7, 5, 8, 9, 4, 2}, //
                        {7, 3, 9, 4, 2, 5, 8, 6, 1}, //
                        {4, 5, 8, 6, 3, 1, 7, 2, 9}, //
                        {6, 1, 2, 8, 7, 9, 5, 3, 4}, //
                        {1, 7, 6, 5, 8, 4, 2, 9, 3}, //
                        {9, 2, 3, 1, 6, 7, 4, 8, 5}, //
                        {8, 4, 5, 3, 9, 2, 1, 7, 6}}, true},
                {new int[][]{ //
                        {5, 9, 7, 2, 4, 6, 3, 1, 8}, //
                        {2, 8, 4, 9, 1, 3, 6, 5, 7}, //
                        {3, 6, 1, 7, 5, 8, 9, 4, 2}, //
                        {7, 3, 9, 4, 2, 5, 8, 6, 1}, //
                        {4, 5, 8, 6, 3, 1, 7, 2, 9}, //
                        {6, 1, 2, 8, 7, 9, 5, 3, 4}, //
                        {1, 7, 6, 5, 8, 4, 2, 9, 3}, //
                        {9, 2, 3, 1, 6, 7, 4, 8, 5}, //
                        {3, 4, 5, 3, 9, 2, 1, 7, 6}}, false},
                {new int[][]{ //
                        {5, 9, 4, 2, 4, 6, 3, 1, 8}, //
                        {2, 8, 4, 9, 1, 3, 6, 5, 7}, //
                        {3, 6, 1, 7, 5, 8, 9, 4, 2}, //
                        {7, 3, 9, 4, 2, 5, 8, 6, 1}, //
                        {4, 5, 8, 6, 3, 1, 7, 2, 9}, //
                        {6, 1, 2, 8, 7, 9, 5, 3, 4}, //
                        {1, 7, 6, 5, 8, 4, 2, 9, 3}, //
                        {9, 2, 3, 1, 6, 7, 4, 8, 5}, //
                        {8, 4, 5, 3, 9, 2, 1, 7, 6}}, false}
        };
    }

    private void showSudokuRow(int[] rows) {
        LOGGER.info("row = {}", Arrays.toString(rows));
    }

}
