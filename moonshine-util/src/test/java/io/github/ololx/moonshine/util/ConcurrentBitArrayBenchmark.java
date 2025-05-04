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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27/09/2023 8:27 pm
 */
@State(Scope.Thread)
@Warmup(
    iterations = 5,
    time = 1000,
    timeUnit = TimeUnit.MILLISECONDS
)
@Measurement(
    iterations = 5,
    time = 1000,
    timeUnit = TimeUnit.MILLISECONDS
)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ConcurrentBitArrayBenchmark {

    private static final int AVAILABLE_CPU = Runtime.getRuntime()
        .availableProcessors();

    private static final String CONCURRENT_BIT_ARRAY = "ConcurrentBitArray";

    @Param(CONCURRENT_BIT_ARRAY)
    private String typeOfBitCollectionRealization;

    @Param({"64", "640"})
    private int sizeOfBitCollection;

    @Param({"1", "5"})
    private int countOfSetters;

    @Param({"1", "5"})
    private int countOfGetters;

    private ConcurrentBitCollection concurrentBitCollection;

    private ExecutorService executor;

    public ConcurrentBitArrayBenchmark() {}

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ConcurrentBitArrayBenchmark.class.getSimpleName())
            .build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        switch (typeOfBitCollectionRealization) {
            case CONCURRENT_BIT_ARRAY:
                concurrentBitCollection = new ConcurrentBitArray(sizeOfBitCollection);
                break;
        }

        executor = Executors.newWorkStealingPool(AVAILABLE_CPU);
    }

    @TearDown
    public void tearDown() {
        executor.shutdown();
    }

    @Benchmark
    public void get_set_benchmark(Blackhole blackhole) {
        int tasksCount = sizeOfBitCollection * (countOfGetters + countOfSetters);
        ArrayList<CompletableFuture<Void>> bitCollectionInvocations = new ArrayList<CompletableFuture<Void>>(tasksCount);

        for (int setBitOpNumber = 0; setBitOpNumber < countOfGetters; setBitOpNumber++) {
            bitCollectionInvocations.add(CompletableFuture.runAsync(() -> {
                for (int bitNumber = 0; bitNumber < sizeOfBitCollection; bitNumber++) {
                    blackhole.consume(concurrentBitCollection.get(bitNumber));
                }
            }, executor));
        }

        for (int getBitOpNumber = 0; getBitOpNumber < countOfSetters; getBitOpNumber++) {
            bitCollectionInvocations.add(CompletableFuture.runAsync(() -> {
                for (int bitNumber = 0; bitNumber < sizeOfBitCollection; bitNumber++) {
                    concurrentBitCollection.set(bitNumber);
                }
            }, executor));
        }

        CompletableFuture.allOf(bitCollectionInvocations.toArray(new CompletableFuture[0]))
            .join();
    }
}
