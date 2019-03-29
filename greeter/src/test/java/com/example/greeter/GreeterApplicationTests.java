package com.example.greeter;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class GreeterApplicationTests {

    private Greeter greeter = new Greeter();

    @Test
    public void greeterSaysHello() {
        Assert.assertThat(greeter.sayHello(), CoreMatchers.containsString("Hello"));
    }

}
