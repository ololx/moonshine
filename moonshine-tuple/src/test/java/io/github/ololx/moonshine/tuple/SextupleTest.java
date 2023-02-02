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
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Stream;

import static java.util.Spliterator.*;
import static org.testng.Assert.*;

/**
 * project moonshine
 * created 18.01.2023 09:35
 *
 * @author Alexander A. Kropotin
 */
public class SextupleTest {

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(A t0, 
                                                                                         B t1, 
                                                                                         C t2, 
                                                                                         D t3, 
                                                                                         E t4,
                                                                                         F t5) {
        //When
        // create new tuple with specified args
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
        assertEquals(tuple.getT2(), t2);
        assertEquals(tuple.getT3(), t3);
        assertEquals(tuple.getT4(), t4);
        assertEquals(tuple.getT5(), t5);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getT0_whenGet_thenReturnThisElementValue(A t0,
                                                                     B t1,
                                                                     C t2,
                                                                     D t3,
                                                                     E t4,
                                                                     F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index 0
        Object actual = tuple.getT0();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getT1_whenGet_thenReturnThisElementValue(A t0,
                                                                     B t1,
                                                                     C t2,
                                                                     D t3,
                                                                     E t4,
                                                                     F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index 1
        Object actual = tuple.getT1();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getT2_whenGet_thenReturnThisElementValue(A t0,
                                                                     B t1,
                                                                     C t2,
                                                                     D t3,
                                                                     E t4,
                                                                     F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index 2
        Object actual = tuple.getT2();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getT3_whenGet_thenReturnThisElementValue(A t0,
                                                                     B t1,
                                                                     C t2,
                                                                     D t3,
                                                                     E t4,
                                                                     F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index 3
        Object actual = tuple.getT3();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t3);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getT4_whenGet_thenReturnThisElementValue(A t0,
                                                                     B t1,
                                                                     C t2,
                                                                     D t3,
                                                                     E t4,
                                                                     F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index 4
        Object actual = tuple.getT4();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t4);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getT5_whenGet_thenReturnThisElementValue(A t0,
                                                                     B t1,
                                                                     C t2,
                                                                     D t3,
                                                                     E t4,
                                                                     F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index 5
        Object actual = tuple.getT5();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t5);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void get_whenIndexExists_thenReturnValueByIndex(A t0,
                                                                       B t1,
                                                                       C t2,
                                                                       D t3,
                                                                       E t4,
                                                                       F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by indexes 0, 1, 2, 3, 4, 5
        Object actualT0 = tuple.get(0);
        Object actualT1 = tuple.get(1);
        Object actualT2 = tuple.get(2);
        Object actualT3 = tuple.get(3);
        Object actualT4 = tuple.get(4);
        Object actualT5 = tuple.get(5);

        //Then
        // actual values equal to stored values
        assertEquals(actualT0, t0);
        assertEquals(actualT1, t1);
        assertEquals(actualT2, t2);
        assertEquals(actualT3, t3);
        assertEquals(actualT4, t4);
        assertEquals(actualT5, t5);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class
    )
    <A, B, C, D, E, F> void get_whenIndexLessThanZero_thenThrowException(A t0,
                                                                         B t1,
                                                                          C t2,
                                                                          D t3,
                                                                          E t4,
                                                                         F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

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
    <A, B, C, D, E, F> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(A t0,
                                                                                 B t1,
                                                                                 C t2,
                                                                                 D t3,
                                                                                 E t4,
                                                                                 F t5) {
        //Given
        // The tuple with size = 6
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(A t0,
                                                                                   B t1,
                                                                                   C t2,
                                                                                   D t3,
                                                                                   E t4,
                                                                                   F t5) {
        //Given
        // The tuple with size = 6
        // and some default value
        final Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);
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
    <A, B, C, D, E, F> void size_whenCreateTuple_thenTupleHasSize(A t0,
                                                                  B t1,
                                                                  C t2,
                                                                  D t3,
                                                                  E t4,
                                                                  F t5) {
        //Given
        // The tuple with size = 6
        final Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 6;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void toArray_whenBuildArray_thenArrayContainsAllElements(A t0,
                                                                                B t1,
                                                                                C t2,
                                                                                D t3,
                                                                                E t4,
                                                                                F t5) {
        //Given
        // The tuple with args
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // build array from this tuple
        Object[] tupleInArray = tuple.toArray();

        //Then
        // array contains all tuple values
        assertEquals(tupleInArray[0], t0);
        assertEquals(tupleInArray[1], t1);
        assertEquals(tupleInArray[2], t2);
        assertEquals(tupleInArray[3], t3);
        assertEquals(tupleInArray[4], t4);
        assertEquals(tupleInArray[5], t5);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void toList_whenBuildList_thenListContainsAllElements(A t0,
                                                                             B t1,
                                                                             C t2,
                                                                             D t3,
                                                                             E t4,
                                                                             F t5) {
        //Given
        // The tuple with args
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // build list from this tuple
        List<Object> tupleInList = tuple.toList();

        //Then
        // list contains all tuple values
        assertEquals(tupleInList.get(0), t0);
        assertEquals(tupleInList.get(1), t1);
        assertEquals(tupleInList.get(2), t2);
        assertEquals(tupleInList.get(3), t3);
        assertEquals(tupleInList.get(4), t4);
        assertEquals(tupleInList.get(5), t5);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void toSet_whenBuildSet_thenSetContainsAllElements(A t0,
                                                                          B t1,
                                                                          C t2,
                                                                          D t3,
                                                                          E t4,
                                                                          F t5) {
        //Given
        // The tuple with args
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // build list from this tuple
        Set<Object> tupleInSet = tuple.toSet();

        //Then
        // list contains all tuple values
        assertTrue(tupleInSet.contains(t0));
        assertTrue(tupleInSet.contains(t1));
        assertTrue(tupleInSet.contains(t2));
        assertTrue(tupleInSet.contains(t3));
        assertTrue(tupleInSet.contains(t4));
        assertTrue(tupleInSet.contains(t5));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void toStream_whenBuildStream_thenStreamContainsAllElements(A t0,
                                                                                   B t1,
                                                                                   C t2,
                                                                                   D t3,
                                                                                   E t4,
                                                                                   F t5) {
        //Given
        // The tuple with args
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // build list from this tuple
        Stream<Object> tupleInStream = tuple.toStream();

        //Then
        // list contains all tuple values
        assertTrue(tupleInStream.anyMatch(tupleElement -> {
            return tupleElement.equals(t0)
                    || tupleElement.equals(t1)
                    || tupleElement.equals(t2)
                    || tupleElement.equals(t3)
                    || tupleElement.equals(t4)
                    || tupleElement.equals(t5);
        }));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void spliterator_whenCreateSpliterator_thenReturnNonNullIterator(A t0,
                                                                                        B t1,
                                                                                        C t2,
                                                                                        D t3,
                                                                                        E t4,
                                                                                        F t5) {
        //Given
        // The tuple with size = 7
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // create spliterator
        Spliterator<Object> spliterator = tuple.spliterator();

        //Then
        // spliterator is not null
        // and spliterator contains sized, immutable, and ordered in characteristics
        assertNotNull(spliterator);
        assertEquals(spliterator.characteristics() ^ SUBSIZED, (SIZED | IMMUTABLE | ORDERED));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0,
                                                                                  B t1,
                                                                                  C t2,
                                                                                  D t3,
                                                                                  E t4,
                                                                                  F t5) {
        //Given
        // The tuple with size = 6
        final Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

        //When
        // create iterator
        final Iterator<Object> iterator = tuple.iterator();

        //Then
        // iterator is not null
        assertNotNull(iterator);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C, D, E, F> void toString_whenBuildString_thenStringContainsAllElements(A t0,
                                                                                   B t1,
                                                                                   C t2,
                                                                                   D t3,
                                                                                   E t4,
                                                                                   F t5) {
        //Given
        // The tuple with args
        Sextuple<A, B, C, D, E, F> tuple = new Sextuple<>(t0, t1, t2, t3, t4, t5);

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
        assertTrue(tupleInString.contains(String.valueOf(t5)));
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Sextuple.class)
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
                        Long.MAX_VALUE,
                        Integer.MIN_VALUE
                },
                {
                        Character.MIN_VALUE,
                        Short.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Character.MAX_VALUE,
                        Character.MAX_VALUE,
                        Integer.MIN_VALUE
                },
                {
                        Short.MIN_VALUE,
                        Integer.MAX_VALUE,
                        Float.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MIN_VALUE
                },
                {
                        Integer.MIN_VALUE,
                        Float.MAX_VALUE,
                        Double.MAX_VALUE,
                        Double.MAX_VALUE,
                        Double.MAX_VALUE,
                        Integer.MIN_VALUE
                },
                {
                        Float.MIN_VALUE,
                        Double.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MAX_VALUE,
                        Integer.MIN_VALUE
                },
                {
                        Double.MIN_VALUE,
                        Byte.MAX_VALUE,
                        Character.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Integer.MIN_VALUE
                },
                {
                        String.valueOf(Integer.MAX_VALUE),
                        Byte.MAX_VALUE,
                        Float.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Byte.MAX_VALUE,
                        Integer.MIN_VALUE
                }
        };
    }
}
