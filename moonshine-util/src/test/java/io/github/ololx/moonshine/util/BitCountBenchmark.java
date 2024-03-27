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

package io.github.ololx.moonshine.util;

import io.github.ololx.moonshine.util.concurrent.ConcurrentBitArray;
import io.github.ololx.moonshine.util.concurrent.ConcurrentBitCollection;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 26/03/2024 8:59pm
 */
@State(Scope.Thread)
@Warmup(
    iterations = 5,
    time = 100,
    timeUnit = TimeUnit.MILLISECONDS
)
@Measurement(
    iterations = 100,
    time = 100,
    timeUnit = TimeUnit.MILLISECONDS
)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BitCountBenchmark {

    private byte[] bytes;

    @Setup
    public void setup() {
        this.bytes = new byte[256];
        for (int byteValue = -128; byteValue <= 127; byteValue++) {
            this.bytes[byteValue + 128] = (byte) byteValue;
        }
    }

    @Benchmark
    public void testByteBitCounting(Blackhole blackhole) {
        for (byte byteValue : this.bytes) {
            blackhole.consume(ConcurrentBitArray.ByteBitCounting.bitCount(byteValue));
        }
    }

    @Benchmark
    public void testByteBitCountingLoockUp(Blackhole blackhole) {
        for (byte byteValue : this.bytes) {
            blackhole.consume(ConcurrentBitArray.ByteBitCountingLoUp.bitCount(byteValue));
        }
    }

    @Benchmark
    public void testIntegerBitCount(Blackhole blackhole) {
        for (byte byteValue : this.bytes) {
            blackhole.consume(Integer.bitCount(byteValue));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(BitCountBenchmark.class.getSimpleName())
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}

