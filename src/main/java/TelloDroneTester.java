
import io.andalosy.tello.sdk.TelloAnswer;
import io.andalosy.tello.sdk.TelloDrone;
import io.andalosy.tello.sdk.TelloPosition;
import io.andalosy.tello.sdk.TelloState;

public class TelloDroneTester {

    public static void main(String args[]){

        TelloDrone telloDrone = new TelloDrone();

        try {

            TelloAnswer answer;
            answer = telloDrone.battery();
            answer.validate();
            int batteryLevel = answer.valueAsInteger();

            telloDrone.videoStart().validate();
            telloDrone.takeoff().validate();

            // stay in the air till the battery drained!
            while (batteryLevel > 10) {
                telloDrone.rotateClockwise(360).validate();
                telloDrone.rotateCounterClockwise(360).validate();

                answer = telloDrone.battery();
                answer.validate();
                batteryLevel = answer.valueAsInteger();

                System.out.println("Tello battery level is : " + String.valueOf(batteryLevel) + "%");
            }

            telloDrone.land().validate();
        }
        catch (Exception err){
            // report
            System.out.println(err.getMessage());

            // no answer validation here;
            // best effort recovery, ignore errors!
            telloDrone.land();
        }

        // Stream #0:0: Video: h264 (Main), yuv420p(progressive), 960x720, 25 fps, 25 tbr, 1200k tbn, 50 tbc
        // ffmpeg -i udp://0.0.0.0:11111 -f sdl "window title"
        // ffmpeg -re -i udp://0.0.0.0:11111 -c copy -map 0 -f rtp_mpegts -fec prompeg=l=5:d=20 rtp://46.137.31.248:5000
    }
}