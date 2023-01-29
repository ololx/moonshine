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

package io.github.ololx.moonshine.stopwatch;

import java.time.Duration;

/**
 **
 * An object that provides a set of methods and properties that can be used
 * to accurately measure elapsed time.
 *
 * The {@code SimpleStopwatch} class implements {@code Stopwatch} interface
 * and provides all its behaviour.
 *
 * <b>Note</b>
 * This class is NOT thread-safe!
 * However, most likely you will be using it as local scope variable in methods.
 *
 * project moonshine
 * created 07.01.2023 09:47
 *
 * @author Alexander A. Kropotin
 */
public class SimpleStopwatch implements Stopwatch {

    /**
     * The const of state "is not running"
     */
    private static final long STOPPED = -1;

    /**
     * The start time when stopwatch was running
     */
    protected long startTime;

    /**
     * The elapsed time before clear
     */
    protected long elapsedTime;

    /**
     * Create new SimpleStopwatch which is not running
     */
    public SimpleStopwatch() {
        this.startTime = STOPPED;
    }

    /**
     * Starts stopwatch if it wasn't started yet.
     *
     * @return this {@code Stopwatch} instance
     */
    @Override
    public SimpleStopwatch start() {
        if (this.startTime == STOPPED) {
            this.startTime = System.nanoTime();
        }

        return this;
    }

    /**
     * Stop stopwatch if it wasn't stopped yet.
     *
     * @return this {@code Stopwatch} instance
     */
    @Override
    public SimpleStopwatch stop() {
        if (this.startTime != STOPPED) {
            this.elapsedTime += System.nanoTime() - startTime;
            this.startTime = STOPPED;
        }

        return this;
    }

    /**
     * Reset stopwatch and set elapsed time to 0
     *
     * @return this {@code Stopwatch} instance
     */
    @Override
    public SimpleStopwatch reset() {
        this.startTime = STOPPED;
        this.elapsedTime = 0;

        return this;
    }

    /**
     * Returns elapsed time
     *
     * @return this {@code Stopwatch} instance
     */
    @Override
    public Duration elapsed() {
        long lastElapsedTime = this.elapsedTime;
        if (startTime != STOPPED) {
            lastElapsedTime += System.nanoTime() - startTime;
        }

        return Duration.ofNanos(lastElapsedTime);
    }
}
