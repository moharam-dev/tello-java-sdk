package io.andalosy.tello.sdk;

public class TelloAnswer {
    private boolean result;
    private String value;

    public TelloAnswer(boolean result, String value){
        this.result = result;
        this.value = value;
    }

    public TelloAnswer(String value){
        this.result = true;
        this.value = value;
    }

    public boolean result() {
        return result;
    }

    public String value() {
        return value;
    }

    public int valueAsInteger(){
        return Integer.parseInt(value);
    }

    TelloState valueAsState(){
        return new TelloState(value);
    }

    public void validate() throws Exception{
        if(result == false){
            throw new Exception(value);
        }
    }

}