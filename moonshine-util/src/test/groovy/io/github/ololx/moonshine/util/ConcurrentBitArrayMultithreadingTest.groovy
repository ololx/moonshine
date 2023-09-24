/**
 * Copyright 2022 the project moonshine authors
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

package io.github.ololx.moonshine.util

import edu.umd.cs.mtc.MultithreadedTest
import edu.umd.cs.mtc.TestFramework
import io.github.ololx.moonshine.util.concurrent.ConcurrentBitArray
import org.junit.Test

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 13/09/2023 9:40 pm
 */
class ConcurrentBitArrayMultithreadingTest extends MultithreadedTest {

    ConcurrentBitArray bitSet = new ConcurrentBitArray(3)

    @Test
    void set_whenInvokeInManyThreadsAndManyTimes_thenAllChangesWereApplied() {
        assertTrue(checkInMultiThreading())
    }

    static def checkInMultiThreading() {
        TestFramework.runManyTimes(new ConcurrentBitArrayMultithreadingTest(), 1000)
        return true
    }

    void thread1() {
        this.bitSet.set(1)
    }

    void thread2() {
        this.bitSet.set(2)
    }

    @Override
    void finish() {
        assertTrue(bitSet.get(1) && bitSet.get(2))
    }
}
