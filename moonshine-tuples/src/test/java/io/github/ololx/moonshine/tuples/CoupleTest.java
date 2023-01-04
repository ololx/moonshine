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
    <T1, T2> void new_whenCreateTuple_thenTupleContainsValuesOfConstructorArgs(final T1 t1,
                                                                               final T2 t2) {
        //When
        // create new tuple with specified args
        final Couple<T1, T2> tuple = new Couple<>(t1, t2);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT1(), t1);
        assertEquals(tuple.getT2(), t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <T1, T2> void get_whenIndexExists_thenReturnValueByIndex(final T1 t1,
                                                             final T2 t2) {
        //Given
        // The tuple with size = 2
        final Couple<T1, T2> tuple = new Couple<>(t1, t2);

        //When
        // get values by indexes 0, 1
        final Object actualT1 = tuple.get(0);
        final Object actualT2 = tuple.get(1);

        //Then
        // actual values equal to stored values
        assertEquals(actualT1, t1);
        assertEquals(actualT2, t2);
    }

    @Test(dataProvider = "providesConstructorArgs")
    <T1, T2> void getOrDefault_whenIndexNotExists_thenReturnDefaultValue(final T1 t1, final T2 t2) {
        //Given
        // The tuple with size = 2
        // and some default value
        final Couple<T1, T2> tuple = new Couple<>(t1, t2);
        final String defaultValue = "default";

        //When
        // get values by index < 0 and index >= tuple size
        final Object actual1 = tuple.getOrDefault(-1, defaultValue);
        final Object actual2 = tuple.getOrDefault(tuple.size(), defaultValue);

        //Then
        // actual values equal to default
        assertEquals(actual1, defaultValue);
        assertEquals(actual2, defaultValue);
    }

    @Test
    public void equalsHashCode_verifyContracts() {
        EqualsVerifier.forClass(Couple.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
