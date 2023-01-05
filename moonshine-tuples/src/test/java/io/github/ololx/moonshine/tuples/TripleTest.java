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

import java.util.Iterator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * project moonshine
 * created 05.01.2023 20:47
 *
 * @author Alexander A. Kropotin
 */
public class TripleTest {

    @DataProvider(name = "providesConstructorArgs")
    static Object[][] providesConstructorArgs() {
        return new Object[][] {
                {Byte.MIN_VALUE, Character.MAX_VALUE, Short.MAX_VALUE},
                {Character.MIN_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE},
                {Short.MIN_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE},
                {Integer.MIN_VALUE, Float.MAX_VALUE, Double.MAX_VALUE},
                {Float.MIN_VALUE, Double.MAX_VALUE, Byte.MAX_VALUE},
                {Double.MIN_VALUE, String.valueOf(Byte.MAX_VALUE), Character.MAX_VALUE},
                {String.valueOf(Integer.MAX_VALUE), Byte.MAX_VALUE, Integer.MAX_VALUE}
        };
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(final A t1, 
                                                                                final B t2,
                                                                                final C t3) {
        //When
        // create new tuple with specified args
        final Triple<A, B, C> tuple = new Triple<>(t1, t2, t3);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT1(), t1);
        assertEquals(tuple.getT2(), t2);
        assertEquals(tuple.getT3(), t3);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void get_whenIndexExists_thenReturnValueByIndex(final A t1, final B t2, final C t3) {
        //Given
        // The tuple with size = 2
        final Triple<A, B, C> tuple = new Triple<>(t1, t2, t3);

        //When
        // get values by indexes 0, 1
        final Object actualT1 = tuple.get(0);
        final Object actualT2 = tuple.get(1);
        final Object actualT3 = tuple.get(2);

        //Then
        // actual values equal to stored values
        assertEquals(actualT1, t1);
        assertEquals(actualT2, t2);
        assertEquals(actualT3, t3);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(final A t1,
                                                                       final B t2,
                                                                       final C t3) {
        //Given
        // The tuple with size = 2
        // and some default value
        final Triple<A, B, C> tuple = new Triple<>(t1, t2, t3);
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

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void size_whenCreateTuple_thenTupleHasSize(final A t1, final B t2, final C t3) {
        //Given
        // The tuple with size = 2
        final Triple<A, B, C> tuple = new Triple<>(t1, t2, t3);

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 3;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void iterator_whenCreateIterator_thenReturnNonNullIterator(final A t1,
                                                                         final B t2,
                                                                         final C t3) {
        //Given
        // The tuple with size = 2
        final Triple<A, B, C> tuple = new Triple<>(t1, t2, t3);

        //When
        // create iterator
        final Iterator<Object> iterator = tuple.iterator();

        //Then
        // size equal to expected
        assertNotNull(iterator);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Triple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
