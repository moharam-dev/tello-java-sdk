package io.andalosy.tello.sdk;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.time.Instant;

public class TelloChannel {
    private final DatagramSocket droneSocket;
    private final InetAddress droneAddress;
    private final int dronePort;
    private final DescriptiveStatistics commStats;

    public TelloChannel(String droneIp, int dronePort) throws SocketException, UnknownHostException {
        this.droneSocket = new DatagramSocket(dronePort);
        this.droneAddress = InetAddress.getByName(droneIp);
        this.dronePort = dronePort;
        this.commStats = new DescriptiveStatistics();
    }

    public boolean reachable() throws IOException {
        return this.droneAddress.isReachable(10000);
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

    public double averageCommunicationTime(){
        return commStats.getPercentile(90);
    }
}
