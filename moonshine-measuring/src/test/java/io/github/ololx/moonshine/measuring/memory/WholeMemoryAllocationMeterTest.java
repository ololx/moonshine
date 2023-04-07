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

/**
 * project moonshine
 * created 07.04.2023 21:06
 *
 * @author Alexander A. Kropotin
 */
public class WholeMemoryAllocationMeterTest {

    @Test
    public void startAndStopAndResult_whenMeasurerWasActivated_thenReturnMeasuringResult() {
        //Given
        // the memory meter
        WholeMemoryAllocationMeter meter = new WholeMemoryAllocationMeter();

        //When
        // start measurer
        meter.start();
        // create new array with 1000 int
        int[] digits = new int[1_000_000];
        // stop measurer
        meter.stop();

        //Then
        // allocated memory was more than digits size 4_000_000 bytes (1_000_000 * 4)
        assertEquals(meter.result().compareTo(Memory.ofBytes(4_000_000)), 1);
    }
}