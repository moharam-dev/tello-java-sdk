
import io.andalosy.tello.sdk.*;

import java.net.SocketException;
import java.net.UnknownHostException;

public class TelloAppMain {

    public static void main(String args[]){
        final String DRONE_ADDRESS = "192.168.10.1";
        final int DRONE_PORT = 8889;

        final String STATE_ADDRESS = "0.0.0.0";
        final int STATE_PORT = 8890;

        try {
            ChannelDrone droneCommandChannel = new ChannelDroneTello(DRONE_ADDRESS, DRONE_PORT);
            CommanderDrone droneCommander = new CommanderDroneTello(droneCommandChannel);

            ChannelDrone droneStateChannel = new ChannelDroneTello(STATE_ADDRESS, STATE_PORT);
            CommanderDrone droneStateCommander = new CommanderDroneTello(droneStateChannel);

            Drone drone = new DroneTello(
                    droneCommandChannel,
                    droneCommander,
                    droneStateChannel,
                    droneStateCommander
            );

            Answer answer;

            State state = drone.state();
            state.validate();
            int batteryLevel = state.batteryLevel();

            drone.videoStart().validate();
            drone.takeoff().validate();

            // stay in the air till the battery drained!
            while (batteryLevel > 10) {
                drone.rotateClockwise(360).validate();
                drone.rotateCounterClockwise(360).validate();

                state = drone.state();
                state.validate();
                batteryLevel = state.batteryLevel();

                System.out.println("Tello battery level is : " + String.valueOf(batteryLevel) + "%");
            }

            drone.land().validate();
        }
        catch (SocketException e) {
            System.out.println("-- Communication error: " + e.getMessage());
        }
        catch (UnknownHostException e) {
            System.out.println("-- Communication error: " + e.getMessage());
        }
        catch (Exception err){
            // report
            System.out.println(err.getMessage());
        }

        // Stream #0:0: Video: h264 (Main), yuv420p(progressive), 960x720, 25 fps, 25 tbr, 1200k tbn, 50 tbc
        // ffmpeg -i udp://0.0.0.0:11111 -f sdl "window title"
        // ffmpeg -re -i udp://0.0.0.0:11111 -c copy -map 0 -f rtp_mpegts -fec prompeg=l=5:d=20 rtp://46.137.31.248:5000
    }
}