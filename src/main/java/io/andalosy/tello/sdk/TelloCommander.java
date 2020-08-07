package io.andalosy.tello.sdk;

import java.io.IOException;
import java.net.*;

public class TelloCommander {
    private final TelloChannel telloChannel;

    public TelloCommander(TelloChannel telloChannel) throws SocketException, UnknownHostException {
        this.telloChannel = telloChannel;
    }

    ///////////////////////////////////
    // Internals

    private String order(String command) throws IOException {
        this.telloChannel.send(command.getBytes());
        byte[] reply = this.telloChannel.receive();
        return new String(reply).trim();
    }

    public void controlCommand(String commandString) throws TelloException, IOException {
        String reply = order(commandString);

        if(TelloReplyParser.success(reply)){
            throw new TelloException("Failed to execute command : " + commandString + ", drone reply was: " + reply);
        }
    }

    public int readInteger(String commandString) throws IOException {
        String reply = order(commandString);
        return TelloReplyParser.number(reply);
    }

    public String readString(String commandString) throws IOException {
        return order(commandString);
    }

    public String listenToData() throws IOException {
        byte[] reply = this.telloChannel.receive();
        return new String(reply);
    }
}
