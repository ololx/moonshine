/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ololx.moonshine.tuples;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * project moonshine
 * created 04.01.2023 09:34
 *
 * @author Alexander A. Kropotin
 */
public class BaseIteratorTest {

    @DataProvider(name = "providesTuples")
    static Object[][] providesTuples() {
        return new Object[][] {
                {new Monuple<>(Byte.MIN_VALUE)},
                {new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)}
        };
    }

    @Test(dataProvider = "providesTuples")
    void hasNext_whenIterationHasMoreValues_thenReturnTrue(final Tuple tuple) {
        //Given
        // tuple iterator
        final Iterator<Object> iterator = tuple.iterator();

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
    void next_whenIterationHasMoreValues_thenReturnValueAndIncrementIteration(final Tuple tuple) {
        //Given
        // tuple iterator
        final Iterator<Object> iterator = tuple.iterator();

        //When
        // get next value tuple.size times
        for (int iteration = 0; iteration < tuple.size(); iteration++) {
            iterator.next();
        }

        //Then
        // iteration hasn't more values
        assertFalse(iterator.hasNext());
    }
}
