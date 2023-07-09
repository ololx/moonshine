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
 * created 05.01.2023 10:00
 *
 * @author Alexander A. Kropotin
 */
public class EmptyTupleTest {

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    void get_whenInvokeWithAnyIndex_thenAlwaysThrowException() {
        //Given
        // The tuple with size = 0
        Tuple0 tuple = new EmptyTuple();

        //When
        // get values by index = size - 1
        //Then
        // throw exception
        assertNull(tuple.get(tuple.size() - 1));
    }

    @Test
    void getOrDefault_whenIndexNotExists_thenReturnDefaultValue() {
        //Given
        // The tuple with size = 0
        // and some default value
        Tuple0 tuple = new EmptyTuple();
        String defaultValue = "default";

        //When
        // get values by index < 0 and index >= tuple size
        Object actual1 = tuple.getOrDefault(-1, defaultValue);
        Object actual2 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual1, defaultValue);
        assertEquals(actual2, defaultValue);
    }

    @Test
    void size_whenCreateTuple_thenTupleHasSize() {
        //Given
        // The tuple with size = 0
        Tuple0 tuple = new EmptyTuple();

        //When
        // get tuple size
        int actual = tuple.size();
        int expected = 0;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test
    void indexOf_whenTupleIsEmpty_thenAlwaysReturnMinusOne() {
        //Given
        // The tuple with values
        final EmptyTuple tuple = new EmptyTuple();

        //When
        // get index of some value
        final int actualIndex = tuple.indexOf("some value");

        //Then
        // actual index equals is -1
        assertEquals(actualIndex, -1);
    }

    @Test
    void lastIndexOf_whenTupleIsEmpty_thenAlwaysReturnMinusOne() {
        //Given
        // The tuple with values
        final EmptyTuple tuple = new EmptyTuple();

        //When
        // get last index of some value
        final int actualIndex = tuple.lastIndexOf("some value");

        //Then
        // actual index is -1
        assertEquals(actualIndex, -1);
    }


    @Test
    void contains_whenTupleIsEmpty_thenAlwaysReturnFalse() {
        //Given
        // The tuple with values
        final EmptyTuple tuple = new EmptyTuple();

        //When
        // check that tuple contains some value,
        // not from this tuple
        final Set<Boolean> allContainsResults = Stream.of("some value")
            .map(tuple::contains)
            .collect(Collectors.toSet());

        //Then
        // no one check return true
        assertFalse(allContainsResults.contains(true));
    }

    @Test
    void toArray_whenBuildArray_thenArrayContainsAllElements() {
        //Given
        // The tuple with args
        Tuple0 tuple = new EmptyTuple();

        //When
        // build array from this tuple
        Object[] tupleInArray = tuple.toArray();

        //Then
        // array is empty
        assertEquals(tupleInArray.length, 0);
    }

    @Test
    void toList_whenBuildList_thenListContainsAllElements() {
        //Given
        // The tuple with args
        Tuple0 tuple = new EmptyTuple();

        //When
        // build list from this tuple
        List<Object> tupleInList = tuple.toList();

        //Then
        // list is empty
        assertTrue(tupleInList.isEmpty());
    }

    @Test
    void toSet_whenBuildSet_thenSetContainsAllElements() {
        //Given
        // The tuple with args
        Tuple0 tuple = new EmptyTuple();

        //When
        // build set from this tuple
        Set<Object> tupleInSet = tuple.toSet();

        //Then
        // set is empty
        assertTrue(tupleInSet.isEmpty());
    }

    @Test
    void stream_whenBuildStream_thenStreamContainsAllElements() {
        //Given
        // The tuple with args
        Tuple0 tuple = new EmptyTuple();

        //When
        // build stream from this tuple
        Stream<Object> tupleInStream = tuple.stream();

        //Then
        // stream is empty
        assertEquals(tupleInStream.count(), 0);
    }

    @Test
    void iterator_whenCreateIterator_thenReturnNonNullIterator() {
        //Given
        // The tuple with size = 0
        Tuple0 tuple = new EmptyTuple();

        //When
        // create iterator
        Iterator<Object> iterator = tuple.iterator();

        //Then
        // iterator is not null
        assertNotNull(iterator);
    }

    @Test
    void spliterator_whenCreateSpliterator_thenReturnNonNullIterator() {
        //Given
        // The tuple with size = 0
        Tuple0 tuple = new EmptyTuple();

        //When
        // create spliterator
        Spliterator<Object> spliterator = tuple.spliterator();

        //Then
        // spliterator is not null
        // and spliterator contains sized, immutable, and ordered in characteristics
        assertNotNull(spliterator);
        assertEquals(spliterator.characteristics() ^ SUBSIZED, (SIZED | IMMUTABLE | ORDERED));
    }

    @Test
    void toString_whenBuildString_thenStringContainsAllElements() {
        //Given
        // The tuple without args
        Tuple0 tuple = new EmptyTuple();

        //When
        // build string representation for this tuple
        String tupleInString = tuple.toString();

        //Then
        // string representation doesn't contain any values
        assertEquals(tupleInString, "()");
    }

    @Test
    void convert_whenConvertingTupleToString_thenStringEqualsDefault() {
        //Given
        // The tuple with args
        Tuple0 tuple = new EmptyTuple();

        //When
        // build string representation for this tuple
        String tupleInString = tuple.convert(t -> String.valueOf(t.getOrDefault(0, "NA")));

        //Then
        // string representation contains all tuple values
        assertEquals("NA", tupleInString);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(EmptyTuple.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .verify();
    }
}
