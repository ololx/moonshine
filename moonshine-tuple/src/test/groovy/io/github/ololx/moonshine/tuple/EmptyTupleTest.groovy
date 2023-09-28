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

import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Spliterator.*

/**
 * project moonshine
 * created 03.01.2023 12:43
 *
 * @author Alexander A. Kropotin
 */
class EmptyTupleTest extends Specification {

    @Unroll
    def "get() - #index always is out of bounds and thus throw exception"() {
        given:
        def tuple = new EmptyTuple()

        when:
        tuple.get(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        index << [-1, 0, 1, 2, 3]
    }

    @Unroll
    def "getOrDefault() - #index always is out of bounds and thus return #defaultValue"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.getOrDefault(index, defaultValue)

        then:
        actual == defaultValue

        where:
        index | defaultValue
        -1    | 12
        0     | 22
    }

    @Unroll
    def "contains() - tuple always is empty and thus return false"() {
        given:
        def tuple = new EmptyTuple()

        expect:
        !tuple.contains(value)

        where:
        value << [-1, 0, 1, 2, 3]
    }

    @Unroll
    def "indexOf() - tuple always is empty and thus return -1"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.indexOf(value)

        then:
        actual == index

        where:
        value | index
        1     | -1
        2     | -1
        3     | -1
    }

    @Unroll
    def "lastIndexOf() - tuple always is empty and thus return -1"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.lastIndexOf(value)

        then:
        actual == index

        where:
        value | index
        1     | -1
        2     | -1
        3     | -1
    }

    @Unroll
    def "size() - tuple always is empty and thus return 0"() {
        given:
        def tuple = new EmptyTuple()

        expect:
        tuple.size() == 0
    }

    @Unroll
    def "toArray() - tuple always is empty and thus return no one elements"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.toArray()

        then:
        actual == elements

        where:
        elements << [[]]
    }

    @Unroll
    def "toList() - tuple always is empty and thus return no one elements"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.toList()

        then:
        actual == elements

        where:
        elements << [[]]
    }

    @Unroll
    def "toSet() - tuple always is empty and thus return no one elements"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.toSet()

        then:
        actual == elements

        where:
        elements << [[] as Set]
    }

    @Unroll
    def "stream() - tuple always is empty and thus return no one elements"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.stream()

        then:
        actual.toArray() == elements

        where:
        elements << [[]]
    }

    @Unroll
    def "spliterator() - tuple always is empty and thus return an empty iterator"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.spliterator()

        then:
        actual.estimateSize() == tuple.size()
        (actual.characteristics()  ^ SUBSIZED) == (SIZED | IMMUTABLE | ORDERED)
    }

    @Unroll
    def "iterator() - tuple always is empty and thus return an empty iterator"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.iterator()

        then:
        def iteratedElements = []

        while (actual.hasNext()) {
            iteratedElements.add(actual.next())
        }

        iteratedElements == tuple.toArray()
    }

    @Unroll
    def "toString() - tuple always is empty and thus return an empty string representation '(∅)'"() {
        given:
        def tuple = new EmptyTuple()

        expect:
        tuple.toString() == "(∅)"
    }

    @Unroll
    def "convert() - when tuple created then convert to #expected int by function it -> #expected"() {
        given:
        def tuple = new EmptyTuple()

        when:
        def actual = tuple.convert {it -> expected}

        then:
        actual == expected

        where:
        expected << [2, 4]
    }

    def "equalsHashCode() - verify equals and hashCode contracts"() {
        expect:
        EqualsVerifier.forClass(EmptyTuple.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .report()
            .successful
    }
}
