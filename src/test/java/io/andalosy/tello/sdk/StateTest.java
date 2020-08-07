package io.andalosy.tello.sdk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    @Test
    void whenStateReply_assertCorrectParsing() {
        Drone.State state = new Drone.State("pitch:10;roll:20;yaw:30;vgx:40;vgy:50;vgz:60;templ:86;temph:89;tof:10;h:76;bat:43;baro:211.00;time:80;agx:-4.00;agy:1.00;agz:-1000.00;");

        assertTrue(state.pitch() == 10);
        assertTrue(state.roll() == 20);
        assertTrue(state.yaw() == 30);

        assertTrue(state.speedX() == 40);
        assertTrue(state.speedY() == 50);
        assertTrue(state.speedZ() == 60);

        assertTrue(state.accelerationX() == -4.00);
        assertTrue(state.accelerationY() == 1.00);
        assertTrue(state.accelerationZ() == -1000.00);

        assertTrue(state.tempretureLow() == 86);
        assertTrue(state.tempretureHigh() == 89);

        assertTrue(state.height() == 76);
        assertTrue(state.flightTime() == 10);
        assertTrue(state.batteryLevel() == 43);
        assertTrue(state.barometer() == 211.00);
        assertTrue(state.motorTime() == 80);

    }
}