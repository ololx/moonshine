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

package io.github.com.ololx.moonshine.measuring.memory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
    public void testOfBytes(long memorySize) {
        Memory memory = Memory.ofBytes(memorySize);
        assertEquals(memory.toBytes(), memorySize);
    }

    @Test(dataProvider = "providesMemorySize")
    public void testOfKilobytes(long memorySize) {
        Memory memory = Memory.ofKilobytes(memorySize);
        assertEquals(memory.toBytes(), memorySize * 1024);
    }

    @Test(dataProvider = "providesMemorySize")
    public void testOfMegabytes(long memorySize) {
        Memory memory = Memory.ofMegabytes(memorySize);
        assertEquals(memory.toBytes(), memorySize * 1_048_576);
    }

    @Test(dataProvider = "providesMemorySize")
    public void testOfGigabytes(long memorySize) {
        Memory memory = Memory.ofGigabytes(memorySize);
        assertEquals(memory.toBytes(), memorySize * 1_073_741_824);
    }

    @Test(dataProvider = "providesMemorySize")
    public void testOfTerabytes(long memorySize) {
        Memory memory = Memory.ofTerabytes(memorySize);
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
    public void testPlus(long firstMemorySize, long secondMemorySize, long expectedMemorySize) {
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
    public void testMinus(long firstMemorySize, long secondMemorySize, long expectedMemorySize) {
        Memory actual = Memory.ofBytes(firstMemorySize).minus(Memory.ofBytes(secondMemorySize));
        assertEquals(actual.toBytes(), expectedMemorySize);
    }

    @Test
    public void testComparable() {
        Memory memory1 = Memory.ofBytes(100L);
        Memory memory2 = Memory.ofKilobytes(200L);
        assertTrue(memory1.compareTo(memory2) < 0);
        assertTrue(memory2.compareTo(memory1) > 0);
        assertEquals(memory1.compareTo(memory1), 0);
    }
}
