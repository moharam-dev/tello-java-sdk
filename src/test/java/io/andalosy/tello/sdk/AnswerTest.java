package io.andalosy.tello.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void whenNonIntegerValue_assertExceptionIsThrown() {
        Answer answer = new Answer(false, "koko");
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            answer.valueAsInteger();
        });

        assertTrue(exception != null);
    }
}