package io.github.ololx.moonshine.util


import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 13/09/2023 9:40 pm
 */
class ConcurrentBitArrayTest extends Specification {

    @Unroll
    def "get when #index bit is unsetted then return false"() {
        given:
        def bits = new ConcurrentBitArray(length)

        expect:
        !bits.get(index)

        where:
        length | index
        1      | 0
        8      | 7
        64     | 63
    }

    @Unroll
    def "set when set #index bit then get return true with length"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.set(index)

        then:
        bits.get(index)

        where:
        length | index
        1      | 0
        8      | 7
        64     | 63
    }

    @Unroll
    def "clear when #index bit is cleared then return false with length"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.set(index)
        bits.clear(index)

        then:
        !bits.get(index)

        where:
        length | index
        1      | 0
        8      | 7
        64     | 63
    }

    @Unroll
    def "flip when #index bit is unset then return true with length"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.flip(index)

        then:
        bits.get(index)

        where:
        length | index
        1      | 0
        8      | 7
        64     | 63
    }

    def "get when index less than 0 then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.get(-1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "get when index more than bit's length then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.get(1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "set when index less than 0 then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.set(-1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "set when index more than bit's length then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.set(1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "clear when index less than 0 then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.clear(-1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "clear when index more than bit's length then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.clear(1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "flip when index less than 0 then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.flip(-1)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "flip when index more than bit's length then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(1)

        when:
        bits.flip(1)

        then:
        thrown(IndexOutOfBoundsException)
    }
}
