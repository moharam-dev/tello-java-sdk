package io.andalosy.tello.sdk;

public class TelloPosition {
    private int x,y,z;

    public TelloPosition(int x, int y, int z){
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
