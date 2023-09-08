package io.github.ololx.moonshine.util.function;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 08.09.2023 15:55
 */
public class ByteBinaryOperatorTest {

    @Test
    public void applyAsByte_whenAddition_thenReturnResult() {
        ByteBinaryOperator addition = (left, right) -> (byte) (left + right);
        byte result = addition.applyAsByte((byte) 5, (byte) 3);

        assertEquals(result, (byte) 8);
    }

    @Test
    public void applyAsByte_whenSubtraction_thenReturnResult() {
        ByteBinaryOperator subtraction = (left, right) -> (byte) (left - right);
        byte result = subtraction.applyAsByte((byte) 5, (byte) 3);

        assertEquals(result, (byte) 2);
    }

    @Test
    public void applyAsByte_whenMultiplication_thenReturnResult() {
        ByteBinaryOperator multiplication = (left, right) -> (byte) (left * right);
        byte result = multiplication.applyAsByte((byte) 5, (byte) 3);

        assertEquals(result, (byte) 15);
    }

    @Test
    public void applyAsByte_whenDivision_thenReturnResult() {
        ByteBinaryOperator division = (left, right) -> (byte) (left / right);
        byte result = division.applyAsByte((byte) 6, (byte) 3);

        assertEquals(result, (byte) 2);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void applyAsByte_whenDivisionByZero_thenThrowException() {
        ByteBinaryOperator division = (left, right) -> (byte) (left / right);
        division.applyAsByte((byte) 6, (byte) 0);
    }
}