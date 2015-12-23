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
public class ContainsValidatorShould {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainsValidatorShould.class);

    @Test
    @Parameters(method = "arraysProvider")
    public void check_array_contains_anly_one_figure_between_1_to_9(int[] array, boolean result) {

        LOGGER.info("array = {}", Arrays.toString(array));
        ContainsValidator containsValidator = new ContainsValidator("test", 9);
        Arrays.stream(array).forEach(containsValidator::add);

        assertThat(containsValidator.nonValid()).isNotEqualTo(result);

    }

    public Object arraysProvider() {

        return new Object[][]{ //
                {new int[]{1}, false}, //
                {new int[]{1, 1}, false}, //
                {new int[]{1, 2}, false}, //
                {new int[]{1, 2, 3}, false}, //
                {new int[]{1, 2, 3, 4}, false}, //
                {new int[]{1, 2, 3, 4, 5}, false}, //
                {new int[]{1, 2, 3, 4, 5, 6}, false}, //
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8}, false}, //
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 1}, false}, //
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 2}, false}, //
                {new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, true}, //
                {new int[]{1, 9, 1, 9, 1, 9, 1, 9, 5}, false}, //
                {new int[]{5, 4, 5, 4, 5, 4, 5, 4, 5}, false}, //

                {new int[]{5, 9, 7, 2, 4, 6, 3, 1, 8}, true}, //
                {new int[]{2, 8, 4, 9, 1, 3, 6, 5, 7}, true}, //
                {new int[]{3, 6, 1, 7, 5, 8, 9, 4, 2}, true}, //
                {new int[]{7, 3, 9, 4, 2, 5, 8, 6, 1}, true}, //
                {new int[]{4, 5, 8, 6, 3, 1, 7, 2, 9}, true}, //
                {new int[]{6, 1, 2, 8, 7, 9, 5, 3, 4}, true}, //
                {new int[]{1, 7, 6, 5, 8, 4, 2, 9, 3}, true}, //
                {new int[]{9, 2, 3, 1, 6, 7, 4, 8, 5}, true}, //
                {new int[]{8, 4, 5, 3, 9, 2, 1, 7, 6}, true}  //
        };
    }

}
