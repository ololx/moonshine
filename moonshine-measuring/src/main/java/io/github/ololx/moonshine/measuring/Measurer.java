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

package io.github.ololx.moonshine.measuring;

/**
 * An interface for measuring some aspect of the application performance, such
 * as elapsed time, CPU load, or memory allocation.
 *
 * @apiNote
 * The {@code Measurer} interface is designed to provide a common abstraction
 * for measuring performance metrics in different contexts. Implementations of
 * this interface can be used to measure elapsed time, CPU load, memory
 * allocation, and other metrics. The result of a measurement is represented by
 * a type parameter {@code R}. Possible types for {@code R} include {@link
 * java.time.Duration} for elapsed time measurements and e.t.c. All
 * measurements are performed between the {@link #start()} and {@link #stop()}
 * methods of the {@code Measurer} instance.
 *
 * @param <R> the type of result returned by the measurement
 *
 * project moonshine
 * created 04.04.2023 22:28
 *
 * @author Alexander A. Kropotin
 */
public interface Measurer<R> {

    /**
     * Starts the measurement process.
     *
     * @return this {@code Measurer} instance
     */
    Measurer<R> start();

    /**
     * Stops the measurement process.
     *
     * @return this {@code Measurer} instance
     */
    Measurer<R> stop();

    /**
     * Returns the result of the measurement.
     *
     * @return the result of the measurement
     */
    R result();
}
