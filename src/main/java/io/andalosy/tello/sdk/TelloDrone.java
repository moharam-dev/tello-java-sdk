package io.andalosy.tello.sdk;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TelloDrone {
    private TelloChannel telloCommandChannel;
    private TelloCommander telloDroneCommander;

    private TelloChannel telloStateChannel;
    private TelloCommander telloStateCommander;

    private static final String DRONE_ADDRESS = "192.168.10.1";
    private static final int DRONE_PORT = 8889;

    private static final String STATE_ADDRESS = "0.0.0.0";
    private static final int STATE_PORT = 8890;

    public TelloDrone() {
        arm();
    }

    //////////////////////////////////
    // configuration

    private boolean arm() {
        try {
            this.telloCommandChannel = new TelloChannel(DRONE_ADDRESS, DRONE_PORT);
            this.telloDroneCommander = new TelloCommander(this.telloCommandChannel);

            this.telloStateChannel = new TelloChannel(STATE_ADDRESS, STATE_PORT);
            this.telloStateCommander = new TelloCommander(this.telloStateChannel);

            if (this.telloCommandChannel.reachable()) {
                sdkMode();
                return true;
            }
        }
        catch (SocketException e) {
            System.out.println("-- Communication error: " + e.getMessage());
        } catch (UnknownHostException e) {
            System.out.println("-- Communication error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("-- Communication error: " + e.getMessage());
        }

        return false;
    }

    public void sdkMode() {
        this.telloDroneCommander.command("command");
    }

    public TelloAnswer wifiSignal()  {
        return this.telloDroneCommander.command("wifi?");
    }

    //////////////////////////////////
    // commands

    public TelloAnswer takeoff() {
        return this.telloDroneCommander.command("takeoff");
    }

    public TelloAnswer land() {
        return this.telloDroneCommander.command("land");
    }

    public TelloAnswer speed(int centimeterPerSecond) {
        return this.telloDroneCommander.command(String.format("speed %d", centimeterPerSecond));
    }

    //////////////////////////////////

    public TelloAnswer up(int centimeters) {
        return this.telloDroneCommander.command(String.format("up %d", centimeters));
    }

    public TelloAnswer down(int centimeters) {
        return this.telloDroneCommander.command(String.format("down %d", centimeters));
    }

    //////////////////////////////////

    public TelloAnswer left(int centimeters) {
        return this.telloDroneCommander.command(String.format("left %d", centimeters));
    }

    public TelloAnswer right(int centimeters) {
        return this.telloDroneCommander.command(String.format("right %d", centimeters));
    }

    //////////////////////////////////

    public TelloAnswer forward(int centimeters) {
        return this.telloDroneCommander.command(String.format("forward %d", centimeters));
    }

    public TelloAnswer backward(int centimeters) {
        return this.telloDroneCommander.command(String.format("back %d", centimeters));
    }

    //////////////////////////////////

    public TelloAnswer rotateClockwise(int degrees) {
        return this.telloDroneCommander.command(String.format("cw %d", degrees));
    }

    public TelloAnswer rotateCounterClockwise(int degrees) {
        return this.telloDroneCommander.command(String.format("ccw %d", degrees));
    }

    //////////////////////////////////

    private TelloAnswer flip(String direction) {
        return this.telloDroneCommander.command("flip " + direction);
    }

    public TelloAnswer flipLeft() {
        return flip("l");
    }

    public TelloAnswer flipRight() {
        return flip("r");
    }

    public TelloAnswer flipForward() {
        return flip("f");
    }

    public TelloAnswer flipBackward() {
        return flip("b");
    }

    //////////////////////////////////
    // video

    public TelloAnswer videoStart() {
        return this.telloDroneCommander.command("streamon");
    }

    public TelloAnswer videoStop(){
        return this.telloDroneCommander.command("streamoff");
    }

    //////////////////////////////////
    // emergency

    public TelloAnswer halt() {
        return this.telloDroneCommander.command("emergency");
    }

    //////////////////////////////////
    // navigation

    public TelloAnswer hover() {
        return this.telloDroneCommander.command("stop");
    }

    public TelloAnswer position(TelloPosition position, int speed) {
        return this.telloDroneCommander.command(
                String.format("go %d %d %d %d",
                        position.getX(),
                        position.getY(),
                        position.getZ(),
                        speed));
    }

    public TelloAnswer curve(TelloPosition start, TelloPosition end, int speed) {
        return this.telloDroneCommander.command(
                String.format("curve %d %d %d %d %d %d %d",
                        start.getX(), start.getY(), start.getZ(),
                        end.getX(), end.getY(), end.getZ(),
                        speed));
    }

    //////////////////////////////////
    // telemetery ??
    // not clear to me if it is present anymore?

    public TelloAnswer state()  {
        //pitch:0;roll:0;yaw:0;vgx:0;vgy:0;vgz:0;templ:86;temph:89;tof:10;h:0;bat:43;baro:211.54;time:0;agx:-4.00;agy:1.00;agz:-1000.00;
        return this.telloStateCommander.answer();
    }

    public TelloAnswer height()  {
        return this.telloDroneCommander.command("height?");
    }

    public TelloAnswer speed()  {
        return this.telloDroneCommander.command("speed?");
    }

    public TelloAnswer battery()  {
        return this.telloDroneCommander.command("battery?");
    }

    public TelloAnswer flightTime()  {
        return this.telloDroneCommander.command("time?");
    }
}

