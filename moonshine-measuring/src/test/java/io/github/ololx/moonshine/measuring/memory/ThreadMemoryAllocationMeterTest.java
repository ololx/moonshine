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

package io.github.ololx.moonshine.measuring.memory;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * project moonshine
 * created 07.04.2023 21:06
 *
 * @author Alexander A. Kropotin
 */
public class ThreadMemoryAllocationMeterTest {

    @Test
    public void startAndStopAndResult_whenMeasurerWasActivated_thenReturnMeasuringResult() {
        //Given
        // the memory meter
        ThreadMemoryAllocationMeter meter = new ThreadMemoryAllocationMeter();

        //When
        // start measurer
        meter.start();
        // create new array with 1000 int
        int[] digits = new int[1_000];
        // stop measurer
        meter.stop();

        //Then
        // allocated memory was more than 0
        assertTrue(meter.result().toBytes() >= 0);
    }
}
