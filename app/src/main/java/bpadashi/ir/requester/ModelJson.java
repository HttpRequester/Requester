package bpadashi.ir.requester;

import java.io.Serializable;

/**
 * Created by BabakPadashi on 29/03/2017.
 */
class ModelJson implements Serializable {

    public int statusCode;
    public String statusDes;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDes() {
        return statusDes;
    }

}