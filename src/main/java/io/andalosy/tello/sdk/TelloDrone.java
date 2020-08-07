package io.andalosy.tello.sdk;

import java.io.IOException;

public class TelloDrone {
    private TelloChannel telloCommandChannel;
    private TelloCommander telloDroneCommander;

    private TelloChannel telloStateChannel;
    private TelloCommander telloStateCommander;

    private static final String DRONE_ADDRESS = "192.168.10.1";
    private static final int DRONE_PORT = 8889;

    private static final String STATE_ADDRESS = "0.0.0.0";
    private static final int STATE_PORT = 8890;

    public TelloDrone() throws IOException, TelloException {
        arm();
    }

    //////////////////////////////////
    // configuration

    private void arm() throws TelloException, IOException {

        this.telloCommandChannel = new TelloChannel(DRONE_ADDRESS, DRONE_PORT);
        this.telloDroneCommander = new TelloCommander(this.telloCommandChannel);

        this.telloStateChannel = new TelloChannel(STATE_ADDRESS, STATE_PORT);
        this.telloStateCommander = new TelloCommander(this.telloStateChannel);

        if(this.telloCommandChannel.reachable() == false){
            throw new TelloException("Tello drone is not connected via wifi yet !!");
        }

        sdkMode();
    }

    public void sdkMode() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("command");
    }

    public String wifiName() throws IOException {
        return this.telloDroneCommander.readString("wifi?");
    }

    //////////////////////////////////
    // commands

    public void takeoff() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("takeoff");
    }

    public void land() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("land");
    }

    public void speed(int centimeterPerSecond) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("speed %d", centimeterPerSecond));
    }

    //////////////////////////////////

    public void up(int centimeters) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("up %d", centimeters));
    }

    public void down(int centimeters) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("down %d", centimeters));
    }

    //////////////////////////////////

    public void left(int centimeters) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("left %d", centimeters));
    }

    public void right(int centimeters) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("right %d", centimeters));
    }

    //////////////////////////////////

    public void forward(int centimeters) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("forward %d", centimeters));
    }

    public void backward(int centimeters) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("back %d", centimeters));
    }

    //////////////////////////////////

    public void rotateClockwise(int degrees) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("cw %d", degrees));
    }

    public void rotateCounterClockwise(int degrees) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(String.format("ccw %d", degrees));
    }

    //////////////////////////////////

    private void flip(String direction) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("flip " + direction);
    }

    public void flipLeft() throws TelloException, IOException {
        flip("l");
    }

    public void flipRight() throws TelloException, IOException {
        flip("r");
    }

    public void flipForward() throws TelloException, IOException {
        flip("f");
    }

    public void flipBackward() throws TelloException, IOException {
        flip("b");
    }

    //////////////////////////////////
    // video

    public void videoStart() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("streamon");
    }

    public void videoStop() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("streamoff");
    }

    //////////////////////////////////
    // emergency

    public void halt() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("emergency");
    }

    //////////////////////////////////
    // navigation

    public void hover() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("stop");
    }

    public void position(TelloPosition position, int speed) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(
                String.format("go %d %d %d %d",
                        position.getX(),
                        position.getY(),
                        position.getZ(),
                        speed));
    }

    public void curve(TelloPosition start, TelloPosition end, int speed) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(
                String.format("curve %d %d %d %d %d %d %d",
                        start.getX(), start.getY(), start.getZ(),
                        end.getX(), end.getY(), end.getZ(),
                        speed));
    }

    //////////////////////////////////
    // telemetery ??
    // not clear to me if it is present anymore?

    public TelloState state() throws IOException {
        //pitch:0;roll:0;yaw:0;vgx:0;vgy:0;vgz:0;templ:86;temph:89;tof:10;h:0;bat:43;baro:211.54;time:0;agx:-4.00;agy:1.00;agz:-1000.00;
        String reply = this.telloStateCommander.listenToData();
        return TelloReplyParser.status(reply);
    }

    public int height() throws IOException {
        return this.telloDroneCommander.readInteger("height?");
    }

    public int speed() throws IOException {
        return this.telloDroneCommander.readInteger("speed?");
    }

    public int battery() throws IOException {
        return this.telloDroneCommander.readInteger("battery?");
    }

    public int flightTime() throws IOException {
        return this.telloDroneCommander.readInteger("time?");
    }

}

