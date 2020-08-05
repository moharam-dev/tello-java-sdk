
import io.andalosy.tello.sdk.TelloDrone;

public class TelloDroneTester {

    public static void main(String args[]){
        try {
            TelloDrone telloDrone = new TelloDrone();

            telloDrone.takeoff();
            Thread.sleep(1000);
            telloDrone.land();

        }
        catch (Exception err){
            System.out.println(err.getMessage());
        }
    }
}