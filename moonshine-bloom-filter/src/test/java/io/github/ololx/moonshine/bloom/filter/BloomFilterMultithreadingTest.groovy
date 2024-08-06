/**
 * Copyright 2024 the project moonshine authors
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
package io.github.ololx.moonshine.bloom.filter

import edu.umd.cs.mtc.MultithreadedTest
import edu.umd.cs.mtc.TestFramework
import org.junit.Test

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 06/08/2024 6:30pm
 */
class BloomFilterMultithreadingTest extends MultithreadedTest {

    BasicBloomFilter bloomFilter

    @Override
    void initialize() {
        List<BloomFilter.HashFunction> hashFunctions = [
            new HashFunction1(),
            new HashFunction2()
        ]
        bloomFilter = BasicBloomFilter.newInstance(100, hashFunctions, 0)
    }

    @Test
    void add_whenInvokeInManyThreads_thenAllChangesAreAppliedCorrectly() {
        assertTrue(checkInMultiThreading())
    }

    static boolean checkInMultiThreading() {
        TestFramework.runManyTimes(new BloomFilterMultithreadingTest(), 1000)
        return true
    }

    void thread1() {
        bloomFilter.add(() -> "thread1".bytes)
    }

    void thread2() {
        bloomFilter.add(() -> "thread2".bytes)
    }

    @Override
    void finish() {
        assertFalse(bloomFilter.absent(() -> "thread1".bytes))
        assertFalse(bloomFilter.absent(() -> "thread2".bytes))
    }
}

class HashFunction1 implements BloomFilter.HashFunction {

    @Override
    int apply(byte[] value) {
        return Arrays.hashCode(value)
    }
}

class HashFunction2 implements BloomFilter.HashFunction {

    @Override
    int apply(byte[] value) {
        return value.length > 0 ? value[0] : 0
    }
}
