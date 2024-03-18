package io.github.moonshine.unsafe.adapter.functional


import spock.lang.Specification
import spock.lang.Unroll

/**
 * Tests for the ByteUnaryAccumulator interface.
 *
 * @author Alexander A. Kropotin
 * project moonshine
 * created 25/02/2024
 */
class ByteUnaryAccumulatorTest extends Specification {

    @Unroll
    def "apply() - when given operand #testOperand then returns #expectedResult"() {
        given:
        ByteUnaryFunction accumulator = {byte it -> (byte) (it + testDelta)} as ByteUnaryFunction

        when:
        byte result = accumulator.apply(testOperand)

        then:
        result == expectedResult

        where:
        testOperand  | testDelta | expectedResult
        0x01 as byte | 1         | 0x02 as byte
        0x02 as byte | 2         | 0x04 as byte
        0x7F as byte | 1         | 0x80 as byte
        0xFF as byte | -1        | 0xFE as byte
    }

    def "apply() - should correctly handle overflow"() {
        given:
        ByteUnaryFunction accumulator = {byte it -> (byte) (it + 1)}

        expect:
        accumulator.apply(0xFF as byte) == 0x00 as byte
    }
}

