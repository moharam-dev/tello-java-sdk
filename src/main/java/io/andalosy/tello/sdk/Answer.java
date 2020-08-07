package io.andalosy.tello.sdk;

public class Answer {
    private boolean result;
    private String value;

    public Answer(boolean result, String value){
        this.result = result;
        this.value = value;
    }

    public Answer(String value){
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

    public State valueAsState(){
        return new State(value);
    }

    public void validate() throws Exception{
        if(result == false){
            throw new Exception(value);
        }
    }
}