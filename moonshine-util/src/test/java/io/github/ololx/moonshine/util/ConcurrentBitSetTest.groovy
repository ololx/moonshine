package io.github.ololx.moonshine.util

import spock.lang.Specification

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 13/09/2023 9:40 pm
 */
class ConcurrentBitSetTest extends Specification {

    @Unroll
    def "get when bit is unsetted then return false with length #length"() {
        given:
        def bitSet = new NonBlockingConcurrentBitSet(length)

        expect:
        !bitSet.get(index)

        where:
        length | index
        1       | 0
        8       | 7
        64      | 63
    }

    @Unroll
    def "set when set bit then get return true with length #length"() {
        given:
        def bitSet = new NonBlockingConcurrentBitSet(length)

        when:
        bitSet.set(index)

        then:
        bitSet.get(index)

        where:
        length | index
        1       | 0
        8       | 7
        64      | 63
    }

    @Unroll
    def "clear when bit is cleared then return false with length #length"() {
        given:
        def bitSet = new NonBlockingConcurrentBitSet(length)

        when:
        bitSet.set(index)
        bitSet.clear(index)

        then:
        !bitSet.get(index)

        where:
        length | index
        1       | 0
        8       | 7
        64      | 63
    }

    @Unroll
    def "flip when bit is unset then return true with length #length"() {
        given:
        def bitSet = new NonBlockingConcurrentBitSet(length)

        when:
        bitSet.flip(index)

        then:
        bitSet.get(index)

        where:
        length | index
        1       | 0
        8       | 7
        64      | 63
    }

    def "get when index less than zero then throw exception"() {
        given:
        def bitSet = new NonBlockingConcurrentBitSet(1)

        when:
        bitSet.get(-1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "get when index more than bit set length then throw exception"() {
        given:
        def bitSet = new NonBlockingConcurrentBitSet(1)

        when:
        bitSet.get(1)

        then:
        thrown(IndexOutOfBoundsException)
    }
}

