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

    private boolean isOk(String reply){
        return !reply.contains("error");
    }

    public TelloAnswer command(String command){
        try {
            this.telloChannel.send(command.getBytes());
            return answer();
        }
        catch (IOException e) {
            return new TelloAnswer(false, "Error in drone communication : " + e.getMessage());
        }
    }

    public TelloAnswer answer() {
        try {
            byte[] reply = this.telloChannel.receive();
            String answer = new String(reply).trim();

            if(isOk(answer) == false) {
                // drone says something is not right!
                return new TelloAnswer(false, "Invalid drone answer : " + answer);
            }

            return new TelloAnswer(answer);
        }
        catch (IOException e) {
            // networking issue
            return new TelloAnswer(false, "Error in drone communication : " + e.getMessage());
        }
    }
}
