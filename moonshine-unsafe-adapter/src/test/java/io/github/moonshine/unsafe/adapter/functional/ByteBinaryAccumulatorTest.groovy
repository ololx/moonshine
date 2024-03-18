package io.github.moonshine.unsafe.adapter.functional


import spock.lang.Specification
import spock.lang.Unroll

/**
 * Tests for the ByteBinaryAccumulator interface.
 *
 * @author Alexander A. Kropotin
 * project moonshine
 * created 25/02/2024
 */
class ByteBinaryAccumulatorTest extends Specification {

    @Unroll
    def "apply() - when given operands #leftOperand and #rightOperand then returns #expectedResult"() {
        given:
        ByteBinaryFunction accumulator = {byte left, byte right -> (byte) (left + right) } as ByteBinaryFunction

        when:
        byte result = accumulator.apply(leftOperand, rightOperand)

        then:
        result == expectedResult

        where:
        leftOperand  | rightOperand | expectedResult
        0x01 as byte | 0x01 as byte | 0x02 as byte
        0x02 as byte | 0x02 as byte | 0x04 as byte
        0x7F as byte | 0x01 as byte | 0x80 as byte
        0xFF as byte | 0x01 as byte | 0x00 as byte
    }

    def "apply() - should correctly handle overflow"() {
        given:
        ByteBinaryFunction accumulator = {byte left, byte right -> (byte) (left + right) }

        expect:
        accumulator.apply(0x7F as byte, 0x01 as byte) == 0x80 as byte
        accumulator.apply(0xFF as byte, 0x01 as byte) == 0x00 as byte
    }
}
