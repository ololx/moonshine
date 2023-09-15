/**
 * Copyright 2022 the project moonshine authors
 * and the original author or authors annotated by {@author}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.util.function

import spock.lang.Specification

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 15/09/2023 2:33 pm
 */
class ByteBinaryOperatorTest extends Specification {

    def "addition returns the correct result"() {
        given:
        ByteBinaryOperator addition = {byte left, byte right -> (byte) (left + right)}

        when:
        byte result = addition.applyAsByte((byte) 5, (byte) 3)

        then:
        result == (byte) 8
    }

    def "subtraction returns the correct result"() {
        given:
        ByteBinaryOperator subtraction = {byte left, byte right -> (byte) (left - right)}

        when:
        byte result = subtraction.applyAsByte((byte) 5, (byte) 3)

        then:
        result == (byte) 2
    }

    def "multiplication returns the correct result"() {
        given:
        ByteBinaryOperator multiplication = {byte left, byte right -> (byte) (left * right)}

        when:
        byte result = multiplication.applyAsByte((byte) 5, (byte) 3)

        then:
        result == (byte) 15
    }

    def "division returns the correct result"() {
        given:
        ByteBinaryOperator division = {byte left, byte right -> (byte) (left / right)}

        when:
        byte result = division.applyAsByte((byte) 6, (byte) 3)

        then:
        result == (byte) 2
    }

    def "division by zero throws ArithmeticException"() {
        given:
        ByteBinaryOperator division = {byte left, byte right -> (byte) (left / right)}

        when:
        division.applyAsByte((byte) 6, (byte) 0)

        then:
        thrown ArithmeticException
    }
}
