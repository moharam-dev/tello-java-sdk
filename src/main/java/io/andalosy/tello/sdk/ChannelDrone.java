package io.andalosy.tello.sdk;

import java.io.IOException;

public interface ChannelDrone {
    boolean reachable() throws IOException;
    byte[] receive() throws IOException;
    void send(byte[] command) throws IOException;
    double averageReceiveTimeMillis();
}
