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

package io.github.ololx.moonshine.bytes.coding


import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
class ByteIndexOperatorTest extends Specification {

    @Unroll
    def "apply should return expected index"() {
        expect:
        byteOrder.apply(oldOrder[1]) == newOrder[1]

        where:
        byteOrder                    | oldOrder     | newOrder
        ByteIndexOperator.identity() | [1, 2, 3, 4] | [1, 2, 3, 4]
    }
}
