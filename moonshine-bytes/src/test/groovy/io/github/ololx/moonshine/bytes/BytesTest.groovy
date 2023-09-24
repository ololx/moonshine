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

package io.github.ololx.moonshine.bytes


import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
class BytesTest extends Specification {

    @Shared
    ByteIndexOperator identity = ByteIndexOperator.identity()

    @Shared
    ByteIndexOperator reverse = {int index -> 7 - index}

    @Shared
    ByteIndexOperator alternateReverse = {int index ->
        index % 2 == 0 ? 7 - (index + 1) : 7 - (index - 1)
    }

    @Shared
    ByteIndexOperator halfReverse = {int index -> 4 - index}

    @Unroll
    def "reorder - when reorder byte array, it returns array in new order"() {
        expect:
        Bytes.reorder(originArray, originOrder, newOrder) == expected

        where:
        originOrder      | newOrder         | originArray                        | expected
        identity         | reverse          | [0, 1, 2, 3, 4, 5, 6, 7] as byte[] | [7, 6, 5, 4, 3, 2, 1, 0] as byte[]
        reverse          | identity         | [7, 6, 5, 4, 3, 2, 1, 0] as byte[] | [0, 1, 2, 3, 4, 5, 6, 7] as byte[]
        identity         | alternateReverse | [0, 1, 2, 3, 4, 5, 6, 7] as byte[] | [6, 7, 4, 5, 2, 3, 0, 1] as byte[]
        alternateReverse | identity         | [6, 7, 4, 5, 2, 3, 0, 1] as byte[] | [0, 1, 2, 3, 4, 5, 6, 7] as byte[]
        identity         | halfReverse      | [0, 1, 2, 3, 4] as byte[]          | [4, 3, 2, 1, 0] as byte[]
    }

    @Unroll
    def "reorder - when reorder byte array with offset, it returns array in new order"() {
        expect:
        Bytes.reorder(originArray, offset, originOrder, newOrder) == expected

        where:
        originOrder | newOrder    | offset | originArray                        | expected
        identity    | halfReverse | 3      | [0, 0, 0, 0, 1, 2, 3, 4] as byte[] | [0, 0, 0, 4, 3, 2, 1, 0] as byte[]
    }
}
