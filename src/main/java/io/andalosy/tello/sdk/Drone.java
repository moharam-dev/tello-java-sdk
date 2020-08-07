package io.andalosy.tello.sdk;

public interface Drone {
    boolean arm();
    void sdkMode();

    Answer wifiSignal();

    Answer takeoff();
    Answer land();
    Answer halt();

    Answer hover();
    Answer position(DronePosition position, int speed);
    Answer curve(DronePosition start, DronePosition end, int speed);
    Answer speed(int centimeterPerSecond);

    Answer up(int centimeters);
    Answer down(int centimeters);
    Answer left(int centimeters);
    Answer right(int centimeters);
    Answer forward(int centimeters);
    Answer backward(int centimeters);

    Answer rotateClockwise(int degrees);
    Answer rotateCounterClockwise(int degrees);

    Answer flipLeft();
    Answer flipRight();
    Answer flipForward();
    Answer flipBackward();

    Answer videoStart();
    Answer videoStop();

    // telemetry
    State state();
    Answer speed();


    class DronePosition {
        private int x,y,z;
        public DronePosition(int x, int y, int z){
            this.x= x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getZ() {
            return z;
        }
    }

}
