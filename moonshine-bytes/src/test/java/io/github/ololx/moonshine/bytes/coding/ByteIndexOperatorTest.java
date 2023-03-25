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

package io.github.ololx.moonshine.bytes.coding;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
public class ByteIndexOperatorTest {

    @DataProvider
    static Object[][] providesByteOrderOperatorAndIndexRange() {
        return new Object[][] {
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index;
                            }
                        },
                        new int[] {0, 1, 2, 3},
                        new int[] {0, 1, 2, 3}
                },
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return 3 - index;
                            }
                        },
                        new int[] {0, 1, 2, 3},
                        new int[] {3, 2, 1, 0}
                }
        };
    }

    @Test(dataProvider = "providesByteOrderOperatorAndIndexRange")
    public void apply_whenApplyToIndex_thenReturenExpected(ByteIndexOperator byteOrder,
                                                           int[] origin,
                                                           int[] expected) {
        //When
        // decode apply byte order operator to index
        //Then
        // return expected
        IntStream.range(0, origin.length)
                .forEach(index -> assertEquals(byteOrder.apply(origin[index]), expected[index]));
    }

    @Test(invocationCount = 10)
    public void identity_whenExecuteIdentityByteIndexOperator_thenByteIndexOperatorReturnInput() {
        //Given
        // the identity byte index operator
        ByteIndexOperator byteIndexOperator = ByteIndexOperator.identity();

        //When
        // get apply for indexes in range from 0 till 10
        //Then
        // operated index equals input index
        IntStream.range(0, 10)
                .forEach(index -> assertEquals(byteIndexOperator.apply(index), index));
    }
}
