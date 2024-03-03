package test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyTest {

    @Test
    public void test(){
        assertTrue("hello".contains("el"));
    }
}
