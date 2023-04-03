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

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * @project moonshine
 * @created 01.04.2023 18:46
 * <p>
 * @author Alexander A. Kropotin
 */
public class main {

    public static void main(String[] args) {
        ThreadMemoryUsageMeter threadMemoryUsage = new ThreadMemoryUsageMeter();
        MemoryUsageMeter fullMemoryUsage = new MemoryUsageMeter();

        threadMemoryUsage.start();
        fullMemoryUsage.start();
        StringBuilder stringBuilder = new StringBuilder();
        CompletableFuture.runAsync(() -> {

            for (int i = 0; i < 10000000; i++) {
                stringBuilder.append(i).append("bla");
            }

        }).join();
        threadMemoryUsage.stop();
        fullMemoryUsage.stop();
        System.out.println(stringBuilder.toString().getBytes(StandardCharsets.UTF_8).length);

        System.out.println("RAM usage by tread: " + threadMemoryUsage.getResult());
        System.out.println("RAM usage heap + non head: " + fullMemoryUsage.getMemoryUsage());
    }

}
