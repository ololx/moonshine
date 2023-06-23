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

package io.github.ololx.moonshine.bytes;

import io.github.ololx.moonshine.bytes.Bytes;
import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
public class BytesTest {

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
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return 7 - index;
                            }
                        },
                        new byte[] {0, 1, 2, 3, 4, 5, 6, 7},
                        new byte[] {7, 6, 5, 4, 3, 2, 1, 0}
                },
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return 7 - index;
                            }
                        },
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index;
                            }
                        },
                        new byte[] {7, 6, 5, 4, 3, 2, 1, 0},
                        new byte[] {0, 1, 2, 3, 4, 5, 6, 7}
                },
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index;
                            }
                        },
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index % 2 == 0
                                        ? 7 - (index + 1)
                                        : 7 - (index - 1);
                            }
                        },
                        new byte[] {0, 1, 2, 3, 4, 5, 6, 7},
                        new byte[] {6, 7, 4, 5, 2, 3, 0, 1}
                },
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index % 2 == 0
                                        ? 7 - (index + 1)
                                        : 7 - (index - 1);
                            }
                        },
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index;
                            }
                        },
                        new byte[] {6, 7, 4, 5, 2, 3, 0, 1},
                        new byte[] {0, 1, 2, 3, 4, 5, 6, 7}
                },
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index;
                            }
                        },
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return 4 - index;
                            }
                        },
                        new byte[] {0, 1, 2, 3, 4},
                        new byte[] {4, 3, 2, 1, 0}
                },
        };
    }

    @Test(dataProvider = "providesByteOrderOperatorAndIndexRange")
    public void reorder_whenReorderByteArray_thenReturnArrayInNewOrder(ByteIndexOperator originOrder,
                                                                       ByteIndexOperator newOrder,
                                                                       byte[] originArray,
                                                                       byte[] expected) {
        //When
        // reorder byte array from origin order to new
        byte[] reorderedByteArray = Bytes.reorder(originArray, originOrder, newOrder);

        //Then
        // reordered array equals expected
        assertEquals(reorderedByteArray, expected);
    }

    @DataProvider
    static Object[][] providesByteOrderOperatorAndIndexRangeAndOffsets() {
        return new Object[][] {
                {
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return index;
                            }
                        },
                        new ByteIndexOperator() {

                            @Override
                            public int apply(int index) {
                                return 4 - index;
                            }
                        },
                        3,
                        new byte[] {0, 0, 0, 0, 1, 2, 3, 4},
                        new byte[] {0, 0, 0, 4, 3, 2, 1, 0}
                },
        };
    }

    @Test(dataProvider = "providesByteOrderOperatorAndIndexRangeAndOffsets")
    public void reorder_whenReorderByteArrayWithOffset_thenReturnArrayInNewOrder(ByteIndexOperator originOrder,
                                                                                 ByteIndexOperator newOrder,
                                                                                 int offset,
                                                                                 byte[] originArray,
                                                                                 byte[] expected) {
        //When
        // reorder byte array from origin order to new
        byte[] reorderedByteArray = Bytes.reorder(originArray, offset, originOrder, newOrder);

        System.out.println(Arrays.toString(reorderedByteArray) + "|" + Arrays.toString(expected));
        //Then
        // reordered array equals expected
        assertEquals(reorderedByteArray, expected);
    }
}
