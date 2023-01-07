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

import java.time.Duration;

/**
 * An object that provides a set of methods and properties that can be used
 * to accurately measure elapsed time.
 *
 * The {@code Stopwatch} interface provides four methods:
 * <ul>
 *     <li>
 *         {@code start} - start the stopwatch, start at first or after stop
 *         usage (from idle state).
 *     </li>
 *     <li>
 *         {@code stop} - stop the stopwatch and calculate elapsed time,
 *         can be used like pause state
 *     </li>
 *     <li>
 *         {@code reset} - reset the stopwatch and clear elapsed time to 0
 *     </li>
 *     <li>
 *         {@code elapsed} - return the elapsed time before current moment.
 *     </li>
 * </ul>
 *
 * project moonshine
 * created 06.01.2023 19:59
 *
 * @author Alexander A. Kropotin
 */
public interface Stopwatch {

    /**
     * Starts stopwatch if it wasn't started yet.
     *
     * @return this {@code Stopwatch} instance
     */
    Stopwatch start();

    /**
     * Stop stopwatch if it wasn't stopped yet.
     *
     * @return this {@code Stopwatch} instance
     */
    Stopwatch stop();

    /**
     * Reset stopwatch and set elapsed time to 0
     *
     * @return this {@code Stopwatch} instance
     */
    Stopwatch reset();

    /**
     * Returns elapsed time
     *
     * @return this {@code Stopwatch} instance
     */
    Duration elapsed();
}
