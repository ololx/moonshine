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
package io.github.com.ololx.moonshine.measuring;

import java.util.concurrent.CompletableFuture;

/**
 * project moonshine
 * created 01.04.2023 18:46
 *
 * @author Alexander A. Kropotin
 */
public class main {

    public static void main(String[] args) {
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    ThreadMemoryUsageMeter threadMemoryUsage = new ThreadMemoryUsageMeter();
                    threadMemoryUsage.start();

                    int[] ints = new int[100000000];
                    for (int i = 0; i < ints.length; i++) {
                        ints[i] = i;
                    }

                    threadMemoryUsage.stop();
                    System.out.println("1 - RAM usage by tread: " + threadMemoryUsage.result());
                }),
                CompletableFuture.runAsync(() -> {
                    MemoryUsageMeter fullMemoryUsage = new MemoryUsageMeter();
                    fullMemoryUsage.start();

                    int[] ints = new int[100000000];
                    for (int i = 0; i < ints.length; i++) {
                        ints[i] = i;
                    }

                    fullMemoryUsage.stop();
                    System.out.println("2 - RAM usage heap + non head: " + fullMemoryUsage.result());
                }),
                CompletableFuture.runAsync(() -> {
                    MemoryUsageMeter fullMemoryUsage = new MemoryUsageMeter();
                    fullMemoryUsage.start();

                    int[] ints = new int[100000000];
                    for (int i = 0; i < ints.length; i++) {
                        ints[i] = i;
                    }

                    fullMemoryUsage.stop();
                    System.out.println("3 - RAM usage heap + non head: " + fullMemoryUsage.result());
                }),
                CompletableFuture.runAsync(() -> {
                    ThreadMemoryUsageMeter threadMemoryUsage = new ThreadMemoryUsageMeter();
                    threadMemoryUsage.start();

                    int[] ints = new int[100000000];
                    for (int i = 0; i < ints.length; i++) {
                        ints[i] = i;
                    }

                    threadMemoryUsage.stop();
                    System.out.println("4 - RAM usage by tread: " + threadMemoryUsage.result());
                })
        ).join();
    }
}
