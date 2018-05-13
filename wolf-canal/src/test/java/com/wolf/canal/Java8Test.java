package com.wolf.canal;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created on 18/5/6 17:42.
 *
 * @author wolf
 */
public class Java8Test {

    @Test
    public void testBinaryOperator() {
        int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);
        assertEquals(6, count);
    }

    interface Function<Long> {

    }
}
