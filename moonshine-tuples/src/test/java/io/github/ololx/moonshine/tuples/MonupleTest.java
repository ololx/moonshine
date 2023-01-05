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

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 03.01.2023 12:43
 *
 * @author Alexander A. Kropotin
 */
public class MonupleTest {

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

    @Test(dataProvider = "providesConstructorArgs")
    <A> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(final A t1) {
        //When
        // create new tuple with specified args
        final Monuple<A> tuple = new Monuple<>(t1);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT1(), t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void get_whenIndexExists_thenReturnValueByIndex(final A t1) {
        //Given
        // The tuple with size = 1
        final Monuple<A> tuple = new Monuple<>(t1);

        //When
        // get value by index 0
        final Object actual = tuple.get(0);

        //Then
        // actual value equals to stored value
        assertEquals(actual, t1);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(final A t1) {
        //Given
        // The tuple with size = 1
        // and some default value
        final Monuple<A> tuple = new Monuple<>(t1);
        final String defaultValue = "default";

        //When
        // get values by index < 0 and index >= tuple size
        final Object actual = tuple.getOrDefault(-1, defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual, defaultValue);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <A> void size_whenCreateTuple_thenTupleHasSize(final A t1) {
        //Given
        // The tuple with size = 1
        final Monuple<A> tuple = new Monuple<>(t1);

        //When
        // get tuple size
        final int actual = tuple.size();
        final int expected = 1;

        //Then
        // size equal to expected
        assertEquals(actual, expected);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Monuple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
