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

package stopwatch

import io.github.ololx.moonshine.stopwatch.SimpleStopwatch
import spock.lang.Specification

import java.time.Duration

/**
 * project moonshine
 * created 08.01.2023 08:01
 *
 * @author Alexander A. Kropotin
 */
class SimpleStopwatchSpec extends Specification {

    def "test start method"() {
        given: "a new SimpleStopwatch instance"
        SimpleStopwatch stopwatch = new SimpleStopwatch()

        when: "start method is called"
        stopwatch.start()

        then: "start time should not be STOPPED"
        stopwatch.startTime != SimpleStopwatch.STOPPED
    }

    def "test stop method"() {
        given: "a started SimpleStopwatch instance"
        SimpleStopwatch stopwatch = new SimpleStopwatch()
        stopwatch.start()

        when: "stop method is called"
        stopwatch.stop()

        then: "start time should be STOPPED and elapsed time should be greater than 0"
        stopwatch.startTime == SimpleStopwatch.STOPPED
        stopwatch.elapsedTime > 0
    }

    def "test reset method"() {
        given: "a started and then stopped SimpleStopwatch instance"
        SimpleStopwatch stopwatch = new SimpleStopwatch()
        stopwatch.start()
        stopwatch.stop()

        when: "reset method is called"
        stopwatch.reset()

        then: "start time should be STOPPED and elapsed time should be 0"
        stopwatch.startTime == SimpleStopwatch.STOPPED
        stopwatch.elapsedTime == 0
    }

    def "test elapsed method"() {
        given: "a started SimpleStopwatch instance"
        SimpleStopwatch stopwatch = new SimpleStopwatch()
        stopwatch.start()

        when: "elapsed method is called without stopping the stopwatch"
        Duration elapsed = stopwatch.elapsed()

        then: "elapsed time should be a Duration instance and greater than 0"
        elapsed instanceof Duration
            elapsed.toNanos() > 0
    }
}
