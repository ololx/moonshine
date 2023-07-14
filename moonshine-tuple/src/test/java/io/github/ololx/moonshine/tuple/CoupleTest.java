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
public class CoupleTest {

    @DataProvider
    static Object[][] providesConstructorArgsAndIndexes() {
        return new Object[][]{
            {1, 2, 1, 0},
            {1, 2, 2, 1},
            {1, 2, 0, -1}
        };
    }

    @DataProvider
    static Object[][] providesConstructorArgsAndLastIndexes() {
        return new Object[][]{
            {1, 1, 1, 1},
            {1, 2, 0, -1}
        };
    }

    @DataProvider
    static Object[][] providesConstructorArgs() {
        return new Object[][]{
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
    <A, B> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(A t0, B t1) {
        //When
        // create new tuple with specified args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void of_whenCreateTuple_thenTupleContainsValuesOfArgs(A t0, B t1) {
        //When
        // create new tuple with specified args
        Tuple2<A, B> tuple = Couple.of(t0, t1);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void getT0_whenGet_thenReturnThisElementValue(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get value by index 0
        Object actual = tuple.getT0();

        //Then
        // actual value equals to stored value
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void getT1_whenGet_thenReturnThisElementValue(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get value by index 1
        Object actual = tuple.getT1();

        //Then
        // actual value equals to stored value
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void get_whenIndexExists_thenReturnValueByIndex(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get values by indexes 0, 1
        Object actualT0 = tuple.get(0);
        Object actualT1 = tuple.get(1);

        //Then
        // actual values equal to stored values
        assertEquals(actualT0, t0);
        assertEquals(actualT1, t1);
    }

    @Test(
        dataProvider = "providesConstructorArgs",
        expectedExceptions = IndexOutOfBoundsException.class
    )
    <A, B> void get_whenIndexLessThanZero_thenThrowException(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

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
    <A, B> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(A t0, B t1) {
        //Given
        // The tuple with size = 2
        // and some default value
        Tuple2<A, B> tuple = new Couple<>(t0, t1);
        String defaultValue = "default";

        //When
        // get values by index < 0 and index == tuple size
        Object actual0 = tuple.getOrDefault(-1, defaultValue);
        Object actual1 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual0, defaultValue);
        assertEquals(actual1, defaultValue);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void contains_whenTupleContainsValue_thenReturnTrue(A t0, B t1) {
        //Given
        // The tuple with values
        final Couple<A, B> tuple = new Couple<>(t0, t1);

        //When
        // check that tuple contains construct args
        final Set<Boolean> allContainsResults = Stream.of(t0, t1)
            .map(tuple::contains)
            .collect(Collectors.toSet());

        //Then
        // no one check return false
        assertFalse(allContainsResults.contains(false));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void contains_whenTupleDoNotContainsValue_thenReturnTrue(A t0, B t1) {
        //Given
        // The tuple with values
        final Couple<A, B> tuple = new Couple<>(t0, t1);

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

    @Test(dataProvider = "providesConstructorArgsAndIndexes")
    <A> void indexOf_whenTupleContainsValue_thenReturnTheirIndex(A t0,
                                                                 A t1,
                                                                 A someValue,
                                                                 int expectedIndex) {
        //Given
        // The tuple with values
        final Couple<A, A> tuple = new Couple<>(t0, t1);

        //When
        // get index of some value
        final int actualIndex = tuple.indexOf(someValue);

        //Then
        // actual index equals expected index
        assertEquals(actualIndex, expectedIndex);
    }

    @Test(dataProvider = "providesConstructorArgsAndLastIndexes")
    <A> void lastIndexOf_whenTupleContainsValue_thenReturnTheirIndex(A t0,
                                                                     A t1,
                                                                     A someValue,
                                                                     int expectedIndex) {
        //Given
        // The tuple with values
        final Couple<A, A> tuple = new Couple<>(t0, t1);

        //When
        // get last index of some value
        final int actualIndex = tuple.lastIndexOf(someValue);

        //Then
        // actual index equals expected index
        assertEquals(actualIndex, expectedIndex);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void size_whenCreateTuple_thenTupleHasSize(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // get tuple size
        int actual = tuple.size();
        int expected = 2;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void toArray_whenBuildArray_thenArrayContainsAllElements(A t0, B t1) {
        //Given
        // The tuple with args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // build array from this tuple
        Object[] tupleInArray = tuple.toArray();

        //Then
        // array contains all tuple values
        assertEquals(tupleInArray[0], t0);
        assertEquals(tupleInArray[1], t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void toList_whenBuildList_thenListContainsAllElements(A t0, B t1) {
        //Given
        // The tuple with args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // build list from this tuple
        List<Object> tupleInList = tuple.toList();

        //Then
        // list contains all tuple values
        assertEquals(tupleInList.get(0), t0);
        assertEquals(tupleInList.get(1), t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void toSet_whenBuildSet_thenSetContainsAllElements(A t0, B t1) {
        //Given
        // The tuple with args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // build list from this tuple
        Set<Object> tupleInSet = tuple.toSet();

        //Then
        // list contains all tuple values
        assertTrue(tupleInSet.contains(t0));
        assertTrue(tupleInSet.contains(t1));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void stream_whenBuildStream_thenStreamContainsAllElements(A t0, B t1) {
        //Given
        // The tuple with args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // build list from this tuple
        Stream<Object> tupleInStream = tuple.stream();

        //Then
        // list contains all tuple values
        assertTrue(tupleInStream.allMatch(tupleElement -> {
            return tupleElement.equals(t0) || tupleElement.equals(t1);
        }));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void spliterator_whenCreateSpliterator_thenReturnNonNullIterator(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

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
    <A, B> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0, B t1) {
        //Given
        // The tuple with size = 2
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // create iterator
        Iterator<Object> iterator = tuple.iterator();

        //Then
        // iterator is not null
        assertNotNull(iterator);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void toString_whenBuildString_thenStringContainsAllElements(A t0, B t1) {
        //Given
        // The tuple with args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // build string representation for this tuple
        String tupleInString = tuple.toString();

        //Then
        // string representation contains all tuple values
        assertTrue(tupleInString.contains(String.valueOf(t0)));
        assertTrue(tupleInString.contains(String.valueOf(t1)));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B> void convert_whenConvertingTupleToString_thenStringEqualsFirstElementOrDefault(A t0, B t1) {
        //Given
        // The tuple with args
        Tuple2<A, B> tuple = new Couple<>(t0, t1);

        //When
        // build string representation for this tuple
        String tupleInString = tuple.convert(t -> String.valueOf(t.getOrDefault(0, "NA")));

        //Then
        // string representation contains all tuple values
        assertTrue(tupleInString.equals(String.valueOf(t0)) || tupleInString.equals("NA"));
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Couple.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .verify();
    }
}
