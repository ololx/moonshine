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
class QuadrupleTest extends Specification {

    @Unroll
    def "new Quadruple() - when create with #t0, #t1, #t2, #t3 then tuple contains #t0, #t1, #t2, #t3"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        expect:
        tuple.getT0() == t0
        tuple.getT1() == t1
        tuple.getT2() == t2
        tuple.getT3() == t3

        where:
        t0 << [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE]
        t1 << [Character.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE]
        t2 << [Double.MAX_VALUE, String.valueOf(Byte.MAX_VALUE), Byte.MAX_VALUE, Float.MAX_VALUE]
        t3 << [Long.MIN_VALUE, Long.MAX_VALUE, "Test String", null]
    }

    @Unroll
    def "from() - when create with #elements then tuple contains #elements"() {
        given:
        def tuple = Quadruple.from(elements)

        expect:
        tuple.toArray() == elements

        where:
        elements << [
            [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE] as Object[],
            [Short.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Byte.MIN_VALUE] as Object[],
        ]
    }

    @Unroll
    def "from() - when create with null then throw exception"() {
        when:
        def tuple = Quadruple.from(null)

        then:
        thrown(NullPointerException)
    }

    @Unroll
    def "from() - when create with size = #elements.length then throw exception"() {
        when:
        def tuple = Quadruple.from()

        then:
        thrown(IllegalArgumentException)

        where:
        elements << [
            new Object[0],
            new Object[1],
            new Object[2],
            new Object[3]
        ]
    }

    @Unroll
    def "of() - when create with #t0, #t1, #t2, #t3 then tuple contains #t0, #t1, #t2, #t3"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        expect:
        tuple.getT0() == t0
        tuple.getT1() == t1
        tuple.getT2() == t2
        tuple.getT3() == t3

        where:
        t0 << [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE]
        t1 << [Character.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE]
        t2 << [Double.MAX_VALUE, String.valueOf(Byte.MAX_VALUE), Byte.MAX_VALUE, Float.MAX_VALUE]
        t3 << [Long.MIN_VALUE, Long.MAX_VALUE, "Test String", null]
    }

    @Unroll
    def "getT0() - when get value by index 0 then return #t0"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        def actual = tuple.getT0()

        then:
        actual == t0

        where:
        t0 | t1 | t2 | t3
        1  | 2  | 3  | 4
    }

    @Unroll
    def "getT1() - when get value by index 1 then return #t1"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        def actual = tuple.getT1()

        then:
        actual == t1

        where:
        t0 | t1 | t2 | t3
        1  | 2  | 3  | 4
    }

    @Unroll
    def "getT2() - when get value by index 2 then return #t2"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        def actual = tuple.getT2()

        then:
        actual == t2

        where:
        t0 | t1 | t2 | t3
        1  | 2  | 3  | 4
    }

    @Unroll
    def "getT3() - when get value by index 3 then return #t3"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        def actual = tuple.getT3()

        then:
        actual == t3

        where:
        t0 | t1 | t2 | t3
        1  | 2  | 3  | 4
    }

    @Unroll
    def "get() - when get value by exists #index then return #expected"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        def actual = tuple.get(index)

        then:
        actual == expected

        where:
        t0 | t1 | t2 | t3 | index | expected
        1  | 2  | 3  | 4  | 0     | 1
        1  | 2  | 3  | 4  | 1     | 2
        1  | 2  | 3  | 4  | 2     | 3
        1  | 2  | 3  | 4  | 3     | 4
    }

    @Unroll
    def "get() - when #index is out of bounds [0, 3) then throw exception"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        tuple.get(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        t0 | t1 | t2 | t3 | index
        1  | 2  | 3  | 4  | -1
        1  | 2  | 3  | 4  | 4
    }

    @Unroll
    def "getOrDefault() - when #index is out of bounds [0, 3) then return #defaultValue"() {
        given:
        def tuple = new Quadruple<>(t0, t1, t2, t3)

        when:
        def actual = tuple.getOrDefault(index, defaultValue)

        then:
        actual == defaultValue

        where:
        t0 | t1 | t2 | t3 | index | defaultValue
        1  | 2  | 3  | 4  | -1    | 12
        1  | 2  | 3  | 4  | 4     | 22
    }

    @Unroll
    def "contains() - when tuple<#t0, #t1, #t2, #t3> contains #value then return true"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        expect:
        tuple.contains(value)

        where:
        t0 | t1 | t2 | t3 | value
        1  | 2  | 3  | 4  | 1
        1  | 2  | 3  | 4  | 2
        1  | 2  | 3  | 4  | 3
        1  | 2  | 3  | 4  | 4
    }

    @Unroll
    def "contains() - when tuple<#t0, #t1, #t2, #t3> not contains #value then return false"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        expect:
        !tuple.contains(value)

        where:
        t0 | t1 | t2 | t3 | value
        1  | 2  | 3  | 4  | 100
        1  | 2  | 3  | 4  | 101
    }

    @Unroll
    def "indexOf() - when tuple<#t0, #t1, #t2, #t3> contains #value then return first #index of #value"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.indexOf(value)

        then:
        actual == index

        where:
        t0 | t1 | t2 | t3 | value | index
        1  | 2  | 3  | 4  | 1     | 0
        1  | 2  | 3  | 4  | 2     | 1
        1  | 2  | 3  | 4  | 3     | 2
        1  | 2  | 3  | 4  | 4     | 3
        1  | 1  | 2  | 3  | 1     | 0
        2  | 2  | 3  | 4  | 2     | 0
        1  | 2  | 3  | 4  | 5     | -1
    }

    @Unroll
    def "lastIndexOf() - when tuple<#t0, #t1, #t2, #t3> contains #value then return last #index of #value"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.lastIndexOf(value)

        then:
        actual == index

        where:
        t0 | t1 | t2 | t3 | value | index
        1  | 2  | 3  | 4  | 1     | 0
        1  | 2  | 3  | 4  | 2     | 1
        1  | 2  | 3  | 4  | 3     | 2
        1  | 2  | 3  | 4  | 4     | 3
        1  | 1  | 2  | 3  | 1     | 1
        2  | 2  | 2  | 4  | 2     | 2
        1  | 2  | 3  | 4  | 5     | -1
    }

    @Unroll
    def "size() - when tuple<#t0, #t1, #t2, #t3> created then return their size"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        expect:
        tuple.size() == 4

        where:
        t0 | t1 | t2 | t3
        0  | 0  | 0  | 0
        1  | 1  | 0  | 0
        1  | 2  | 3  | 4
    }

    @Unroll
    def "toArray() - when tuple<#t0, #t1, #t2, #t3> created then return an array with tuple #elements"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.toArray()

        then:
        actual == elements

        where:
        t0 | t1 | t2 | t3 | elements
        0  | 0  | 0  | 0  | [0, 0, 0, 0]
        1  | 1  | 0  | 0  | [1, 1, 0, 0]
        1  | 2  | 3  | 4  | [1, 2, 3, 4]
    }

    @Unroll
    def "toList() - when tuple<#t0, #t1, #t2, #t3> created then return a list with tuple #elements"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.toList()

        then:
        actual == elements

        where:
        t0 | t1 | t2 | t3 | elements
        0  | 0  | 0  | 0  | [0, 0, 0, 0]
        1  | 1  | 0  | 0  | [1, 1, 0, 0]
        1  | 2  | 3  | 4  | [1, 2, 3, 4]
    }

    @Unroll
    def "toSet() - when tuple<#t0, #t1, #t2, #t3> created then return a set with unique tuple #elements"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.toSet()

        then:
        actual == elements

        where:
        t0 | t1 | t2 | t3 | elements
        0  | 0  | 0  | 0  | [0] as Set
        1  | 1  | 0  | 0  | [1, 0] as Set
        1  | 2  | 3  | 4  | [1, 2, 3, 4] as Set
    }

    @Unroll
    def "stream() - when tuple<#t0, #t1, #t2, #t3> created then return a stream with tuple #elements"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.stream()

        then:
        actual.toArray() == elements

        where:
        t0 | t1 | t2 | t3 | elements
        0  | 0  | 0  | 0  | [0, 0, 0, 0]
        1  | 1  | 0  | 0  | [1, 1, 0, 0]
        1  | 2  | 3  | 4  | [1, 2, 3, 4]
    }

    @Unroll
    def "spliterator() - when tuple created then return a spliterator with tuple size"() {
        given:
        def tuple = Quadruple.of(0, 0, 0, 0)

        when:
        def actual = tuple.spliterator()

        then:
        actual.estimateSize() == tuple.size()
        (actual.characteristics() ^ SUBSIZED) == (SIZED | IMMUTABLE | ORDERED)
    }

    @Unroll
    def "iterator() - when tuple created then return an iterator with tuple size"() {
        given:
        def tuple = Quadruple.of(0, 0, 0, 0)

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
    def "toString() - when tuple<#t0, #t1, #t2, #t3> created then return an expected #stringRepresentation"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.toString()

        then:
        actual == stringRepresentation

        where:
        t0 | t1 | t2 | t3 | stringRepresentation
        0  | 0  | 0  | 0  | "(0, 0, 0, 0)"
        1  | 1  | 0  | 0  | "(1, 1, 0, 0)"
        1  | 2  | 3  | 4  | "(1, 2, 3, 4)"
    }

    @Unroll
    def "convert() - when tuple<#t0, #t1, #t2, #t3> created then convert to #expected int by function t.getT0() * 2"() {
        given:
        def tuple = Quadruple.of(t0, t1, t2, t3)

        when:
        def actual = tuple.convert {((Quadruple<?, ?, ?, ?>) it).getT0() * 2}

        then:
        actual == expected

        where:
        t0 | t1 | t2 | t3 | expected
        1  | 1  | 0  | 0  | 2
        2  | 2  | 0  | 0  | 4
    }

    def "equalsHashCode() - verify equals and hashCode contracts"() {
        expect:
        EqualsVerifier.forClass(Quadruple.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .report()
            .successful
    }
}
