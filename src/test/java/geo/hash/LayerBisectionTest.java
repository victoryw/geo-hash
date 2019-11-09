/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package geo.hash;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LayerBisectionTest {
    @Test void should_get_right_bit_when_value_in_right() {
        LayerBisection bisec = new LayerBisection(1, -90, 90);
        final var position = BigDecimal.valueOf(80);
        assertEquals(0b1, bisec.split(position));
    }

    @Test void should_get_right_bit_when_value_in_middle() {
        LayerBisection bisec = new LayerBisection(1, -90, 90);
        final var position = BigDecimal.valueOf(0);
        assertEquals(0b1, bisec.split(position));
    }

    @Test void should_get_right_bit_when_value_in_left() {
        LayerBisection bisec = new LayerBisection(1, -90, 90);
        final var position = BigDecimal.valueOf(-10);
        assertEquals(0b0, bisec.split(position));
    }

    @Test void should_get_right_bit_when_value_compose_by_right_right_right() {
        LayerBisection bisec = new LayerBisection(3, -90, 90);
        final var position = BigDecimal.valueOf(80);
        assertEquals(0b111, bisec.split(position));
    }

    @Test void should_get_right_bit_when_value_compose_by_left_left_left() {
        LayerBisection bisec = new LayerBisection(3, -90, 90);
        final var position = BigDecimal.valueOf(-70);
        assertEquals(0b000, bisec.split(position));
    }

    @Test void should_get_right_bit_with_full_size_when_value_is_in_middle() {
        LayerBisection bisec = new LayerBisection(3, -90, 90);
        final var position = BigDecimal.valueOf(0);
        assertEquals(0b100, bisec.split(position));
    }

    @Test void should_get_right_answer_with_full_size_when_value_complex() {
        LayerBisection bisec = new LayerBisection(20, -90, 90);
        final var position = BigDecimal.valueOf(39.918118);
        assertEquals(0b10111000110001011011, bisec.split(position));
    }

    @Test void should_get_right_answer_with_full_size_when_value_complex2() {
        LayerBisection bisec = new LayerBisection(20, -180, 180);
        final var position = BigDecimal.valueOf(116.40382);
        assertEquals(0b11010010110001101010, bisec.split(position));
    }
}

