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
    static Object[] providesConstructorArgs() {
        return new Object[] {
                null,
                "",
                "str",
                12,
                1L,
                13F,
                14D
        };
    }

    @Test(dataProvider = "providesConstructorArgs")
    <T> void new_whenCreateTuple_theTupleContainsValuesOfConstructorArgs(final T arg) {
        //When
        // create new tuple with specified args
        final Monuple<T> tuple = new Monuple<>(arg);

        //Then
        // tuple contains arg value
        assertEquals(tuple.getT1(), arg);
    }
}
