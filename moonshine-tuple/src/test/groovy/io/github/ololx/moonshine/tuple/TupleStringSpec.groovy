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

package io.github.ololx.moonshine.tuple


import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 29.01.2023 14:14
 *
 * @author Alexander A. Kropotin
 */
class TupleStringSpec extends Specification {

    @Unroll
    def "format() - when #tuple has values then return string with #expected"() {
        expect:
        TupleString.format(tuple) == expected

        where:
        tuple                              | expected
        new EmptyTuple()                   | "()"
        Monuple.of(1)                      | "(1)"
        Couple.of(1, 2)                    | "(1, 2)"
        Triple.of(1, 2, 3)                 | "(1, 2, 3)"
        Quadruple.of(1, 2, 3, 4)           | "(1, 2, 3, 4)"
        Quintuple.of(1, 2, 3, 4, 5)        | "(1, 2, 3, 4, 5)"
        Sextuple.of(1, 2, 3, 4, 5, 6)      | "(1, 2, 3, 4, 5, 6)"
        Septuple.of(1, 2, 3, 4, 5, 6, 7)   | "(1, 2, 3, 4, 5, 6, 7)"
        Octuple.of(1, 2, 3, 4, 5, 6, 7, 8) | "(1, 2, 3, 4, 5, 6, 7, 8)"
    }
}
