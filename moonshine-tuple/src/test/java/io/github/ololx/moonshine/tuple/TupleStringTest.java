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
public class TupleStringTest {

    @DataProvider
    static Object[][] providesTuples() {
        return new Object[][]{
                {new EmptyTuple(), "()"},
                {new Monuple<>(1), "(1)"},
                {new Couple<>(1, 2), "(1, 2)"},
                {new Triple<>(1, 2, 3), "(1, 2, 3)"},
                {new Quadruple<>(1, 2, 3, 4), "(1, 2, 3, 4)"},
                {new Quintuple<>(1, 2, 3, 4, 5), "(1, 2, 3, 4, 5)"},
                {new Sextuple<>(1, 2, 3, 4, 5, 6), "(1, 2, 3, 4, 5, 6)"},
                {new Septuple<>(1, 2, 3, 4, 5, 6, 7), "(1, 2, 3, 4, 5, 6, 7)"},
                {new Octuple<>(1, 2, 3, 4, 5, 6, 7, 8), "(1, 2, 3, 4, 5, 6, 7, 8)"},
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