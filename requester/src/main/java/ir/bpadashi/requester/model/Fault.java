package ir.bpadashi.requester.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by root on 4/3/17.
 */

@Root(strict=false)
public class Fault implements Serializable {

    @Element
    private String faultcode;

    @Element
    private String faultstring;

    public String getFaultcode(){
        return faultcode;
    }

    public String getFaultstring() {
        return faultstring;
    }
}
