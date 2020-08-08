package io.andalosy.tello.sdk;

import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ChannelDroneTelloTest {
    @Test
    void whenVerifyingADroneReply_isOkWorks() throws SocketException, UnknownHostException {
        ChannelDroneTello channelDroneTello = new ChannelDroneTello("127.0.0.1", 9090);

        String reply = "error invalid imu";
        assertFalse(channelDroneTello.isOk(reply));

        reply = "11.34";
        assertTrue(channelDroneTello.isOk(reply));

        reply = "ok";
        assertTrue(channelDroneTello.isOk(reply));
    }
}