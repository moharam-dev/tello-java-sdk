package io.andalosy.tello.sdk;

import java.io.IOException;

public class DroneTello implements Drone {
    private ChannelDrone droneCommandChannel;
    private ChannelDrone droneStateChannel;

    public DroneTello(  ChannelDrone droneCommandChannel,
                        ChannelDrone droneStateChannel
                        ) {

        this.droneCommandChannel = droneCommandChannel;
        this.droneStateChannel = droneStateChannel;
    }

    //////////////////////////////////
    // configuration

    public boolean arm() {
        if (this.droneCommandChannel.reachable()) {
            sdkMode();
            return true;
        }

        return false;
    }

    public void sdkMode() {
        this.droneCommandChannel.command("command");
    }

    public Answer wifiSignal()  {
        String reply = droneCommandChannel.command("wifi?");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////
    // commands

    public Answer takeoff() {
        String reply = this.droneCommandChannel.command("takeoff");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer land() {
        String reply = this.droneCommandChannel.command("land");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer speed(int centimeterPerSecond) {
        String reply = this.droneCommandChannel.command(String.format("speed %d", centimeterPerSecond));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////

    public Answer up(int centimeters) {
        String reply = this.droneCommandChannel.command(String.format("up %d", centimeters));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer down(int centimeters) {
        String reply = this.droneCommandChannel.command(String.format("down %d", centimeters));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////

    public Answer left(int centimeters) {
        String reply = this.droneCommandChannel.command(String.format("left %d", centimeters));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer right(int centimeters) {
        String reply = this.droneCommandChannel.command(String.format("right %d", centimeters));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////

    public Answer forward(int centimeters) {
        String reply = this.droneCommandChannel.command(String.format("forward %d", centimeters));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer backward(int centimeters) {
        String reply = this.droneCommandChannel.command(String.format("back %d", centimeters));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////

    public Answer rotateClockwise(int degrees) {
        String reply = this.droneCommandChannel.command(String.format("cw %d", degrees));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer rotateCounterClockwise(int degrees) {
        String reply = this.droneCommandChannel.command(String.format("ccw %d", degrees));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////

    private Answer flip(String direction) {
        String reply = this.droneCommandChannel.command("flip " + direction);
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
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
        String reply = this.droneCommandChannel.command("streamon");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer videoStop(){
        String reply = this.droneCommandChannel.command("streamoff");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    @Override
    public Answer rtpToAwsMediaLive(String awsRtpInput) {
        try{
            String cmd = "sh -c ffmpeg -re -i udp://0.0.0.0:11111 -c copy -map 0 -f rtp_mpegts -fec prompeg=l=5:d=20 " + awsRtpInput;
            Runtime.getRuntime().exec(cmd);
            return new Answer("ok");
        } catch (IOException e) {
            return new Answer(false, "error failed to run ffmpeg :" + e.getMessage());
        }
    }

    //////////////////////////////////
    // emergency

    public Answer halt() {
        String reply = this.droneCommandChannel.command("emergency");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////
    // navigation

    public Answer hover() {
        String reply = this.droneCommandChannel.command("stop");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer position(Position position, int speed) {
        String reply = this.droneCommandChannel.command(
                String.format("go %d %d %d %d",
                        position.getX(),
                        position.getY(),
                        position.getZ(),
                        speed));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    public Answer curve(Position start, Position end, int speed) {
        String reply = this.droneCommandChannel.command(
                String.format("curve %d %d %d %d %d %d %d",
                        start.getX(), start.getY(), start.getZ(),
                        end.getX(), end.getY(), end.getZ(),
                        speed));
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }

    //////////////////////////////////
    // telemetery ??
    // not clear to me if it is present anymore?

    public State state()  {
        return new State(this.droneStateChannel.answer());
    }

    public Answer speed()  {
        String reply = this.droneCommandChannel.command("speed?");
        return new Answer(droneCommandChannel.isOk(reply), reply) ;
    }
}

