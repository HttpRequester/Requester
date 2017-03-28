package ir.bpadashi.requester.model;

/**
 * Created by BabakPadashi on 28/03/2017.
 */
public class ResponseString {

    private final Object str;

    public ResponseString(Object str) {
        this.str=str;
    }

    public String toString(){

        return (String) str.toString();

    }

    public String getResponse(){

        return (String) str.toString();

    }
}
