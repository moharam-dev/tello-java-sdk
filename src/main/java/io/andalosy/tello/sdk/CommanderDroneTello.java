package io.andalosy.tello.sdk;

import java.io.IOException;

public class CommanderDroneTello implements CommanderDrone {
    private final ChannelDrone telloChannel;

    public CommanderDroneTello(ChannelDrone telloChannel) {
        this.telloChannel = telloChannel;
    }

    ///////////////////////////////////
    // Internals

    private boolean isOk(String reply){
        return !reply.contains("error");
    }

    public Answer command(String command){
        try {
            this.telloChannel.send(command.getBytes());
            return answer();
        }
        catch (IOException e) {
            return new Answer(false, "Error in drone communication : " + e.getMessage());
        }
    }

    @Override
    public Answer answerWithoutCommand() {
        return answer();
    }

    private Answer answer() {
        try {
            byte[] reply = this.telloChannel.receive();
            String answer = new String(reply).trim();

            if(isOk(answer) == false) {
                // drone says something is not right!
                return new Answer(false, "Invalid drone answer : " + answer);
            }

            return new Answer(answer);
        }
        catch (IOException e) {
            // networking issue
            return new Answer(false, "Error in drone communication : " + e.getMessage());
        }
    }
}
