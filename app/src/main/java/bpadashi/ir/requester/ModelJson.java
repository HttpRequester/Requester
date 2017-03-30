package bpadashi.ir.requester;

import java.io.Serializable;

/**
 * Created by BabakPadashi on 29/03/2017.
 */
class ModelJson implements Serializable {

    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}