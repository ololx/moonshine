package io.github.ololx.moonshine.tuple;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 29.01.2023 14:14
 *
 * @author Alexander A. Kropotin
 */
public class TupleStringTest_ {

    @DataProvider
    static Object[][] providesTuples() {
        return new Object[][]{
                {new EmptyTuple(), "()"},
                {Monuple.of(1), "(1)"},
                {Couple.of(1, 2), "(1, 2)"},
                {Triple.of(1, 2, 3), "(1, 2, 3)"},
                {Quadruple.of(1, 2, 3, 4), "(1, 2, 3, 4)"},
                {Quintuple.of(1, 2, 3, 4, 5), "(1, 2, 3, 4, 5)"},
                {Sextuple.of(1, 2, 3, 4, 5, 6), "(1, 2, 3, 4, 5, 6)"},
                {Septuple.of(1, 2, 3, 4, 5, 6, 7), "(1, 2, 3, 4, 5, 6, 7)"},
                {Octuple.of(1, 2, 3, 4, 5, 6, 7, 8), "(1, 2, 3, 4, 5, 6, 7, 8)"},
        };
    }

    @Test(dataProvider = "providesTuples")
    public void format_whenTupleHasValues_thenReturnStringWithValues(Tuple tuple, String expected) {
        // Given
        // specified tuple

        //When
        // format string from tuple
        String actual = TupleString.format(tuple);

        //Then
        // actual string equals expected
        assertEquals(expected, actual);
    }
}