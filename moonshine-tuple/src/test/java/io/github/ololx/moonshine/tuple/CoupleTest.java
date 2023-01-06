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

package io.github.ololx.moonshine.tuple;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * project moonshine
 * created 03.01.2023 12:43
 *
 * @author Alexander A. Kropotin
 */
public class CoupleTest {

    @DataProvider(name = "providesConstructorArgs")
    static Object[][] providesConstructorArgs() {
        return new Object[][] {
                {Byte.MIN_VALUE, Character.MAX_VALUE},
                {Character.MIN_VALUE, Short.MAX_VALUE},
                {Short.MIN_VALUE, Integer.MAX_VALUE},
                {Integer.MIN_VALUE, Float.MAX_VALUE},
                {Float.MIN_VALUE, Double.MAX_VALUE},
                {Double.MIN_VALUE, String.valueOf(Byte.MAX_VALUE)},
                {String.valueOf(Integer.MAX_VALUE), Byte.MAX_VALUE}
        };
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(final A t0,
                                                                             final B t1) {
        //When
        // create new tuple with specified args
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void getT0_whenGet_thenReturnThisElementValue(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get value by index 0
        final Object actual = tuple.getT0();

        //Then
        // actual value equals to stored value
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void getT1_whenGet_thenReturnThisElementValue(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get value by index 1
        final Object actual = tuple.getT1();

        //Then
        // actual value equals to stored value
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void get_whenIndexExists_thenReturnValueByIndex(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get values by indexes 0, 1
        final Object actualT0 = tuple.get(0);
        final Object actualT1 = tuple.get(1);

        //Then
        // actual values equal to stored values
        assertEquals(actualT0, t0);
        assertEquals(actualT1, t1);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "There is no elements by index.*"
    )
    <A, B> void get_whenIndexLessThanZero_thenThrowException(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get values by index < 0
        //Then
        // throw exception
        tuple.get(-1);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "There is no elements by index.*"
    )
    <A, B> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        // and some default value
        final Couple<A, B> tuple = new Couple<>(t0, t1);
        final String defaultValue = "default";

        //When
        // get values by index < 0 and index == tuple size
        final Object actual0 = tuple.getOrDefault(-1, defaultValue);
        final Object actual1 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual0, defaultValue);
        assertEquals(actual1, defaultValue);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void size_whenCreateTuple_thenTupleHasSize(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 2;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void iterator_whenCreateIterator_thenReturnNonNullIterator(final A t0, final B t1) {
        //Given
        // The tuple with size = 2
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // create iterator
        final Iterator<Object> iterator = tuple.iterator();

        //Then
        // size equal to expected
        assertNotNull(iterator);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Couple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
