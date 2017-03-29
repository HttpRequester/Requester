package bpadashi.ir.requester;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;


@Root(name = "soap:Envelope", strict = false)
public class ModelSoap implements Serializable {


    static final long serialVersionUID =8740213115075839093L;

    @Element(name = "id")
    @Path("Body/GetProductsResponse/GetProductsResult/Entity")
    private int id;

    public int getId(){
        return id;
    }


}
