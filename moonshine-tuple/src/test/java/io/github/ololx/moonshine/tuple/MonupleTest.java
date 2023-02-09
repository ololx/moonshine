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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Spliterator.*;
import static org.testng.Assert.*;

/**
 * project moonshine
 * created 03.01.2023 12:43
 *
 * @author Alexander A. Kropotin
 */
public class MonupleTest {

    @Test(dataProvider = "providesConstructorArgs")
    <A> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(A t0) {
        //When
        // create new tuple with specified args
        Monuple<A> tuple = new Monuple<>(t0);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void getT0_whenGet_thenReturnThisElementValue(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // get value by index 0
        Object actual = tuple.getT0();

        //Then
        // actual value equals to stored value
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void get_whenIndexExists_thenReturnValueByIndex(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // get value by index 0
        Object actual = tuple.get(0);

        //Then
        // actual value equals to stored value
        assertEquals(actual, t0);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class
    )
    <A> void get_whenIndexLessThanZero_thenThrowException(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

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
    <A> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(A t0) {
        //Given
        // The tuple with size = 1
        // and some default value
        Monuple<A> tuple = new Monuple<>(t0);
        String defaultValue = "default";

        //When
        // get values by index < 0 and index >= tuple size
        Object actual0 = tuple.getOrDefault(-1, defaultValue);
        Object actual1 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual0, defaultValue);
        assertEquals(actual1, defaultValue);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void contains_whenTupleContainsValue_thenReturnTrue(A t0) {
        //Given
        // The tuple with values
        final Monuple<A> tuple = new Monuple<>(t0);

        //When
        // check that tuple contains construct args
        final Set<Boolean> allContainsResults = Stream.of(t0)
                .map(tuple::contains)
                .collect(Collectors.toSet());

        //Then
        // no one check return false
        assertFalse(allContainsResults.contains(false));
    }

    @Test
    void contains_whenTupleContainsNull_thenReturnTrue() {
        //Given
        // The tuple with values
        final Monuple<Object> tuple = new Monuple<>(null);

        //When
        // check that tuple contains construct args
        final boolean containsNull = tuple.contains(null);

        //Then
        // no one check return true
        assertTrue(containsNull);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void contains_whenTupleDoNotContainsValue_thenReturnTrue(A t0) {
        //Given
        // The tuple with values
        final Monuple<A> tuple = new Monuple<>(t0);

        //When
        // check that tuple contains some value,
        // not from this tuple
        final Set<Boolean> allContainsResults = Stream.of("wrong value")
                .map(tuple::contains)
                .collect(Collectors.toSet());

        //Then
        // no one check return true
        assertFalse(allContainsResults.contains(true));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void contains_whenTupleDoNotContainsNull_thenReturnTrue(A t0) {
        //Given
        // The tuple with values
        final Monuple<A> tuple = new Monuple<>(t0);

        //When
        // check that tuple contains null value,
        // not from this tuple
        final boolean containsNull = tuple.contains(null);

        //Then
        // check return false
        assertFalse(containsNull);
    }

    @Test(dataProvider = "providesConstructorArgsAndIndexes")
    <A> void indexOf_whenTupleContainsValue_thenReturnTheirIndex(A t0,
                                                                 A someValue,
                                                                 int expectedIndex) {
        //Given
        // The tuple with values
        final Monuple<A> tuple = new Monuple<>(t0);

        //When
        // get index of some value
        final int actualIndex = tuple.indexOf(someValue);

        //Then
        // actual index equals expected index
        assertEquals(actualIndex, expectedIndex);
    }

    @Test(dataProvider = "providesConstructorArgsAndLastIndexes")
    <A> void lastIndexOf_whenTupleContainsValue_thenReturnTheirIndex(A t0,
                                                                     A someValue,
                                                                     int expectedIndex) {
        //Given
        // The tuple with values
        final Monuple<A> tuple = new Monuple<>(t0);

        //When
        // get last index of some value
        final int actualIndex = tuple.lastIndexOf(someValue);

        //Then
        // actual index equals expected index
        assertEquals(actualIndex, expectedIndex);
    }


    @Test(dataProvider = "providesConstructorArgs")
    <A> void size_whenCreateTuple_thenTupleHasSize(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // get tuple size
        int actual = tuple.size();
        int expected = 1;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void toArray_whenBuildArray_thenArrayContainsAllElements(A t0) {
        //Given
        // The tuple with args
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // build array from this tuple
        Object[] tupleInArray = tuple.toArray();

        //Then
        // array contains all tuple values
        assertEquals(tupleInArray[0], t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void toList_whenBuildList_thenListContainsAllElements(A t0) {
        //Given
        // The tuple with args
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // build list from this tuple
        List<Object> tupleInList = tuple.toList();

        //Then
        // list contains all tuple values
        assertEquals(tupleInList.get(0), t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void toSet_whenBuildSet_thenSetContainsAllElements(A t0) {
        //Given
        // The tuple with args
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // build list from this tuple
        Set<Object> tupleInSet = tuple.toSet();

        //Then
        // list contains all tuple values
        assertTrue(tupleInSet.contains(t0));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void toStream_whenBuildStream_thenStreamContainsAllElements(A t0) {
        //Given
        // The tuple with args
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // build list from this tuple
        Stream<Object> tupleInStream = tuple.toStream();

        //Then
        // list contains all tuple values
        assertTrue(tupleInStream.anyMatch(tupleElement -> {
            return tupleElement.equals(t0);
        }));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void spliterator_whenCreateSpliterator_thenReturnNonNullIterator(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

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
    <A> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // create iterator
        Iterator<Object> iterator = tuple.iterator();

        //Then
        // iterator is not null
        assertNotNull(iterator);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void toString_whenBuildString_thenStringContainsAllElements(A t0) {
        //Given
        // The tuple with args
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // build string representation for this tuple
        String tupleInString = tuple.toString();

        //Then
        // string representation contains all tuple values
        assertTrue(tupleInString.contains(String.valueOf(t0)));
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Monuple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @DataProvider
    static Object[][] providesConstructorArgsAndIndexes() {
        return new Object[][]{
                {1, 1, 0},
                {1, 0, -1}
        };
    }

    @DataProvider
    static Object[][] providesConstructorArgsAndLastIndexes() {
        return new Object[][]{
                {1, 1, 0},
                {1, 0, -1}
        };
    }

    @DataProvider
    static Object[][] providesConstructorArgs() {
        return new Object[][] {
                {Byte.MIN_VALUE},
                {Character.MIN_VALUE},
                {Short.MIN_VALUE},
                {Integer.MIN_VALUE},
                {Float.MIN_VALUE},
                {Double.MIN_VALUE},
                {String.valueOf(Integer.MAX_VALUE)}
        };
    }
}
