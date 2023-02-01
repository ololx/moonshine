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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

/**
 * project moonshine
 * created 05.01.2023 20:47
 *
 * @author Alexander A. Kropotin
 */
public class QuintupleTest {

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(A t0, 
                                                                                      B t1, 
                                                                                      C t2, 
                                                                                      D t3,
                                                                                      E t4) {
        //When
        // create new tuple with specified args
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
        assertEquals(tuple.getT2(), t2);
        assertEquals(tuple.getT3(), t3);
        assertEquals(tuple.getT4(), t4);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void getT0_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index 0
        Object actual = tuple.getT0();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void getT1_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index 1
        Object actual = tuple.getT1();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void getT2_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index 2
        Object actual = tuple.getT2();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void getT3_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index 3
        Object actual = tuple.getT3();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t3);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void getT4_whenGet_thenReturnThisElementValue(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index 4
        Object actual = tuple.getT4();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t4);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void get_whenIndexExists_thenReturnValueByIndex(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by indexes 0, 1, 2, 3, 4
        Object actualT0 = tuple.get(0);
        Object actualT1 = tuple.get(1);
        Object actualT2 = tuple.get(2);
        Object actualT3 = tuple.get(3);
        Object actualT4 = tuple.get(4);

        //Then
        // actual values equal to stored values
        assertEquals(actualT0, t0);
        assertEquals(actualT1, t1);
        assertEquals(actualT2, t2);
        assertEquals(actualT3, t3);
        assertEquals(actualT4, t4);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class
    )
    <A, B, C, D, E> void get_whenIndexLessThanZero_thenThrowException(A t0,
                                                                      B t1,
                                                                      C t2,
                                                                      D t3,
                                                                      E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index < 0
        //Then
        // throw exception
        tuple.get(-1);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class
    )
    <A, B, C, D, E> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(A t0,
                                                                              B t1,
                                                                              C t2,
                                                                              D t3,
                                                                              E t4) {
        //Given
        // The tuple with size = 5
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(A t0,
                                                                                B t1,
                                                                                C t2,
                                                                                D t3,
                                                                                E t4) {
        //Given
        // The tuple with size = 5
        // and some default value
        final Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);
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
    <A, B, C, D, E> void size_whenCreateTuple_thenTupleHasSize(A t0, B t1, C t2, D t3, E t4) {
        //Given
        // The tuple with size = 5
        final Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 5;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0,
                                                                               B t1,
                                                                               C t2,
                                                                               D t3,
                                                                               E t4) {
        //Given
        // The tuple with size = 5
        final Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // create iterator
        final Iterator<Object> iterator = tuple.iterator();

        //Then
        // iterator is not null
        assertNotNull(iterator);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E> void toString_whenBuildString_thenStringContainsAllElements(A t0,
                                                                                B t1,
                                                                                C t2,
                                                                                D t3,
                                                                                E t4) {
        //Given
        // The tuple with args
        Quintuple<A, B, C, D, E> tuple = new Quintuple<>(t0, t1, t2, t3, t4);

        //When
        // build string representation for this tuple
        String tupleInString = tuple.toString();

        //Then
        // string representation contains all tuple values
        assertTrue(tupleInString.contains(String.valueOf(t0)));
        assertTrue(tupleInString.contains(String.valueOf(t1)));
        assertTrue(tupleInString.contains(String.valueOf(t2)));
        assertTrue(tupleInString.contains(String.valueOf(t3)));
        assertTrue(tupleInString.contains(String.valueOf(t4)));
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Quintuple.class)
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
                        Integer.MAX_VALUE,
                        Long.MAX_VALUE
                },
                {
                        Character.MIN_VALUE,
                        Short.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Character.MAX_VALUE,
                        Character.MAX_VALUE
                },
                {
                        Short.MIN_VALUE,
                        Integer.MAX_VALUE,
                        Float.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE
                },
                {
                        Integer.MIN_VALUE,
                        Float.MAX_VALUE,
                        Double.MAX_VALUE,
                        Double.MAX_VALUE,
                        Double.MAX_VALUE
                },
                {
                        Float.MIN_VALUE,
                        Double.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE
                },
                {
                        Double.MIN_VALUE,
                        Byte.MAX_VALUE,
                        Character.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Byte.MAX_VALUE
                },
                {
                        String.valueOf(Integer.MAX_VALUE),
                        Byte.MAX_VALUE,
                        Float.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Byte.MAX_VALUE
                }
        };
    }
}
