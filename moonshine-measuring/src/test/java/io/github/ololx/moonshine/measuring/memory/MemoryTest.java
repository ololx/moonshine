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

package io.github.ololx.moonshine.measuring.memory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 07.04.2023 15:56
 * 
 * @author Alexander A. Kropotin
 */
public class MemoryTest {

    @DataProvider
    static Object[][] providesMemorySize() {
        return new Object[][] {{0L}, {1L}, {100L}, {1000L}};
    }

    @Test(dataProvider = "providesMemorySize")
    public void ofBytes_whenCreateNew_thenCreatedMemoryContainsSpecifiedBytesSize(long memorySize) {
        //When
        // create new memory instance
        Memory memory = Memory.ofBytes(memorySize);

        //Then
        // created memory instance size equals expected
        assertEquals(memory.toBytes(), memorySize);
    }

    @Test(dataProvider = "providesMemorySize")
    public void ofKilobytes_whenCreateNew_thenCreatedMemoryContainsSpecifiedBytesSize(long memorySize) {
        //When
        // create new memory instance
        Memory memory = Memory.ofKilobytes(memorySize);

        //Then
        // created memory instance size equals expected
        assertEquals(memory.toBytes(), memorySize * 1024);
    }

    @Test(dataProvider = "providesMemorySize")
    public void ofMegabytes_whenCreateNew_thenCreatedMemoryContainsSpecifiedBytesSize(long memorySize) {
        //When
        // create new memory instance
        Memory memory = Memory.ofMegabytes(memorySize);

        //Then
        // created memory instance size equals expected
        assertEquals(memory.toBytes(), memorySize * 1_048_576);
    }

    @Test(dataProvider = "providesMemorySize")
    public void ofGigabytes_whenCreateNew_thenCreatedMemoryContainsSpecifiedBytesSize(long memorySize) {
        //When
        // create new memory instance
        Memory memory = Memory.ofGigabytes(memorySize);

        //Then
        // created memory instance size equals expected
        assertEquals(memory.toBytes(), memorySize * 1_073_741_824);
    }

    @Test(dataProvider = "providesMemorySize")
    public void ofTerabytes_whenCreateNew_thenCreatedMemoryContainsSpecifiedBytesSize(long memorySize) {
        //When
        // create new memory instance
        Memory memory = Memory.ofTerabytes(memorySize);

        //Then
        // created memory instance size equals expected
        assertEquals(memory.toBytes(), memorySize * 1_099_511_627_776L);
    }

    @DataProvider
    static Object[][] providesMemorySizeFirstAndSecondAndSum() {
        return new Object[][] {
                {0L, 0L, 0L},
                {1L, 0L, 1L},
                {0L, 1L, 1L},
                {100L, 100L, 200L},
                {512L, 512L, 1024L},
        };
    }

    @Test(dataProvider = "providesMemorySizeFirstAndSecondAndSum")
    public void plus_whenPlusFirstMemoryWithSecond_thenResultMemoryEqualsSumOfTheirBytes(
            long firstMemorySize,
            long secondMemorySize,
            long expectedMemorySize) {
        Memory actual = Memory.ofBytes(firstMemorySize).plus(Memory.ofBytes(secondMemorySize));
        assertEquals(actual.toBytes(), expectedMemorySize);
    }

    @DataProvider
    static Object[][] providesMemorySizeFirstAndSecondAndDifference() {
        return new Object[][] {
                {0L, 0L, 0L},
                {1L, 0L, 1L},
                {1L, 1L, 0L},
                {200L, 100L, 100L},
                {1024L, 512L, 512L},
        };
    }

    @Test(dataProvider = "providesMemorySizeFirstAndSecondAndDifference")
    public void minus_whenPlusFirstMemoryWithSecond_thenResultMemoryEqualsDifferenceOfTheirBytes(
            long firstMemorySize,
            long secondMemorySize,
            long expectedMemorySize) {
        Memory actual = Memory.ofBytes(firstMemorySize).minus(Memory.ofBytes(secondMemorySize));
        assertEquals(actual.toBytes(), expectedMemorySize);
    }

    @DataProvider
    static Object[][] providesMemorySizeFirstAndSecondAndComparingResult() {
        return new Object[][] {
                {0L, 0L, 0},
                {0L, 1L, -1},
                {1L, 0L, 1},
                {1L, 1L, 0},
                {200L, 200L, 0},
                {200L, 100L, 1},
                {100L, 200L, -1},
                {512L, 1024L, -1},
                {512L, 512L, 0},
                {1024L, 512L, 1},
        };
    }

    @Test(dataProvider = "providesMemorySizeFirstAndSecondAndComparingResult")
    public void comparable_whenMemryInstancesEquals_thenReturnZero(long firstMemorySize,
                                                                   long secondMemorySize,
                                                                   long expectedComparingResult) {
        Memory first = Memory.ofBytes(firstMemorySize);
        Memory second = Memory.ofBytes(secondMemorySize);

        assertEquals(first.compareTo(second), expectedComparingResult);
    }
}
