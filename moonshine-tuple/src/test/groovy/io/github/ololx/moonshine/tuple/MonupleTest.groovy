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
class MonupleTest extends Specification {

    @Unroll
    def "new Monuple() - when create with #t0 then tuple contains #t0"() {
        given:
        def tuple = new Monuple<>(t0)

        expect:
        tuple.getT0() == t0

        where:
        t0 << [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Double.MIN_VALUE, String.valueOf(Integer.MAX_VALUE)]
    }

    @Unroll
    def "from() - when create with #elements then tuple contains #elements"() {
        given:
        def tuple = Monuple.from(elements)

        expect:
        tuple.toArray() == elements

        where:
        elements << [
            [Byte.MIN_VALUE] as Object[],
            [Short.MIN_VALUE] as Object[],
            [Float.MIN_VALUE] as Object[],
        ]
    }

    @Unroll
    def "from() - when create with null then throw exception"() {
        when:
        def tuple = Monuple.from(null)

        then:
        thrown(NullPointerException)
    }

    @Unroll
    def "from() - when create with size = #elements.length then throw exception"() {
        when:
        def tuple = Monuple.from()

        then:
        thrown(IllegalArgumentException)

        where:
        elements << [
            [] as Object[]
        ]
    }

    @Unroll
    def "of() - when create with #t0 then tuple contains #t0"() {
        given:
        def tuple = Monuple.of(t0)

        expect:
        tuple.getT0() == t0

        where:
        t0 << [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Double.MIN_VALUE, String.valueOf(Integer.MAX_VALUE)]
    }

    @Unroll
    def "getT0() - when get value by index 0 then return #t0"() {
        given:
        def tuple = new Monuple<>(t0)

        when:
        def actual = tuple.getT0()

        then:
        actual == t0

        where:
        t0 << 1
    }

    @Unroll
    def "get() - when get value by exists #index then return #expected"() {
        given:
        def tuple = new Monuple<>(t0)

        when:
        def actual = tuple.get(index)

        then:
        actual == expected

        where:
        t0 | index | expected
        1  | 0     | 1
        2  | 0     | 2
    }

    @Unroll
    def "get() - when #index is out of bounds [0, 1) then throw exception"() {
        given:
        def tuple = new Monuple<>(t0)

        when:
        tuple.get(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        t0 | index
        1  | -1
        1  | 1
    }

    @Unroll
    def "getOrDefault() - when #index is out of bounds [0, 1) then return #defaultValue"() {
        given:
        def tuple = new Monuple<>(t0)

        when:
        def actual = tuple.getOrDefault(index, defaultValue)

        then:
        actual == defaultValue

        where:
        t0 | index | defaultValue
        1  | -1    | 12
        1  | 2     | 22
    }

    @Unroll
    def "contains() - when tuple<#t0> contains #value then return true"() {
        given:
        def tuple = Monuple.of(t0)

        expect:
        tuple.contains(value)

        where:
        t0 | value
        0  | 0
        1  | 1
    }

    @Unroll
    def "contains() - when tuple<#t0> not contains #value then return false"() {
        given:
        def tuple = Monuple.of(t0)

        expect:
        !tuple.contains(value)

        where:
        t0 | value
        1  | 100
        1  | 100
    }

    @Unroll
    def "indexOf() - when tuple<#t0> contains #value then return first #index of #value"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.indexOf(value)

        then:
        actual == index

        where:
        t0 | value | index
        1  | 1     | 0
        2  | 2     | 0
        1  | 3     | -1
    }

    @Unroll
    def "lastIndexOf() - when tuple<#t0> contains #value then return last #index of #value"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.lastIndexOf(value)

        then:
        actual == index

        where:
        t0 | value | index
        1  | 1     | 0
        2  | 2     | 0
        1  | 3     | -1
    }

    @Unroll
    def "size() - when tuple<#t0> created then return their size"() {
        given:
        def tuple = Monuple.of(0)

        expect:
        tuple.size() == 1
    }

    @Unroll
    def "toArray() - when tuple<#t0> created then return an array with tuple #elements"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.toArray()

        then:
        actual == elements

        where:
        t0 | elements
        0  | [0]
        1  | [1]
        2  | [2]
    }

    @Unroll
    def "toList() - when tuple<#t0> created then return a list with tuple #elements"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.toList()

        then:
        actual == elements

        where:
        t0 | elements
        0  | [0]
        1  | [1]
        2  | [2]
    }

    @Unroll
    def "toSet() - when tuple<#t0> created then return a set with unique tuple #elements"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.toSet()

        then:
        actual == elements

        where:
        t0 | elements
        0  | [0] as Set
        1  | [1] as Set
    }

    @Unroll
    def "stream() - when tuple<#t0> created then return a stream with tuple #elements"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.stream()

        then:
        actual.toArray() == elements

        where:
        t0 | elements
        0  | [0]
        1  | [1]
        2  | [2]
    }

    @Unroll
    def "spliterator() - when tuple created then return a spliterator with tuple size"() {
        given:
        def tuple = Monuple.of(0)

        when:
        def actual = tuple.spliterator()

        then:
        actual.estimateSize() == tuple.size()
        (actual.characteristics() ^ SUBSIZED) == (SIZED | IMMUTABLE | ORDERED)
    }

    @Unroll
    def "iterator() - when tuple created then return a iterator with tuple size"() {
        given:
        def tuple = Monuple.of(0)

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
    def "toString() - when tuple<#t0> created then return an expected #stringRepresentation"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.toString()

        then:
        actual == stringRepresentation

        where:
        t0 | stringRepresentation
        0  | "(0)"
        1  | "(1)"
        2  | "(2)"
    }

    @Unroll
    def "convert() - when tuple<#t0> created then convert to #expected int by function t.getT0() * 2"() {
        given:
        def tuple = Monuple.of(t0)

        when:
        def actual = tuple.convert {((Monuple<?>) it).getT0() * 2}

        then:
        actual == expected

        where:
        t0 | expected
        1  | 2
        2  | 4
    }

    def "equalsHashCode() - verify equals and hashCode contracts"() {
        expect:
        EqualsVerifier.forClass(Monuple.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .report()
            .successful
    }
}
