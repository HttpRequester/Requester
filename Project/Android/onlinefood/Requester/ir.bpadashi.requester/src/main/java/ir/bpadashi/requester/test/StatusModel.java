package ir.bpadashi.requester.test;

import java.io.Serializable;

public class StatusModel implements Serializable {

    static final long serialVersionUID = 8996890340799609057L;


    public long statusCode;
    public String statusDes;

    public StatusModel(long statusCode, String statusDes) {
        this.statusCode = statusCode;
        this.statusDes = statusDes;
    }

}
