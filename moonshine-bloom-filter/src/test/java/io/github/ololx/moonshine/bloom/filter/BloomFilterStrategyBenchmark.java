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

package io.github.ololx.moonshine.bloom.filter;

import io.github.ololx.moonshine.bloom.filter.BasicBloomFilter;
import io.github.ololx.moonshine.util.concurrent.ConcurrentBitArray;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 01/05/2024 8:37am
 */
@State(Scope.Benchmark)
@Warmup(
    iterations = 5,
    time = 1,
    timeUnit = TimeUnit.SECONDS
)
@Measurement(
    iterations = 5,
    time = 1,
    timeUnit = TimeUnit.SECONDS
)
@Fork(5)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BloomFilterStrategyBenchmark {

    @Param({"0", "1"})
    private int strategy;

    private BasicBloomFilter bloomFilter;

    @Param({"5", "10", "20"})
    private int power;

    @Setup
    public void setup() {
        int size = 1 << power;
        List<BloomFilter.HashFunction> hashFunctions = new ArrayList<>();
        IntStream.rangeClosed(1, size).forEach(i -> {
            BloomFilter.HashFunction hashFunction = (value) -> {
                int hash = i;

                for (byte b : value) {
                    hash = (hash * 31) + (b & 0xFF);
                }

                return hash;
            };
            hashFunctions.add(hashFunction);
        });

        bloomFilter = BasicBloomFilter.newInstance(size, hashFunctions, strategy);
    }

    static int hash(byte[] data) {
        int hash = 0;
        for (byte b : data) {
            hash = (hash * 31) + (b & 0xFF);
        }
        return hash;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(BloomFilterStrategyBenchmark.class.getSimpleName())
            .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void testAbsent(Blackhole blackhole) {
        IntStream.rangeClosed('A', 'Z')
            .mapToObj(String::valueOf)
            .forEach(ch -> {
                boolean result = bloomFilter.absent(ch::getBytes);
                blackhole.consume(result);
            });
    }
}
