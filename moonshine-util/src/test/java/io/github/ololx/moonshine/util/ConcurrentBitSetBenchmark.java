package io.github.ololx.moonshine.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(
        iterations = 5,
        time = 100,
        timeUnit = TimeUnit.MILLISECONDS
)
@Measurement(
        iterations = 5,
        time = 100,
        timeUnit = TimeUnit.MILLISECONDS
)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ConcurrentBitSetBenchmark {

    private static final int AVAILABLE_CPU = Runtime.getRuntime()
            .availableProcessors();

    private static final String FULL_SYNCHRONIZATION = "javaNativeWithSynchronizationByThis";

    private static final String SEGMENTS_SYNCHRONIZATION = "javaNativeWithSynchronizationBySegments";

    private static final String ONE_READ_WRITE_LOCK = "javaNativeWithOneReadWriteLock";

    private static final String MANY_READ_WRITE_LOCKS = "javaNativeWithManyReadWriteLocksBySegments";

    private static final String NON_BLOCKING = "NonBlockingConcurrentBitset";

    private ConcurrentBitSet concurrentBitSet;

    @Param(
            {
                    FULL_SYNCHRONIZATION,
                    ONE_READ_WRITE_LOCK,
                    SEGMENTS_SYNCHRONIZATION,
                    MANY_READ_WRITE_LOCKS,
                    NON_BLOCKING
            }
    )
    private String typeOfBitSetRealization;

    private ExecutorService executor;

    @Param({"640"})
    private int sizeOfBitSet;

    @Param({"5"})
    private int countOfSetters;

    @Param({"5"})
    private int countOfGetters;

    public ConcurrentBitSetBenchmark() {}

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ConcurrentBitSetBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        switch (typeOfBitSetRealization) {
            case FULL_SYNCHRONIZATION:
                concurrentBitSet = new ConcurrentBitSetWithFullSynchronization(sizeOfBitSet);
                break;
            case ONE_READ_WRITE_LOCK:
                concurrentBitSet = new ConcurrentBitSetWithGeneralRWLock(sizeOfBitSet);
                break;
            case SEGMENTS_SYNCHRONIZATION:
                concurrentBitSet = new ConcurrentBitSetWithSegmentsSynchronization(sizeOfBitSet);
                break;
            case MANY_READ_WRITE_LOCKS:
                concurrentBitSet = new ConcurrentBitSetWithSegmentsRWLocks(sizeOfBitSet);
                break;
            case NON_BLOCKING:
                concurrentBitSet = new NonBlockingConcurrentBitSet(sizeOfBitSet);
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
        int tasksCount = sizeOfBitSet * (countOfGetters + countOfSetters);
        List<CompletableFuture<Void>> bitsetInvocations = new ArrayList<>(tasksCount);

        for (int setBitOpNumber = 0; setBitOpNumber < countOfGetters; setBitOpNumber++) {
            bitsetInvocations.add(CompletableFuture.runAsync(() -> {
                for (int bitNumber = 0; bitNumber < sizeOfBitSet; bitNumber++) {
                    blackhole.consume(concurrentBitSet.get(bitNumber));
                }

            }, executor));
        }

        for (int getBitOpNumber = 0; getBitOpNumber < countOfSetters; getBitOpNumber++) {
            bitsetInvocations.add(CompletableFuture.runAsync(() -> {
                for (int bitNumber = 0; bitNumber < sizeOfBitSet; bitNumber++) {
                    concurrentBitSet.set(bitNumber);
                }

            }, executor));
        }

        CompletableFuture.allOf(bitsetInvocations.toArray(new CompletableFuture[0]))
                .join();
    }
}
