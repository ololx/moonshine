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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 05.01.2023 10:00
 *
 * @author Alexander A. Kropotin
 */
public class EmptyTupleTest {

    @Test
    void getOrDefault_whenIndexNotExists_thenReturnDefaultValue() {
        //Given
        // The tuple with size = 0
        // and some default value
        final EmptyTuple tuple = new EmptyTuple();
        final String defaultValue = "default";

        //When
        // get values by index < 0 and index >= tuple size
        final Object actual1 = tuple.getOrDefault(-1, defaultValue);
        final Object actual2 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual1, defaultValue);
        assertEquals(actual2, defaultValue);
    }

    @Test
    void size_whenCreateTuple_thenTupleHasSize() {
        //Given
        // The tuple with size = 0
        final EmptyTuple tuple = new EmptyTuple();

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 0;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Monuple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
