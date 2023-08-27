package io.github.ololx.moonshine.util;

import io.github.ololx.moonshine.util.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.assertTrue;

public class ConcurrentBitsetTest {

    @DataProvider(name = "bitsetData")
    public Object[][] bitsetData() {
        List<Object[]> data = new ArrayList<>();
        Random random = new Random();

        int bitSetSize = random.nextInt(100) + 100;
        List<Integer> unitIndexes = IntStream.range(0, random.nextInt(50))
                .boxed()
                .filter(index -> random.nextBoolean())
                .collect(Collectors.toList());

        data.add(new Object[]{
                new ConcurrentBitSetWithFullSynchronization(bitSetSize),
                unitIndexes,
                bitSetSize
        });
        data.add(new Object[]{
                new ConcurrentBitSetWithGeneralRWLock(bitSetSize),
                unitIndexes,
                bitSetSize
        });
        data.add(new Object[]{
                new ConcurrentBitSetWithSegmentsRWLocks(bitSetSize),
                unitIndexes,
                bitSetSize
        });
        data.add(new Object[]{
            new ConcurrentBitSetWithSegmentsSynchronization(bitSetSize),
            unitIndexes,
            bitSetSize
        });
        data.add(new Object[]{
                new NonBlockingConcurrentBitSet(bitSetSize),
                unitIndexes,
                bitSetSize
        });

        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "bitsetData")
    public void get_whenBitIsTrue_thenReturnTrue(ConcurrentBitSet bitset, List<Integer> unitIndexes, int bitSetSize) {
        unitIndexes.parallelStream()
                .forEach(bitset::set);
        List<Boolean> unitBits = unitIndexes.parallelStream()
                .map(bitset::get)
                .collect(Collectors.toList());
        List<Boolean> nilBits = IntStream.range(0, bitSetSize)
                .parallel()
                .filter(index -> !unitIndexes.contains(index))
                .mapToObj(bitset::get)
                .collect(Collectors.toList());

        assertTrue(unitBits.stream()
                           .allMatch(bit -> bit));
        assertTrue(nilBits.stream()
                           .noneMatch(bit -> bit));
    }

    @Test(dataProvider = "bitsetData", invocationCount = 30)
    public void set_whenSetBit_thenBitIsTrue(ConcurrentBitSet bitset, List<Integer> unitIndexes, int bitSetSize) {
        unitIndexes.parallelStream()
                .forEach(bitset::set);
        List<Boolean> unitBits = unitIndexes.parallelStream()
                .map(bitset::get)
                .collect(Collectors.toList());
        List<Boolean> nilBits = IntStream.range(0, bitSetSize)
                .parallel()
                .filter(index -> !unitIndexes.contains(index))
                .mapToObj(bitset::get)
                .collect(Collectors.toList());

        assertTrue(unitBits.stream()
                           .allMatch(bit -> bit));
        assertTrue(nilBits.stream()
                           .noneMatch(bit -> bit));
    }

    @Test(dataProvider = "bitsetData")
    public void clear_whenBitWasTrue_thenBitIsFalseNow(ConcurrentBitSet bitset, List<Integer> unitIndexes, int bitSetSize) {
        unitIndexes.parallelStream()
                .forEach(bitset::set);
        IntStream.range(0, bitSetSize)
                .parallel()
                .forEach(bitset::clear);

        List<Boolean> unitBits = unitIndexes.parallelStream()
                .map(bitset::get)
                .collect(Collectors.toList());
        List<Boolean> nilBits = IntStream.range(0, bitSetSize)
                .parallel()
                .filter(index -> !unitIndexes.contains(index))
                .mapToObj(bitset::get)
                .collect(Collectors.toList());

        assertTrue(unitBits.stream()
                           .noneMatch(bit -> bit));
        assertTrue(nilBits.stream()
                           .noneMatch(bit -> bit));
    }

    @Test(dataProvider = "bitsetData", invocationCount = 30)
    public void flip_whenBitWasTrue_thenBitIsFalseNowAndViceVersa(ConcurrentBitSet bitset, List<Integer> unitIndexes, int bitSetSize) {
        unitIndexes.parallelStream()
                .forEach(bitset::flip);
        List<Boolean> unitBits = unitIndexes.stream()
                .map(bitset::get)
                .collect(Collectors.toList());
        List<Boolean> nilBits = IntStream.range(0, bitSetSize)
                .parallel()
                .filter(index -> !unitIndexes.contains(index))
                .mapToObj(bitset::get)
                .collect(Collectors.toList());

        assertTrue(nilBits.stream()
                           .noneMatch(bit -> bit));
        assertTrue(unitBits.stream()
                           .allMatch(bit -> bit));
    }
}
