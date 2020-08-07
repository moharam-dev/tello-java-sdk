
import io.andalosy.tello.sdk.TelloDrone;
import io.andalosy.tello.sdk.TelloState;

public class TelloDroneTester {

    public static void main(String args[]){
        try {
            TelloDrone telloDrone = new TelloDrone();
            telloDrone.videoStart();
            telloDrone.takeoff();

            for (int i =0; i< 5; i++ ) {
                telloDrone.rotateClockwise(360);

                int battery = telloDrone.battery();
                String wifi = telloDrone.wifiName();
                System.out.println("Tello is ON: battery: " + String.valueOf(battery) + ", wifi: " + wifi);
                TelloState state = telloDrone.state();
                System.out.println("battery level: " + String.valueOf(state.batteryLevel()));
                Thread.sleep(3000);
            }

            telloDrone.land();
            // Stream #0:0: Video: h264 (Main), yuv420p(progressive), 960x720, 25 fps, 25 tbr, 1200k tbn, 50 tbc
            // ffmpeg -i udp://0.0.0.0:11111 -f sdl "window title"
            // ffmpeg -re -i udp://0.0.0.0:11111 -c copy -map 0 -f rtp_mpegts -fec prompeg=l=5:d=20 rtp://46.137.31.248:5000
        }
        catch (Exception err){
            System.out.println(err.getMessage());
        }
    }
}