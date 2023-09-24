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
class CoupleSpec extends Specification {

    @Unroll
    def "new Couple() - when create with #t0, #t1 then tuple contains #t0, #t1"() {
        given:
        def tuple = new Couple<>(t0, t1)

        expect:
        tuple.getT0() == t0
        tuple.getT1() == t1

        where:
        t0 << [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Double.MIN_VALUE, String.valueOf(Integer.MAX_VALUE)]
        t1 << [Character.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, String.valueOf(Byte.MAX_VALUE), Byte.MAX_VALUE]
    }

    @Unroll
    def "of() - when create with #t0, #t1 then tuple contains #t0, #t1"() {
        given:
        def tuple = Couple.of(t0, t1)

        expect:
        tuple.getT0() == t0
        tuple.getT1() == t1

        where:
        t0 << [Byte.MIN_VALUE, Character.MIN_VALUE, Short.MIN_VALUE, Integer.MIN_VALUE, Float.MIN_VALUE, Double.MIN_VALUE, String.valueOf(Integer.MAX_VALUE)]
        t1 << [Character.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, String.valueOf(Byte.MAX_VALUE), Byte.MAX_VALUE]
    }

    @Unroll
    def "getT0() - when get value by index 0 then return #t0"() {
        given:
        def tuple = new Couple<>(t0, t1)

        when:
        def actual = tuple.getT0()

        then:
        actual == t0

        where:
        t0 | t1
        1  | 2
    }

    @Unroll
    def "getT1() - when get value by index 1 then return #t1"() {
        given:
        def tuple = new Couple<>(t0, t1)

        when:
        def actual = tuple.getT1()

        then:
        actual == t1

        where:
        t0 | t1
        1  | 2
    }

    @Unroll
    def "get() - when get value by exists #index then return #expected"() {
        given:
        def tuple = new Couple<>(t0, t1)

        when:
        def actual = tuple.get(index)

        then:
        actual == expected

        where:
        t0 | t1 | index | expected
        1  | 2  | 0     | 1
        1  | 2  | 1     | 2
    }

    @Unroll
    def "get() - when #index is out of bounds [0, 2) then throw exception"() {
        given:
        def tuple = new Couple<>(t0, t1)

        when:
        tuple.get(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        t0 | t1 | index
        1  | 2  | -1
        1  | 2  | 2
    }

    @Unroll
    def "getOrDefault() - when #index is out of bounds [0, 2) then return #defaultValue"() {
        given:
        def tuple = new Couple<>(t0, t1)

        when:
        def actual = tuple.getOrDefault(index, defaultValue)

        then:
        actual == defaultValue

        where:
        t0 | t1 | index | defaultValue
        1  | 2  | -1    | 12
        1  | 2  | 2     | 22
    }

    @Unroll
    def "contains() - when tuple<#t0, #t1> contains #value then return true"() {
        given:
        def tuple = Couple.of(t0, t1)

        expect:
        tuple.contains(value)

        where:
        t0 | t1 | value
        1  | 2  | 1
        1  | 2  | 2
    }

    @Unroll
    def "contains() - when tuple<#t0, #t1> not contains #value then return false"() {
        given:
        def tuple = Couple.of(t0, t1)

        expect:
        !tuple.contains(value)

        where:
        t0 | t1 | value
        1  | 2  | 100
        1  | 2  | 100
    }

    @Unroll
    def "indexOf() - when tuple<#t0, #t1> contains #value then return first #index of #value"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.indexOf(value)

        then:
        actual == index

        where:
        t0 | t1 | value | index
        1  | 2  | 1     | 0
        1  | 2  | 2     | 1
        1  | 1  | 1     | 0
        2  | 2  | 2     | 0
        1  | 2  | 3     | -1
    }

    @Unroll
    def "lastIndexOf() - when tuple<#t0, #t1> contains #value then return last #index of #value"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.lastIndexOf(value)

        then:
        actual == index

        where:
        t0 | t1 | value | index
        1  | 2  | 1     | 0
        1  | 2  | 2     | 1
        1  | 1  | 1     | 1
        2  | 2  | 2     | 1
        1  | 2  | 3     | -1
    }

    @Unroll
    def "size() - when tuple<#t0, #t1> created then return their size"() {
        given:
        def tuple = Couple.of(0, 0)

        expect:
        tuple.size() == 2
    }

    @Unroll
    def "toArray() - when tuple<#t0, #t1> created then return an array with tuple #elements"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.toArray()

        then:
        actual == elements

        where:
        t0 | t1 | elements
        0  | 0  | [0, 0]
        1  | 1  | [1, 1]
        1  | 2  | [1, 2]
    }

    @Unroll
    def "toList() - when tuple<#t0, #t1> created then return a list with tuple #elements"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.toList()

        then:
        actual == elements

        where:
        t0 | t1 | elements
        0  | 0  | [0, 0]
        1  | 1  | [1, 1]
        1  | 2  | [1, 2]
    }

    @Unroll
    def "toSet() - when tuple<#t0, #t1> created then return a set with unique tuple #elements"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.toSet()

        then:
        actual == elements

        where:
        t0 | t1 | elements
        0  | 0  | [0] as Set
        1  | 1  | [1] as Set
        1  | 2  | [1, 2] as Set
    }

    @Unroll
    def "stream() - when tuple<#t0, #t1> created then return a stream with tuple #elements"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.stream()

        then:
        actual.toArray() == elements

        where:
        t0 | t1 | elements
        0  | 0  | [0, 0]
        1  | 1  | [1, 1]
        1  | 2  | [1, 2]
    }

    @Unroll
    def "spliterator() - when tuple created then return a spliterator with tuple size"() {
        given:
        def tuple = Couple.of(0, 0)

        when:
        def actual = tuple.spliterator()

        then:
        actual.estimateSize() == tuple.size()
        (actual.characteristics() ^ SUBSIZED) == (SIZED | IMMUTABLE | ORDERED)
    }

    @Unroll
    def "iterator() - when tuple created then return a iterator with tuple size"() {
        given:
        def tuple = Couple.of(0, 0)

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
    def "toString() - when tuple<#t0, #t1> created then return an expected #stringRepresentation"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.toString()

        then:
        actual == stringRepresentation

        where:
        t0 | t1 | stringRepresentation
        0  | 0  | "(0, 0)"
        1  | 1  | "(1, 1)"
        1  | 2  | "(1, 2)"
    }

    @Unroll
    def "convert() - when tuple<#t0, #t1> created then convert to #expected int by function t.getT0() * 2"() {
        given:
        def tuple = Couple.of(t0, t1)

        when:
        def actual = tuple.convert {((Couple<?, ?>) it).getT0() * 2}

        then:
        actual == expected

        where:
        t0 | t1 | expected
        1  | 1  | 2
        2  | 2  | 4
    }

    def "equalsHashCode() - verify equals and hashCode contracts"() {
        expect:
        EqualsVerifier.forClass(Couple.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .report()
            .successful
    }
}
