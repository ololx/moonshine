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

import static org.testng.Assert.*;

/**
 * project moonshine
 * created 05.01.2023 20:47
 *
 * @author Alexander A. Kropotin
 */
public class TripleTest {

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(A t0, B t1, C t2) {
        //When
        // create new tuple with specified args
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT0(), t0);
        assertEquals(tuple.getT1(), t1);
        assertEquals(tuple.getT2(), t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void getT0_whenGet_thenReturnThisElementValue(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // get values by index 0
        Object actual = tuple.getT0();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t0);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void getT1_whenGet_thenReturnThisElementValue(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // get values by index 1
        Object actual = tuple.getT1();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void getT2_whenGet_thenReturnThisElementValue(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // get values by index 1
        Object actual = tuple.getT2();

        //Then
        // actual values equal to stored values
        assertEquals(actual, t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void get_whenIndexExists_thenReturnValueByIndex(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // get values by indexes 0, 1, 2
        Object actualT0 = tuple.get(0);
        Object actualT1 = tuple.get(1);
        Object actualT2 = tuple.get(2);

        //Then
        // actual values equal to stored values
        assertEquals(actualT0, t0);
        assertEquals(actualT1, t1);
        assertEquals(actualT2, t2);
    }

    @Test(
            dataProvider = "providesConstructorArgs",
            expectedExceptions = IndexOutOfBoundsException.class
    )
    <A, B, C> void get_whenIndexLessThanZero_thenThrowException(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

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
    <A, B, C> void get_whenIndexMoreOrEqualTupleSize_thenThrowException(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // get values by index == tuple size
        //Then
        // throw exception
        tuple.get(tuple.size());
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        // and some default value
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);
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
    <A, B, C> void size_whenCreateTuple_thenTupleHasSize(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // get tuple size
        int actual = tuple.size();
        int expected = 3;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0, B t1, C t2) {
        //Given
        // The tuple with size = 3
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // create iterator
        Iterator<Object> iterator = tuple.iterator();

        //Then
        // size equal to expected
        assertNotNull(iterator);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void toString_whenBuildString_thenStringContainsAllElements(A t0, B t1, C t2) {
        //Given
        // The tuple with args
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // build string representation for this tuple
        String tupleInString = tuple.toString();

        //Then
        // string representation contains all tuple values
        assertTrue(tupleInString.contains(String.valueOf(t0)));
        assertTrue(tupleInString.contains(String.valueOf(t1)));
        assertTrue(tupleInString.contains(String.valueOf(t2)));
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void toArray_whenBuildArray_thenArrayContainsAllElements(A t0, B t1, C t2) {
        //Given
        // The tuple with args
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // build array from this tuple
        Object[] tupleInArray = tuple.toArray();

        //Then
        // array contains all tuple values
        assertEquals(tupleInArray[0], t0);
        assertEquals(tupleInArray[1], t1);
        assertEquals(tupleInArray[2], t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A, B, C> void toList_whenBuildList_thenListContainsAllElements(A t0, B t1, C t2) {
        //Given
        // The tuple with args
        Triple<A, B, C> tuple = new Triple<>(t0, t1, t2);

        //When
        // build list from this tuple
        List<Object> tupleInList = tuple.toList();

        //Then
        // list contains all tuple values
        assertEquals(tupleInList.get(0), t0);
        assertEquals(tupleInList.get(1), t1);
        assertEquals(tupleInList.get(2), t2);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Triple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

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
}
