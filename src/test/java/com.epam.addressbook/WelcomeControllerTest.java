package com.epam.addressbook;

import com.epam.addressbook.controller.rest.WelcomeController;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WelcomeControllerTest {
    @Test
    public void sayHello_whenWelcomeMessageIsSet_returnsWelcomeMessage() {
        WelcomeController controller = new WelcomeController("A welcome message");

        assertThat(controller.greeting()).isEqualTo("A welcome message");
    }
}