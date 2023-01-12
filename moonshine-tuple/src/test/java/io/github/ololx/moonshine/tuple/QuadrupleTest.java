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
 * created 05.01.2023 20:47
 *
 * @author Alexander A. Kropotin
 */
public class QuadrupleTest {

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(A t0,
                                                                                   B t1,
                                                                                   C t2,
                                                                                   D t3) {
        //When
        // create new tuple with specified args
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
        assertEquals(tuple.getT2(), t2);
        assertEquals(tuple.getT3(), t3);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void getT0_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // get values by index 0
        Object actual = tuple.getT0();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void getT1_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // get values by index 1
        Object actual = tuple.getT1();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void getT2_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // get values by index 2
        Object actual = tuple.getT2();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void get_whenIndexExists_thenReturnValueByIndex(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // get values by indexes 0, 1, 2, 3
        Object actualT0 = tuple.get(0);
        Object actualT1 = tuple.get(1);
        Object actualT2 = tuple.get(2);
        Object actualT3 = tuple.get(3);

        //Then
        // actual values equal to stored values
        assertEquals(actualT0, t0);
        assertEquals(actualT1, t1);
        assertEquals(actualT2, t2);
        assertEquals(actualT3, t3);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "There is no elements by index.*"
    )
    <A, B, C, D> void get_whenIndexLessThanZero_thenThrowException(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

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
    <A, B, C, D> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(A t0,
                                                                             B t1,
                                                                             C t2,
                                                                             D t3) {
        //Given
        // The tuple with size = 4
        // and some default value
        final Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);
        final String defaultValue = "default";

        //When
        // get values by index < 0 and index >= tuple size
        final Object actual0 = tuple.getOrDefault(-1, defaultValue);
        final Object actual1 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual0, defaultValue);
        assertEquals(actual1, defaultValue);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void size_whenCreateTuple_thenTupleHasSize(A t0, B t1, C t2, D t3) {
        //Given
        // The tuple with size = 4
        final Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 4;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0,
                                                                            B t1,
                                                                            C t2,
                                                                            D t3) {
        //Given
        // The tuple with size = 4
        final Quadruple<A, B, C, D> tuple = new Quadruple<>(t0, t1, t2, t3);

        //When
        // create iterator
        final Iterator<Object> iterator = tuple.iterator();

        //Then
        // size equal to expected
        assertNotNull(iterator);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Quadruple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @DataProvider(name = "providesConstructorArgs")
    static Object[][] providesConstructorArgs() {
        return new Object[][] {
                {
                        Byte.MIN_VALUE,
                        Character.MAX_VALUE,
                        Short.MAX_VALUE,
                        Integer.MAX_VALUE
                },
                {
                        Character.MIN_VALUE,
                        Short.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Character.MAX_VALUE
                },
                {
                        Short.MIN_VALUE,
                        Integer.MAX_VALUE,
                        Float.MAX_VALUE,
                        Integer.MAX_VALUE
                },
                {
                        Integer.MIN_VALUE,
                        Float.MAX_VALUE,
                        Double.MAX_VALUE,
                        Double.MAX_VALUE
                },
                {
                        Float.MIN_VALUE,
                        Double.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Integer.MAX_VALUE
                },
                {
                        Double.MIN_VALUE,
                        Byte.MAX_VALUE,
                        Character.MAX_VALUE,
                        Byte.MAX_VALUE
                },
                {
                        String.valueOf(Integer.MAX_VALUE),
                        Byte.MAX_VALUE,
                        Float.MAX_VALUE,
                        Byte.MAX_VALUE
                }
        };
    }
}