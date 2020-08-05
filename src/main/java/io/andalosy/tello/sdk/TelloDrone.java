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

    public void sdkVersion() throws IOException {
        this.telloDroneCommander.readCommand("sdk?");
    }

    public void serialNo() throws IOException {
        this.telloDroneCommander.readCommand("sn?");
    }

    public void wifiName() throws IOException {
        this.telloDroneCommander.readCommand("wifi?");
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

    private void videoStart() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("streamon");
    }

    private void videoStop() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("streamoff");
    }

    //////////////////////////////////
    // emergency

    private void halt() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("emergency");
    }

    //////////////////////////////////
    // navigation

    private void hover() throws TelloException, IOException {
        this.telloDroneCommander.controlCommand("stop");
    }

    private void position(TelloPosition position, int speed) throws TelloException, IOException {
        this.telloDroneCommander.controlCommand(
                String.format("go %d %d %d %d",
                        position.getX(),
                        position.getY(),
                        position.getZ(),
                        speed));
    }

    private void curve(TelloPosition start, TelloPosition end, int speed) throws TelloException, IOException {
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
        String reply = this.telloStateCommander.listenToData();
        return TelloReplyParser.status(reply);
    }

    public int height() throws IOException {
        return this.telloDroneCommander.readCommand("height?");
    }

    public int speed() throws IOException {
        return this.telloDroneCommander.readCommand("Speed?");
    }

    public int battery() throws IOException {
        return this.telloDroneCommander.readCommand("Battery?");
    }

    public int flightTime() throws IOException {
        return this.telloDroneCommander.readCommand("Time?");
    }

}

