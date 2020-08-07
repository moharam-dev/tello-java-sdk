package io.andalosy.tello.sdk;

public class State {
    private boolean isValid;
    private String origin;

    private int height;

    private int pitch;
    private int roll;
    private int yaw;

    private int speedX;
    private int speedY;
    private int speedZ;

    private float accelerationX;
    private float accelerationY;
    private float accelerationZ;

    private int tempretureLow;
    private int tempretureHigh;

    private int flightTime;
    private int batteryLevel;

    private float barometer;
    private int motorTime;

    public State(String reply){
        origin = reply;
        isValid = parse(reply);
    }

    // internals
    private void set(String field, String value) {
        switch (field) {
            case "pitch":
                pitch = Integer.parseInt(value);
                break;
            case "roll":
                roll = Integer.parseInt(value);
                break;
            case "yaw":
                yaw = Integer.parseInt(value);
                break;
            /////////
            case "vgx":
                speedX = Integer.parseInt(value);
                break;
            case "vgy":
                speedY = Integer.parseInt(value);
                break;
            case "vgz":
                speedZ = Integer.parseInt(value);
                break;
            /////////
            case "agx":
                accelerationX = Float.parseFloat(value);
                break;
            case "agy":
                accelerationY = Float.parseFloat(value);
                break;
            case "agz":
                accelerationZ = Float.parseFloat(value);
                break;
            /////////
            case "templ":
                tempretureLow = Integer.parseInt(value);
                break;
            case "temph":
                tempretureHigh = Integer.parseInt(value);
                break;
            /////////
            case "tof":
                flightTime = Integer.parseInt(value);
                break;
            case "h":
                height = Integer.parseInt(value);
                break;
            case "bat":
                batteryLevel = Integer.parseInt(value);
                break;
            case "baro":
                barometer = Float.parseFloat(value);
                break;
            case "time":
                motorTime = Integer.parseInt(value);
                break;
        }
    }

    private boolean parse(String reply) {
        try {
            reply = reply.trim();
            String[] tokens = reply.split("[;]");
            for (String token : tokens) {
                String[] fieldAndValue = token.split("[:]");
                set(fieldAndValue[0], fieldAndValue[1]);
            }

            return true;
        }
        catch(Exception err){
            System.out.println("Invalid drone state :" + reply);
            return false;
        }
    }

    public int height() {   return height;  }

    public int pitch() {    return pitch;   }
    public int roll()   {   return roll;    }
    public int yaw()    {   return yaw;     }

    public int speedX()  {      return speedX;      }
    public int speedY()  {      return speedY;      }
    public int speedZ()  {      return speedZ;      }

    public float accelerationX()    {   return accelerationX; }
    public float accelerationY()    {   return accelerationY; }
    public float accelerationZ()    {   return accelerationZ; }

    public int tempretureLow()      { return tempretureLow;     }
    public int tempretureHigh()      { return tempretureHigh;     }

    public int flightTime() {   return flightTime; }
    public int batteryLevel() {   return batteryLevel; }
    public float barometer() {   return barometer; }
    public int motorTime() {   return motorTime; }

    public void validate() throws Exception{
        if(isValid == false){
            throw new Exception("Invalid drone state: " + origin);
        }
    }
}
