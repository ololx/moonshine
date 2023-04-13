/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <br/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.measuring.cpu;

import io.github.ololx.moonshine.measuring.cpu.ProcessCPULoadMeter;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;

/**
 * project moonshine
 * created 07.04.2023 21:06
 *
 * @author Alexander A. Kropotin
 */
public class ProcessCPULoadMeterTest {

    @Test
    public void startAndStopAndResult_whenMeasurerWasActivated_thenReturnMeasuringResult() {
        //Given
        // the memory meter

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    ProcessCPULoadMeter meter = new ProcessCPULoadMeter();

                    meter.start();
                    //When
                    // start measurer

                    // create new array with 1_000_000 int
                    int[] array = new int[999999999];
                    for (int i = 0; i < array.length; i++) {
                        array[i] = i;
                    }
                    // stop measurer
                    meter.stop();

                    System.out.println(meter.result());
                }),
                CompletableFuture.runAsync(() -> {
                    ProcessCPULoadMeter meter = new ProcessCPULoadMeter();

                    meter.start();
                    //When
                    // start measurer

                    // create new array with 1_000_000 int
                    int[] array = new int[9_000_000];
                    for (int i = 0; i < array.length; i++) {
                        array[i] = i;
                    }
                    // stop measurer
                    meter.stop();

                    System.out.println(meter.result());
                }),
                CompletableFuture.runAsync(() -> {
                    ProcessCPULoadMeter meter = new ProcessCPULoadMeter();

                    meter.start();
                    //When
                    // start measurer

                    // create new array with 1_000_000 int
                    int[] array = new int[9_000_000];
                    for (int i = 0; i < array.length; i++) {
                        array[i] = i;
                    }
                    // stop measurer
                    meter.stop();

                    System.out.println(meter.result());
                })
        ).join();
        CompletableFuture.runAsync(() -> {
            ProcessCPULoadMeter meter = new ProcessCPULoadMeter();

            meter.start();
            //When
            // start measurer

            // create new array with 1_000_000 int
            int[] array = new int[9_000_000];
            for (int i = 0; i < array.length; i++) {
                array[i] = i;
            }
            // stop measurer
            meter.stop();

            System.out.println(meter.result());
        }).join();

        //Then
        // allocated memory is more than 0
        //assertEquals(meter.result().toBytes(), 0);
    }

    @Test
    public void start2AndStopAndResult_whenMeasurerWasActivated_thenReturnMeasuringResult() {
        //Given
        // the memory meter
        ProcessCPULoadMeter meters = new ProcessCPULoadMeter();
        meters.start();
        //When
        // start measurer

        // create new array with 1_000_000 int
        int numCore = Runtime.getRuntime().availableProcessors();
        int numThreadsPerCore = 100;
        double load = 1;
        final long duration = 9999;
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }
        // stop measurer
        meters.stop();
        System.out.println(meters.result() + " | " + meters.result() * Runtime.getRuntime().availableProcessors());
    }

    private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {

            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
