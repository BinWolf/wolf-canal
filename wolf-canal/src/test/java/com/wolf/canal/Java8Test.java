package com.wolf.canal;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.computeIfAbsent("name", this::getValue);
        System.out.println("---->"+map.get("name"));

    }

    private String getValue(String name) {
        System.out.println("name:" + name);
        return "wolf";
    }

}
