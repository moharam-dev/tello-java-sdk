package io.andalosy.tello.sdk;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.time.Instant;

public class ChannelDroneTello implements ChannelDrone {
    private final DatagramSocket droneSocket;
    private final InetAddress droneAddress;
    private final int dronePort;
    private final DescriptiveStatistics commStats;

    public ChannelDroneTello(String droneIp, int dronePort) throws SocketException, UnknownHostException {
        this.droneSocket = new DatagramSocket(dronePort);
        this.droneSocket.setSoTimeout(10000);
        this.droneAddress = InetAddress.getByName(droneIp);
        this.dronePort = dronePort;
        this.commStats = new DescriptiveStatistics();
    }

    public boolean reachable() {
        try {
            this.droneAddress.isReachable(10000);
            return true;

        } catch (IOException e) {
            System.out.println("Error reaching drone ip!");
            return false;
        }
    }

    public byte[] receive() throws IOException {
        byte[] replyBuffer = new byte[512];
        DatagramPacket replyPacket = new DatagramPacket(replyBuffer, replyBuffer.length);

        Instant start = Instant.now();
        this.droneSocket.receive(replyPacket);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        commStats.addValue(timeElapsed.getNano());

        return replyPacket.getData();
    }

    public void send(byte[] command) throws IOException {
        DatagramPacket commandPacket = new DatagramPacket(command, command.length, this.droneAddress, this.dronePort);
        this.droneSocket.send(commandPacket);
    }

    public double averageReceiveTimeMillis(){
        return commStats.getPercentile(90);
    }

    ///////////////////

    public boolean isOk(String reply){
        return !reply.contains("error");
    }

    public String command(String command){
        try {
            send(command.getBytes());
            return answer();
        }
        catch (IOException e) {
            return "error in drone communication : " + e.getMessage();
        }
    }

    public String answer() {
        try {
            byte[] reply = receive();
            return new String(reply).trim();
        }
        catch (IOException e) {
            // networking issue
            return "error in drone communication : " + e.getMessage();
        }
    }
}
