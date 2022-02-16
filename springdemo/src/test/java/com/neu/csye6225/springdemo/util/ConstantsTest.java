package com.neu.csye6225.springdemo.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ConstantsTest {

    @Test
    public void applicationRunningTest() {

        Assert.assertEquals(Constants.APPLICATION_RUNNING, "Running!");
        Assert.assertNotEquals(Constants.APPLICATION_RUNNING, "running");
    }
}
