/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <br/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.tuple;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * project moonshine
 * created 04.01.2023 09:34
 *
 * @author Alexander A. Kropotin
 */
public class BaseIteratorTest {

    @Test(dataProvider = "providesTuples")
    void hasNext_whenIterationHasMoreValues_thenReturnTrue(Tuple tuple) {
        //Given
        // tuple iterator
        Iterator<Object> iterator = tuple.iterator();

        //When
        // iterate values
        int iteration = 0;
        while (iterator.hasNext()) {
            iteration++;
            iterator.next();
        }

        //Then
        // iterate exactly tuple.size times
        assertEquals(iteration, tuple.size());
    }

    @Test(dataProvider = "providesTuples")
    void next_whenIterationHasMoreValues_thenReturnValueAndIncrementIteration(Tuple tuple) {
        //Given
        // tuple iterator
        Iterator<Object> iterator = tuple.iterator();

        //When
        // get next value tuple.size times
        for (int iteration = 0; iteration < tuple.size(); iteration++) {
            iterator.next();
        }

        //Then
        // iteration hasn't more values
        assertFalse(iterator.hasNext());
    }

    @Test(dataProvider = "providesTuples")
    void forEachRemaining_whenIterationHasValues_thenAddToListEachRemaining(Tuple tuple) {
        //Given
        // tuple iterator
        // and empty list
        Iterator<Object> iterator = tuple.iterator();
        List<Object> iterationValues = new ArrayList<>();

        //When
        // forEachRemaining add value to list
        iterator.forEachRemaining(iterationValues::add);

        //Then
        // all values from tuple were iterated and add to iterationValues list
        // with the same order
        assertEquals(tuple.size(), iterationValues.size());
    }

    @Test(
            dataProvider = "providesTuples",
            expectedExceptions = NoSuchElementException.class,
            expectedExceptionsMessageRegExp = "The iteration has no more elements"
    )
    void next_whenIterationDoesNotHasMoreValues_thenThrowException(Tuple tuple) {
        //Given
        // tuple iterator wih cursor on the last element
        Iterator<Object> iterator = tuple.iterator();

        for (int iteration = 0; iteration < tuple.size(); iteration++) {
            iterator.next();
        }

        assertFalse(iterator.hasNext());

        //When
        // get next value (which is not exists)
        //Then
        // throw exception
        iterator.next();
    }

    @Test(
            dataProvider = "providesTuples",
            expectedExceptions = UnsupportedOperationException.class,
            expectedExceptionsMessageRegExp = "remove"
    )
    void remove_whenTryToRemoveLastElementFromTuple_thenThrowException(Tuple tuple) {
        //Given
        // tuple iterator
        Iterator<Object> iterator = tuple.iterator();

        //When
        // remove last value from tuple
        //Then
        // throw exception
        iterator.remove();
    }

    @DataProvider(name = "providesTuples")
    static Object[][] providesTuples() {
        return new Object[][] {
                {new EmptyTuple()},
                {new Monuple<>(Byte.MIN_VALUE)},
                {new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)}
        };
    }
}
