package ir.bpadashi.requester.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by root on 4/3/17.
 */

@Root(name = "soap:Envelope", strict = false)
public class RequestFault implements Serializable {

    @Element(name = "Fault")
    @Path("Body")
    private Fault fault;

    public Fault getFault(){
        return  fault;
    }
}

