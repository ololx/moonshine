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
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "There is no elements by index.*"
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
            expectedExceptions = IndexOutOfBoundsException.class,
            expectedExceptionsMessageRegExp = "There is no elements by index.*"
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
    <A> void iterator_whenCreateIterator_thenReturnNonNullIterator(A t0) {
        //Given
        // The tuple with size = 1
        Monuple<A> tuple = new Monuple<>(t0);

        //When
        // create iterator
        Iterator<Object> iterator = tuple.iterator();

        //Then
        // size equal to expected
        assertNotNull(iterator);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Monuple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @DataProvider(name = "providesConstructorArgs")
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
