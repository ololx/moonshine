package io.github.ololx.moonshine.tuple;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * project moonshine
 * created 26.01.2023 09:39
 *
 * @author Alexander A. Kropotin
 */
public class IndexBoundsTest {

    @Test(dataProvider = "providesIndexesWithinUpperBounds")
    public void checkIndex_whenIndexWithinUpperBounds_thenReturnTrue(int index, int upperBound) {
        // Given
        // index and upper-bound, index within upper-bound

        //When
        // checkIndex
        boolean isIndexWithinBounds = IndexBounds.checkIndex(index, upperBound);

        //Then
        // index within upper-bound
        assertTrue(isIndexWithinBounds);
    }

    @Test(dataProvider = "providesIndexesOutOfUpperBounds")
    public void checkIndex_whenIndexOutOfUpperBounds_thenReturnFalse(int index, int upperBound) {
        // Given
        // index and upper-bound, index out of the upper-bound

        //When
        // checkIndex
        boolean isIndexWithinBounds = IndexBounds.checkIndex(index, upperBound);

        //Then
        // index ou of the upper-bound
        assertFalse(isIndexWithinBounds);
    }

    @Test(dataProvider = "providesIndexesWithinBoothBounds")
    public void checkIndex_whenIndexWithinBoothBounds_thenReturnTrue(int index,
                                                                     int lowerBound,
                                                                     int upperBound) {
        // Given
        // index and booth bounds, index within bounds

        //When
        // checkIndex
        boolean isIndexWithinBounds = IndexBounds.checkIndex(index, lowerBound, upperBound);

        //Then
        // index within bounds
        assertTrue(isIndexWithinBounds);
    }

    @Test(dataProvider = "providesIndexesOutOfBoothBounds")
    public void checkIndex_whenIndexOutOfBoothBounds_thenReturnFalse(int index,
                                                                     int lowerBound,
                                                                     int upperBound) {
        // Given
        // index and booth bounds, index out of a bounds

        //When
        // checkIndex
        boolean isIndexWithinBounds = IndexBounds.checkIndex(index, lowerBound, upperBound);

        //Then
        // index out of a bounds
        assertFalse(isIndexWithinBounds);
    }

    @Test(dataProvider = "providesIndexesWithinUpperBounds")
    public void requireIndexWithinBounds_whenIndexWithinUpperBounds_thenReturnTrue(int index,
                                                                                   int upperBound) {
        // Given
        // index and upper-bound, index within upper-bound

        //When
        // requireIndexWithinBounds
        int requiredIndex = IndexBounds.requireIndexWithinBounds(index, upperBound);

        //Then
        // required index equals to origin index
        assertEquals(requiredIndex, index);
    }

    @Test(
            dataProvider = "providesIndexesOutOfUpperBounds",
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "The index \\d+ out of a bounds \\[0, \\d+\\)"
    )
    public void requireIndexWithinBounds_whenIndexOutOfUpperBounds_thenThrowException(int index,
                                                                                      int upperBound) {
        // Given
        // index and upper-bound, index out of the upper-bound

        //When
        // requireIndexWithinBounds

        //Then
        // throw exception
        int requiredIndex = IndexBounds.requireIndexWithinBounds(index, upperBound);
    }

    @Test(dataProvider = "providesIndexesWithinBoothBounds")
    public void requireIndexWithinBounds_whenIndexWithinBoothBounds_thenReturnTrue(int index,
                                                                                   int lowerBound,
                                                                                   int upperBound) {
        // Given
        // index and upper-bound, index within upper-bound

        //When
        // requireIndexWithinBounds
        int requiredIndex = IndexBounds.requireIndexWithinBounds(index, upperBound);

        //Then
        // required index equals to origin index
        assertEquals(requiredIndex, index);
    }

    @Test(
            dataProvider = "providesIndexesOutOfBoothBounds",
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "The index \\d+ out of a bounds \\[\\d+, \\d+\\)"
    )
    public void requireIndexWithinBounds_whenIndexOutOfBoothBounds_thenThrowException(int index,
                                                                                      int lowerBound,
                                                                                      int upperBound) {
        // Given
        // index and booth bounds, index out of the bounds

        //When
        // requireIndexWithinBounds

        //Then
        // throw exception
        int requiredIndex = IndexBounds.requireIndexWithinBounds(index, lowerBound, upperBound);
    }

    @DataProvider
    static Object[][] providesIndexesWithinUpperBounds() {
        return new Object[][]{
                {0, 1},
                {1, 2},
                {0, 101},
                {1, 101},
                {100, 101}
        };
    }

    @DataProvider
    static Object[][] providesIndexesOutOfUpperBounds() {
        return new Object[][]{
                {0, 0},
                {1, 0},
                {1, 1},
                {100, 1},
                {100, 100},
                {101, 100}
        };
    }

    @DataProvider
    static Object[][] providesIndexesWithinBoothBounds() {
        return new Object[][]{
                {0, 0, 1},
                {1, 0, 2},
                {1, 0, 100},
                {100, 0, 101},
                {100, 100, 101}
        };
    }

    @DataProvider
    static Object[][] providesIndexesOutOfBoothBounds() {
        return new Object[][]{
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 1},
                {100, 100, 100},
                {100, 101, 101},
                {100, 101, 100}
        };
    }
}