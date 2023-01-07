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
 * project moonshine
 * created 07.01.2023 09:47
 *
 * @author Alexander A. Kropotin
 */
public class SimpleStopwatch implements Stopwatch {

    protected long startTime;

    protected long stopTime;

    protected long elapsedTime;

    @Override
    public void start() {
        if (this.startTime == 0) {
            this.startTime = System.nanoTime();
            this.elapsedTime = 0L;
        }
    }

    @Override
    public void stop() {
        if (this.startTime != 0) {
            this.startTime = 0;
        }
    }

    @Override
    public void reset() {
        this.startTime = 0L;
        this.stopTime = 0L;
        this.elapsedTime = 0L;
    }

    @Override
    public Duration elapsed() {
        long elapsedTime = this.elapsedTime;
        if (elapsedTime == 0L) {
            elapsedTime = System.nanoTime();
        }

        return Duration.ofNanos(elapsedTime);
    }
}
