package bpadashi.ir.requester;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;


@Root(name = "soap:Envelope", strict = false)
public class ModelSoap implements Serializable {


    static final long serialVersionUID =8740213115075839093L;

    @ElementList(entry = "Entity", inline = true,required = false)
    @Path("Body/GetProductsResponse/GetProductsResult")
    private List<Entity> entities;

    public List<Entity> getEntity(){
        return  entities;
    }



}

@Root(strict=false)
class Entity implements Serializable{

    @Element
    private int id;

    @Element
    private String name;

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }
}