/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 08.09.2023 10:09
 */
public class NonBlockingConcurrentBitSetTest {

    @DataProvider
    public Object[][] providesBitSetsAndLength() {
        return new Object[][] {
            {new NonBlockingConcurrentBitSet(1), 1},
            {new NonBlockingConcurrentBitSet(8), 8},
            {new NonBlockingConcurrentBitSet(64), 64},
        };
    }

    @Test(dataProvider = "providesBitSetsAndLength")
    public void get_whenBitIsUnsetted_thenReturnFalse(NonBlockingConcurrentBitSet bitSet, int length) {
        for (int i = 0; i < length; i++) {
            assertFalse(bitSet.get(i));
        }
    }

    @Test(dataProvider = "providesBitSetsAndLength")
    public void set_whenSetBit_thenGetReturnTrue(NonBlockingConcurrentBitSet bitSet, int length) {
        for (int i = 0; i < length; i++) {
            assertFalse(bitSet.get(i));
            bitSet.set(i);
            assertTrue(bitSet.get(i));
        }
    }

    @Test(dataProvider = "providesBitSetsAndLength")
    public void clear_whenBitIsCleared_thenReturnFalse(NonBlockingConcurrentBitSet bitSet, int length) {
        for (int i = 0; i < length; i++) {
            bitSet.clear(i);
            assertFalse(bitSet.get(i));
            bitSet.set(i);
            bitSet.clear(i);
            assertFalse(bitSet.get(i));
        }
    }

    @Test(dataProvider = "providesBitSetsAndLength")
    public void flip_whenBitIsUnset_thenReturnTrue(NonBlockingConcurrentBitSet bitSet, int length) {
        for (int i = 0; i < length; i++) {
            assertFalse(bitSet.get(i));
            bitSet.flip(i);
            assertTrue(bitSet.get(i));
            bitSet.flip(i);
            assertFalse(bitSet.get(i));
        }
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void get_whenIndexLessThanZero_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(-1);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void get_whenIndexMoreThanBitSetLength_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(length);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void set_whenIndexLessThanZero_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(-1);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void set_whenIndexMoreThanBitSetLength_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(length);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void clear_whenIndexLessThanZero_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(-1);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void clear_whenIndexMoreThanBitSetLength_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(length);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void flip_whenIndexLessThanZero_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(-1);
    }

    @Test(dataProvider = "providesBitSetsAndLength", expectedExceptions = IndexOutOfBoundsException.class)
    public void flip_whenIndexMoreThanBitSetLength_thenThrowException(NonBlockingConcurrentBitSet bitSet, int length) {
        bitSet.get(length);
    }
}
