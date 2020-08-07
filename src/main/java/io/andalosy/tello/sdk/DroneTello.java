package io.andalosy.tello.sdk;

import java.io.IOException;

public class DroneTello implements Drone {
    private ChannelDrone droneCommandChannel;
    private CommanderDrone droneCommander;

    private ChannelDrone droneStateChannel;
    private CommanderDrone droneStateCommander;

    public DroneTello(  ChannelDrone droneCommandChannel,
                        CommanderDrone droneCommander,
                        ChannelDrone droneStateChannel,
                        CommanderDrone droneStateCommander) {

        this.droneCommandChannel = droneCommandChannel;
        this.droneCommander = droneCommander;
        this.droneStateChannel = droneStateChannel;
        this.droneStateCommander = droneStateCommander;
    }

    //////////////////////////////////
    // configuration

    public boolean arm() {
        try {
            if (this.droneCommandChannel.reachable()) {
                sdkMode();
                return true;
            }
        }
        catch (IOException e) {
            System.out.println("-- Communication error: " + e.getMessage());
        }

        return false;
    }

    public void sdkMode() {
        this.droneCommander.command("command");
    }

    public Answer wifiSignal()  {
        return this.droneCommander.command("wifi?");
    }

    //////////////////////////////////
    // commands

    public Answer takeoff() {
        return this.droneCommander.command("takeoff");
    }

    public Answer land() {
        return this.droneCommander.command("land");
    }

    public Answer speed(int centimeterPerSecond) {
        return this.droneCommander.command(String.format("speed %d", centimeterPerSecond));
    }

    //////////////////////////////////

    public Answer up(int centimeters) {
        return this.droneCommander.command(String.format("up %d", centimeters));
    }

    public Answer down(int centimeters) {
        return this.droneCommander.command(String.format("down %d", centimeters));
    }

    //////////////////////////////////

    public Answer left(int centimeters) {
        return this.droneCommander.command(String.format("left %d", centimeters));
    }

    public Answer right(int centimeters) {
        return this.droneCommander.command(String.format("right %d", centimeters));
    }

    //////////////////////////////////

    public Answer forward(int centimeters) {
        return this.droneCommander.command(String.format("forward %d", centimeters));
    }

    public Answer backward(int centimeters) {
        return this.droneCommander.command(String.format("back %d", centimeters));
    }

    //////////////////////////////////

    public Answer rotateClockwise(int degrees) {
        return this.droneCommander.command(String.format("cw %d", degrees));
    }

    public Answer rotateCounterClockwise(int degrees) {
        return this.droneCommander.command(String.format("ccw %d", degrees));
    }

    //////////////////////////////////

    private Answer flip(String direction) {
        return this.droneCommander.command("flip " + direction);
    }

    public Answer flipLeft() {
        return flip("l");
    }

    public Answer flipRight() {
        return flip("r");
    }

    public Answer flipForward() {
        return flip("f");
    }

    public Answer flipBackward() {
        return flip("b");
    }

    //////////////////////////////////
    // video

    public Answer videoStart() {
        return this.droneCommander.command("streamon");
    }

    public Answer videoStop(){
        return this.droneCommander.command("streamoff");
    }

    //////////////////////////////////
    // emergency

    public Answer halt() {
        return this.droneCommander.command("emergency");
    }

    //////////////////////////////////
    // navigation

    public Answer hover() {
        return this.droneCommander.command("stop");
    }

    public Answer position(DronePosition position, int speed) {
        return this.droneCommander.command(
                String.format("go %d %d %d %d",
                        position.getX(),
                        position.getY(),
                        position.getZ(),
                        speed));
    }

    public Answer curve(DronePosition start, DronePosition end, int speed) {
        return this.droneCommander.command(
                String.format("curve %d %d %d %d %d %d %d",
                        start.getX(), start.getY(), start.getZ(),
                        end.getX(), end.getY(), end.getZ(),
                        speed));
    }

    //////////////////////////////////
    // telemetery ??
    // not clear to me if it is present anymore?

    public State state()  {
        //pitch:0;roll:0;yaw:0;vgx:0;vgy:0;vgz:0;templ:86;temph:89;tof:10;h:0;bat:43;baro:211.54;time:0;agx:-4.00;agy:1.00;agz:-1000.00;
        Answer answer = this.droneStateCommander.answerWithoutCommand();
        return answer.valueAsState();
    }

    public Answer speed()  {
        return this.droneCommander.command("speed?");
    }
}

