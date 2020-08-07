package io.andalosy.tello.sdk;

public interface CommanderDrone {
    Answer command(String command);
    Answer answerWithoutCommand();
}
