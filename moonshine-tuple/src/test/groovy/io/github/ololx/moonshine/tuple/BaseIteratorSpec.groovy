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

/**
 * project moonshine
 * created 04.01.2023 09:34
 *
 * @author Alexander A. Kropotin
 */
class BaseIteratorSpec extends Specification {

    def "hasNext() - when iteration has more values then return true"() {
        setup:
        Iterator<Object> iterator = tuple.iterator()
        int iteration = 0

        when:
        while (iterator.hasNext()) {
            iteration++
            iterator.next()
        }

        then:
        iteration == tuple.size()

        where:
        tuple << [new EmptyTuple(), new Monuple<>(Byte.MIN_VALUE), new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)]
    }

    def "next() - when iteration has more values then return value and increment iteration"() {
        setup:
        Iterator<Object> iterator = tuple.iterator()

        when:
        for (int iteration = 0; iteration < tuple.size(); iteration++) {
            iterator.next()
        }

        then:
        !iterator.hasNext()

        where:
        tuple << [new EmptyTuple(), new Monuple<>(Byte.MIN_VALUE), new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)]
    }

    def "forEachRemaining() - when iteration has values then add to list each remaining"() {
        setup:
        Iterator<Object> iterator = tuple.iterator()
        List<Object> iterationValues = new ArrayList<>()

        when:
        iterator.forEachRemaining(iterationValues::add)

        then:
        tuple.size() == iterationValues.size()

        where:
        tuple << [new EmptyTuple(), new Monuple<>(Byte.MIN_VALUE), new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)]
    }

    def "next() - when iteration does not have more values then throw exception"() {
        setup:
        Iterator<Object> iterator = tuple.iterator()
        for (int iteration = 0; iteration < tuple.size(); iteration++) {
            iterator.next()
        }
        !iterator.hasNext()

        when:
        iterator.next()

        then:
        thrown(NoSuchElementException)

        where:
        tuple << [new EmptyTuple(), new Monuple<>(Byte.MIN_VALUE), new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)]
    }

    def "remove() - when try to remove last element from tuple then throw exception"() {
        setup:
        Iterator<Object> iterator = tuple.iterator()

        when:
        iterator.remove()

        then:
        thrown(UnsupportedOperationException)

        where:
        tuple << [new EmptyTuple(), new Monuple<>(Byte.MIN_VALUE), new Couple<>(Byte.MIN_VALUE, Short.MIN_VALUE)]
    }
}
