package io.andalosy.tello.sdk;

import java.util.HashMap;
import java.util.Map;

public class TelloReplyParser {
    public static boolean success(String reply){
        return "OK".compareTo(reply) == 0;
    }

    public static int number(String reply){
        return Integer.parseInt(reply);
    }

    public static TelloState status(String reply){
        return new TelloState(reply);
    }
}
