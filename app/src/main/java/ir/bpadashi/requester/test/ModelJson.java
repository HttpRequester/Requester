package ir.bpadashi.requester.test;

import java.io.Serializable;

/**
 * Created by BabakPadashi on 29/03/2017.
 */
class ModelJson implements Serializable {

    public int ID;
    public String Name;

    public int getId() {
        return ID;
    }

    public String getName() {
        return Name;
    }

}