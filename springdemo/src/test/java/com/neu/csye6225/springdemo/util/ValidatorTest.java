package com.neu.csye6225.springdemo.util;

import com.neu.csye6225.springdemo.request.UserRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

    @Test
    public void isUsernameValidTest() {

        Assert.assertEquals(Validator.isUsernameValid("reddy@gmail.com"), true);
        Assert.assertNotEquals(Validator.isUsernameValid("amireddy.m"), true);
        Assert.assertEquals(Validator.isUsernameValid("amireddy.m"), false);
        Assert.assertNotEquals(Validator.isUsernameValid("test@gmail.com"), false);
    }

    @Test
    public void isUserRequestValidTest() {

        UserRequest userRequest = new UserRequest("manojreddy","amireddy","amireddy@gmail.com","123456");
        Assert.assertEquals(Validator.isUserRequestValid(userRequest), true);
        userRequest.setUsername("amireddy.m");
        Assert.assertNotEquals(Validator.isUserRequestValid(userRequest), true);
        Assert.assertEquals(Validator.isUserRequestValid(userRequest), false);
    }
}
