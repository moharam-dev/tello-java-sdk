package io.andalosy.tello.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void whenNonIntegerValue_assertExceptionIsThrown() {
        Drone.Answer answer = new Drone.Answer(false, "koko");
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            answer.valueAsInteger();
        });
    }
}