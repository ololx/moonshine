/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.stopwatch;

import org.testng.annotations.Test;

import java.time.Duration;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * project moonshine
 * created 08.01.2023 08:01
 *
 * @author Alexander A. Kropotin
 */
public class SimpleStopwatchTest {

    @Test
    void new_whenCreateStopwatch_thenElapsedTimeIs0() {
        //When
        // create new stopwatch
        final Stopwatch stopwatch = new SimpleStopwatch();

        //Then
        // elapsed time is 0
        assertEquals(stopwatch.elapsed().toNanos(), 0);
    }

    @Test
    void stop_whenStopwatchIsNotRunning_thenDoesNotCalsNewElapsedTime() {
        //Given
        // the stop watch which is not running
        final Stopwatch stopwatch = new SimpleStopwatch();

        //When
        // stop stopwatch a several times
        IntStream.range(0, 10).forEach(click -> stopwatch.stop());

        //Then
        // elapsed time is 0
        assertEquals(stopwatch.elapsed().toNanos(), 0);
    }

    @Test
    void start_whenStopwatchIsRunning_thenDoesNotStartEveryTime() {
        //Given
        // the stop watch which is running
        // and current elapsed time
        final Stopwatch stopwatch = new SimpleStopwatch().start();
        final Duration currentElapsedTime = stopwatch.elapsed();

        //When
        // start stopwatch a several times and wait 100 ms
        IntStream.range(0, 10).forEach(click -> {
            stopwatch.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        //Then
        // new elapsed time in nano is more than previous elapsed time in nano
        assertTrue(stopwatch.stop().elapsed().toNanos() > currentElapsedTime.toNanos());
    }

    @Test
    void elapsed_whenStopwatchIsRunning_thenCalcNewElapsedTime() {
        //Given
        // the stop watch which is running
        // and current elapsed time
        final Stopwatch stopwatch = new SimpleStopwatch().start();
        final Duration currentElapsedTime = stopwatch.elapsed();

        //When
        // sleep 1 sec and get elapsed time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Then
        // new elapsed time in nano is more than previous elapsed time in nano
        assertTrue(stopwatch.stop().elapsed().toNanos() > currentElapsedTime.toNanos());
    }

    @Test
    void reset_whenStopwatchHaveBeenRunningBefore_thenElapsedTimeSetTo0() {
        //Given
        // the stop watch which have been running before
        // and current elapsed time
        final Stopwatch stopwatch = new SimpleStopwatch().start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final Duration currentElapsedTime = stopwatch.stop().elapsed();
        assertTrue(currentElapsedTime.toNanos() > 0);

        //When
        // reset stopwatch
        stopwatch.reset();

        //Then
        // new elapsed time is 0
        assertTrue(stopwatch.elapsed().isZero());
    }
}
