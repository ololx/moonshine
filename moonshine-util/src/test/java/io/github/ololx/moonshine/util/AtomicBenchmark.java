package io.github.ololx.moonshine.util;

import io.github.ololx.moonshine.util.concurrent.atomic.AtomicByteArray;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AtomicBenchmark {

    private static final int AVAILABLE_CPU = Runtime.getRuntime()
            .availableProcessors();

    private static final String BYTE = "byte";

    private static final String INT = "int";

    private static final String LONG = "long";

    private AtomicArray atomicArray;

    @Param(
            {
                    INT,
                    BYTE
            }
    )
    private String typeOfBitSetRealization;

    @Param({"100000"})
    private int sizeOfBitSet;

    @Param({"100"})
    private int countOfSetters;

    @Param({"100"})
    private int countOfGetters;

    public AtomicBenchmark() {}

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(AtomicBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }

    @Setup
    public void setup() {
        switch (typeOfBitSetRealization) {
            case BYTE:
                atomicArray = new AtomicArray() {
                    private AtomicByteArray arr = new AtomicByteArray(sizeOfBitSet);
                    @Override
                    public int get(int index) {
                        return arr.get(index);
                    }

                    @Override
                    public void set(int index, int value) {
                        arr.set(index, (byte) value);
                    }
                };
                break;
            case INT:
                atomicArray = new AtomicArray() {
                    private AtomicIntegerArray arr = new AtomicIntegerArray(sizeOfBitSet);
                    @Override
                    public int get(int index) {
                        return arr.get(index);
                    }

                    @Override
                    public void set(int index, int value) {
                        arr.set(index, value);
                    }
                };
                break;
        }
    }

    @Benchmark
    public void get_set_benchmark(Blackhole blackhole) {
        for (int setBitOpNumber = 0; setBitOpNumber < countOfGetters; setBitOpNumber++) {
            for (int bitNumber = 0; bitNumber < sizeOfBitSet; bitNumber++) {
                blackhole.consume(atomicArray.get(bitNumber));
            }
        }

        for (int getBitOpNumber = 0; getBitOpNumber < countOfSetters; getBitOpNumber++) {
            for (int bitNumber = 0; bitNumber < sizeOfBitSet; bitNumber++) {
                atomicArray.set(bitNumber, 1);
            }
        }

    }

    interface AtomicArray {

        int get(int index);

        void set(int index, int value);
    }
}
