package io.andalosy.tello.sdk;

import java.io.IOException;

public interface ChannelDrone {
    boolean reachable();

    boolean isOk(String reply);
    String command(String command);
    String answer();

    double averageReceiveTimeMillis();
}
